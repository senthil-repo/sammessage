package messageprocessor.processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import messageprocessor.dto.Constants;
import messageprocessor.dto.Input;
import messageprocessor.dto.Result;
import messageprocessor.exception.InvalidMessageException;
import org.paukov.combinatorics3.Generator;

/**
 * 1 - Validate the input message
 * 2 - Generates all possible combinations of the values
 * Created by s.nathan on 19/07/2020.
 */
public class Processor {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String processMessage(String inputFromSQS) {
        Result result;
        try{
            Input input = gson.fromJson(inputFromSQS, Input.class);
            result = getResult(input.getInput());
        } catch (JsonSyntaxException e) {
            StringBuilder stringBuilder = new StringBuilder(" Unable to process the input message: ")
                    .append(e.getMessage());
            System.out.println(stringBuilder.toString());
            throw new InvalidMessageException(Constants.INVALID_MESSAGE);
        }
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
