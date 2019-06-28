package io.sentry;

import javax.management.InvalidApplicationException;

public class Main {
    public static void main(String[] args) throws InvalidApplicationException {
        SentryEvent event = new SentryEvent();

        event.setLogEntry(new LogEntry("Sample event from Java", null, null));
        event.setLogger("Java-main");
        event.setRelease("6858af2");

        System.out.println("Event Id: " + event.getEventId());
        System.out.println("Timestamp: " + event.getTimestamp());

        SentryOptions options = new SentryOptions();
        options.setDsn("https://5fd7a6cda8444965bade9ccfd3df9882@sentry.io/1188141");
        options.setRelease("6858af2");

        Sentry.init(options);
        Sentry.captureEvent(event);

        try {
            crappyFunction();
        } catch (Throwable throwable) {
            Sentry.captureException(throwable);

            // rethrown and crash the app!
            throw throwable;
        }
    }

    private static void crappyFunction() throws InvalidApplicationException {
        throw new InvalidApplicationException("Test exception");
    }
}