package messageprocessor.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import messageprocessor.processor.Processor;

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
                message = msg.getBody();
                messageId = msg.getMessageId();
            }
        }
        printMessage(message, messageId);

        //process the input to get the result
        String processedMessage = new Processor().processMessage(message);

        return processedMessage != null ? "Success" : "Failure";
    }

    private void printMessage(String message, String messageId) {
        StringBuilder messageDetails = new StringBuilder("Message Body: ")
                .append(message).append(" & message id: ").append(messageId);
        System.out.println(messageDetails.toString());
    }
}
