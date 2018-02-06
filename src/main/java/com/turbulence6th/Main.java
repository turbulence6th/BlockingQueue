package com.turbulence6th;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) throws Exception {

		BlockingQueue<Message> queue = new ArrayBlockingQueue<>(1024);
		Random random = new Random();
		
		new Thread(() -> {
			int i = 0;
			while(true) {
				try {
					Thread.sleep(random.nextInt(2000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				queue.add(new Message("click", String.valueOf(i++)));
			}
		}).start();
		
		new Thread(() -> {
			int i = 0;
			while(true) {
				try {
					Thread.sleep(random.nextInt(2000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				queue.add(new Message("focus", String.valueOf(i++)));
			}
		}).start();
		
		EventController eventController = new EventController(); 
		Map<String, List<Method>> methodMap = Arrays.stream(EventController.class.getDeclaredMethods())
			.filter(m -> m.isAnnotationPresent(Event.class))
			.collect(Collectors.groupingBy(m -> m.getDeclaredAnnotation(Event.class).value()));
		
		while(true) {
			Message message = queue.take();
			for(Method method: methodMap.get(message.event)) {
				new Thread(() -> {
					try {
						method.invoke(eventController, message);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}).start();
			}
			
		}
	}
	
}
