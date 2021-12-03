package cn.korostudio.shouhoupetspring.event;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.korostudio.shouhoupetspring.err.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;

public class EventSystem {

    protected final static Logger logger = LoggerFactory.getLogger(EventSystem.class);

    protected static final HashMap<String, ArrayList<Event>> eventLists;
    protected static final Thread distributionThread;
    protected static final ArrayBlockingQueue<String> distributionBlockingQueue;
    protected static final ExecutorService eventExecutor;

    static {
        distributionThread = new Thread(new distributionThreadClass());
        distributionBlockingQueue = new ArrayBlockingQueue<String>(1024, false);
        eventLists = new HashMap<>();
        eventExecutor = ExecutorBuilder.create()
                .setCorePoolSize(5)
                .setMaxPoolSize(10)
                .setKeepAliveTime(0)
                .build();
    }

    public static void Init() {
        logger.info("EvenSystem Init.");
        distributionThread.start();
    }

    public static void trigger(String str) {
        synchronized (distributionBlockingQueue) {
            distributionBlockingQueue.add(str);
        }
    }

    static public void addEvent(String key, Event event) {
        synchronized (eventLists) {
            eventLists.get(key).add(event);
        }
    }

    protected static class distributionThreadClass implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    String running = distributionBlockingQueue.take();
                    if (running.equalsIgnoreCase("EventSystem.End")) {
                        return;
                    }
                    ArrayList<Event> runEvents;
                    synchronized (eventLists) {
                        runEvents = eventLists.get(running);
                    }
                    if (runEvents != null) {
                        for (Event event : runEvents) {
                            eventExecutor.submit(event);
                        }
                    }
                } catch (InterruptedException e) {
                    Error.error(EventSystem.class, e);
                }
            }
        }
    }

}
