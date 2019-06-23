package io.sentry;

public class Main {
    public static void main(String[] args) {
        SentryEvent event = new SentryEvent();
        System.out.println("Event Id: " + event.getEventId());
        System.out.println("Timestamp: " + event.getTimestamp());
    }
}