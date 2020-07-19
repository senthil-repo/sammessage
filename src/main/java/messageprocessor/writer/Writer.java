package messageprocessor.writer;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

/**
 * Created by s.nathan on 19/07/2020.
 */
public class Writer {
    public void storeOutput(String messageId, String processedMessage) {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        Table table = dynamoDB.getTable("processedmessage");
        Item item = new Item()
                .withPrimaryKey("MessageId", messageId)
                .withStringSet("output", processedMessage);

        StringBuilder stringBuilder = new StringBuilder(" Table name: ")
                .append(table.getTableName())
                .append(" Item details: ").append(item.toJSONPretty());
        System.out.println(stringBuilder.toString());

        table.putItem(item);
    }
}
