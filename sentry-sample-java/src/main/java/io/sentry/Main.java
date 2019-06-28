package io.sentry;
// Required to find the overload with lambda
// import kotlin.jvm.functions.Function1;

//import kotlin.Unit;

public class Main {
    public static void main(String[] args) {
        SentryEvent event = new SentryEvent();

        // TODO: No optional parameters? Having to pass nulls here isn't great and makes `data class` with all nullable
        // fields less interesting
        event.setLogEntry(new LogEntry("Sample event from Java", null, null));
        event.setLogger("Java-main");
        event.setRelease("6858af2");

        System.out.println("Event Id: " + event.getEventId());
        System.out.println("Timestamp: " + event.getTimestamp());

        // TODO: fails with: "bad return type in lambda expression void cannot be converted to kotlin.Unit)"
        // Very much non idiomatic:
//         Sentry.init((SentryOptions o) -> {
//             o.setRelease("6858af2");
//             return Unit.INSTANCE;
//         });

        SentryOptions options = new SentryOptions();
        options.setDsn("https://5fd7a6cda8444965bade9ccfd3df9882@sentry.io/1188141");
        options.setRelease("6858af2");

        Sentry.init(options);

        Sentry.captureEvent(event);
    }
}