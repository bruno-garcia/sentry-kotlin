package io.sentry;

public class Main {
    public static void main(String[] args) {
        SentryEvent event = new SentryEvent();
        event.setMessage("Sample event from Java");

        System.out.println("Event Id: " + event.getEventId());
        System.out.println("Event Id: " + event.getEventId());
        System.out.println("Timestamp: " + event.getTimestamp());
        System.out.println("Message: " + event.getMessage());

        SentryClient client = new DefaultSentryClient();
        client.captureEvent(event);
    }
}