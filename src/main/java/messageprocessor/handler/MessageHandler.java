package messageprocessor.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

/**
 * Created by s.nathan on 19/07/2020.
 */
public class MessageHandler implements RequestHandler<SQSEvent, String> {
    @Override
    public String handleRequest(SQSEvent sqsEvent, Context context) {
        String message = null;
        String messageId = null;

        if(sqsEvent != null && sqsEvent.getRecords() != null) {
            for(SQSEvent.SQSMessage msg : sqsEvent.getRecords()){
            }
        }

        return "Success";
    }
}
