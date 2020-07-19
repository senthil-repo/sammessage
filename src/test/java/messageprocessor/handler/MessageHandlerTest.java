package messageprocessor.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.assertEquals;

/**
 * Created by s.nathan on 19/07/2020.
 */
public class MessageHandlerTest {
    MessageHandler handler;

    @Mock
    Context context;
    @Mock
    SQSEvent sqsEvent;

    @Before
    public void setup() throws Exception{
        handler = new MessageHandler();
    }

    @Test
    public void testMessageHandler() {
        String result = handler.handleRequest(sqsEvent, context);
        assertEquals(" Unexpected result ", "Success", result);
    }
}
