package messageprocessor.handler.writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import messageprocessor.writer.Writer;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by s.nathan on 19/07/2020.
 */
public class WriterTest {
    Writer writer = null;

    @Before
    public void setup() throws Exception{
        writer = new Writer();
    }

    @Test
    public void testStoreOutput() throws Exception{
        writer.storeOutput("2d03b305", getProcessedMessage());
    }

    private String getProcessedMessage() {
        String response[] = {"A", "B", "C", "AB"};
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(response);
    }
}
