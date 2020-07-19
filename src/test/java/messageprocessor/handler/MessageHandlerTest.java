package messageprocessor.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import messageprocessor.processor.Processor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * Created by s.nathan on 19/07/2020.
 */
public class MessageHandlerTest {
    MessageHandler handler;

    @Mock
    Context context;
    @Mock
    SQSEvent sqsEvent;
    @Mock
    Processor processor;

    @Before
    public void setup() throws Exception{
        handler = new MessageHandler();
        PowerMockito.whenNew(Processor.class).withAnyArguments().thenReturn(processor);
    }

    @Test
    public void testMessageHandler() {
        when(sqsEvent.getRecords()).thenReturn(getRecords());
        when(processor.processMessage(anyString())).thenReturn(getProcessedMessage());
        String result = handler.handleRequest(sqsEvent, context);
        assertEquals(" Unexpected result ", "Success", result);
    }

    private List<SQSEvent.SQSMessage> getRecords() {
        List<SQSEvent.SQSMessage> sqsMessages = new ArrayList<>();
        SQSEvent.SQSMessage sqsMessage = new SQSEvent.SQSMessage();
        sqsMessage.setAwsRegion("eu-west-2");
        sqsMessage.setBody("hello");
        sqsMessage.setMessageId("2d03b305");
        sqsMessages.add(sqsMessage);

        return sqsMessages;
    }

    private String getProcessedMessage() {
        String response[] = {"A", "B", "C", "AB"};
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(response);
    }
}
