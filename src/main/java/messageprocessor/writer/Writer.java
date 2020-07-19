package messageprocessor.writer;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import messageprocessor.dto.Constants;
import messageprocessor.exception.UnableToStoreOutputException;

/**
 * Class to store the result to dynamodb table
 * Created by s.nathan on 19/07/2020.
 */
public class Writer {
    public void storeOutput(String messageId, String processedMessage)  {
        StringBuilder stringBuilder;
        try {
            AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
            DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
            Table table = dynamoDB.getTable("processedmessage");
            Item item = new Item()
                    .withPrimaryKey("MessageId", messageId)
                    .withStringSet("output", processedMessage);

            printItem(table, item);

            table.putItem(item);
        } catch(Exception e) { //This is caught just to give a meaningful exception to the caller,
            // in case of any issues while connecting to the database
            stringBuilder = new StringBuilder(" Unable to process the message for message id: ")
                    .append(messageId)
                    .append(" - ")
                    .append(e.getMessage());
            System.out.println(stringBuilder.toString());
            throw new UnableToStoreOutputException(Constants.UNABLE_TO_STORE_OUTPUT);
        }
    }

    private void printItem(Table table, Item item) {
        StringBuilder stringBuilder;
        stringBuilder = new StringBuilder(" Table name: ")
                .append(table.getTableName())
                .append(" Item details: ").append(item.toJSONPretty());
        System.out.println(stringBuilder.toString());
    }
}
