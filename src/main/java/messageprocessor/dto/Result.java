package messageprocessor.dto;

/**
 * Created by s.nathan on 19/07/2020.
 */
public class Result {
    private String response[];

    public Result(String response[]) {
        this.response = response;
    }

    public String[] getResponse() {
        return response;
    }
}
