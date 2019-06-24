package io.sentry;
// Required to find the overload with lambda
// import kotlin.jvm.functions.Function1;

public class Main {
    public static void main(String[] args) {
        SentryEvent event = new SentryEvent();
        event.setMessage("Sample event from Java");

        System.out.println("Event Id: " + event.getEventId());
        System.out.println("Timestamp: " + event.getTimestamp());
        System.out.println("Message: " + event.getMessage());

        //  bad return type in lambda expression void cannot be converted to kotlin.Unit)
        // Sentry.init((SentryOptions o) -> o.setRelease("6858af2"));

        SentryOptions options = new SentryOptions();
        options.setRelease("6858af2");
        Sentry.init(options);

        Sentry.captureEvent(event);
    }
}