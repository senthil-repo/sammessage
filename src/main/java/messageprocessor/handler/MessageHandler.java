package messageprocessor.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import messageprocessor.dto.Constants;
import messageprocessor.exception.InvalidMessageException;
import messageprocessor.processor.Processor;
import messageprocessor.writer.Writer;
import org.apache.commons.lang3.StringUtils;

/**
 * Main Lambda class that gets invoked whenever a message is dropped in SQS
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

        if(StringUtils.isBlank(message))
            throw new InvalidMessageException(Constants.INVALID_MESSAGE);

        handleMessage(message, messageId);

        return "Success";
    }

    private void printMessage(String message, String messageId) {
        StringBuilder messageDetails = new StringBuilder("Message Body: ")
                .append(message).append(" & message id: ").append(messageId);
        System.out.println(messageDetails.toString());
    }

    /**
     * 1 - Validate and process the message
     * 2 - Store the processed message in dynamodb
     * @param message
     * @param messageId
     */
    private void handleMessage(String message, String messageId) {
        //process the input to get the result
        String processedMessage = new Processor().processMessage(message);

        //store the result in dynamodb
        new Writer().storeOutput(messageId, processedMessage);
    }
}
