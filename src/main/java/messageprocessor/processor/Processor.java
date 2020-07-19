package messageprocessor.processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import messageprocessor.dto.Input;
import messageprocessor.dto.Result;
import org.paukov.combinatorics3.Generator;

/**
 * Created by s.nathan on 19/07/2020.
 */
public class Processor {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String processMessage(String inputFromSQS) {
        Input input = gson.fromJson(inputFromSQS, Input.class);
        Result result = getResult(input.getInput());
        return toJson(result);
    }

    /**
     * Main method that generates all possible combinations of the values for the respective input.
     * @param input
     * @return
     */
    private Result getResult(String input[]) {
        String response[] = Generator.subset(input)
                .simple()
                .stream()
                .map(strings -> strings.stream().reduce("", String::concat))
                .toArray(String[]::new);
        return new Result(response);
    }

    private String toJson(Result output) {
        return gson.toJson(output);
    }
}
