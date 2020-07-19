package messageprocessor.handler.processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import messageprocessor.dto.Constants;
import messageprocessor.dto.Input;
import messageprocessor.dto.Result;
import messageprocessor.exception.InvalidMessageException;
import messageprocessor.processor.Processor;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

/**
 * Created by s.nathan on 19/07/2020.
 */
public class ProcessorTest {
    Processor processor;
    Gson gson = null;

    @Before
    public void setup() throws Exception{
        processor = new Processor();
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Test
    public void testProcessMessage() {
        String input = getInput();
        String output = processor.processMessage(input);
        assertNotNull(" Unexpected output ", output);
        Result result = toResult(output);
        assertTrue(" Wrong result ", result.getResponse().length == 16);
    }

    @Test(expected = InvalidMessageException.class)
    public void testProcessMessage_InvalidInput() {
        String input = "{A,B,C,D}";
        String output = processor.processMessage(input);
        assertNotNull(" Unexpected output ", output);
        try{
            Result result = toResult(output);
            assertNull(" Unexpected output ", result);
        }catch(InvalidMessageException e) {
            assertEquals(" Wrong output ", Constants.INVALID_MESSAGE, e.getMessage());
            throw e;
        }
    }

    private String getInput(){
        String inputArray[] = {"A", "B", "C", "D"};
        Input input = new Input();
        input.setInput(inputArray);
        return gson.toJson(input);
    }

    private Result toResult(String output) {
        return gson.fromJson(output, Result.class);
    }
}
