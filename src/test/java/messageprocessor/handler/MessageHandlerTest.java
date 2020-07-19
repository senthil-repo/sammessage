package messageprocessor.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import messageprocessor.handler.dto.Constants;
import messageprocessor.handler.exception.InvalidMessageException;
import messageprocessor.processor.Processor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertNull;

/**
 * Created by s.nathan on 19/07/2020.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageHandler.class, Context.class, SQSEvent.class})
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

    @Test(expected = InvalidMessageException.class)
    public void testMessageHandler_EmptyMessage() {
        when(sqsEvent.getRecords()).thenReturn(getEmptyMessage());
        try {
            String result = handler.handleRequest(sqsEvent, context);
            assertNull(result);
        }catch(InvalidMessageException e) {
            assertEquals(" Unexpected result ", Constants.INVALID_MESSAGE, e.getMessage());
            throw e;
        }
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

    private List<SQSEvent.SQSMessage> getEmptyMessage() {
        List<SQSEvent.SQSMessage> sqsMessages = new ArrayList<>();
        SQSEvent.SQSMessage sqsMessage = new SQSEvent.SQSMessage();
        sqsMessages.add(sqsMessage);
        return sqsMessages;
    }
}
