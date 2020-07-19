package messageprocessor.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

/**
 * Created by s.nathan on 19/07/2020.
 */
public class MessageHandler {
    public String handleRequest(SQSEvent sqsEvent, Context context) {
        return "";
    }
}
