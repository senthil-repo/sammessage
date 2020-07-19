package messageprocessor.handler.processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import messageprocessor.dto.Input;
import messageprocessor.processor.Processor;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;


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
    }

    private String getInput(){
        String inputArray[] = {"A", "B", "C", "D"};
        Input input = new Input();
        input.setInput(inputArray);
        return gson.toJson(input);
    }
}
