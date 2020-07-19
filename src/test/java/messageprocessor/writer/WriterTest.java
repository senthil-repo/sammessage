package messageprocessor.writer;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import messageprocessor.dto.Constants;
import messageprocessor.exception.UnableToStoreOutputException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

/**
 * Created by s.nathan on 19/07/2020.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AmazonDynamoDBClientBuilder.class, AmazonDynamoDB.class,
        DynamoDB.class, Table.class, PutItemResult.class})
public class WriterTest {
    Writer writer = null;
    @Mock
    AmazonDynamoDBClientBuilder amazonDynamoDBClientBuilder;
    @Mock
    AmazonDynamoDB amazonDynamoDB;
    @Mock
    DynamoDB dynamoDB;
    @Mock
    Table table;
    @Mock
    PutItemResult putItemResult;

    @Before
    public void setup() throws Exception{
        writer = new Writer();
        PowerMockito.mockStatic(AmazonDynamoDBClientBuilder.class);
    }

    @Test
    public void testStoreOutput() throws Exception{
        when(AmazonDynamoDBClientBuilder.defaultClient()).thenReturn(amazonDynamoDB);
        PowerMockito.whenNew(DynamoDB.class).withArguments(amazonDynamoDB).thenReturn(dynamoDB);
        when(dynamoDB.getTable(anyString())).thenReturn(table);
        when(amazonDynamoDB.putItem(any(PutItemRequest.class))).thenReturn(putItemResult);

        try {
            writer.storeOutput("2d03b305", getProcessedMessage());
        } catch (Exception e){
            fail(" Exception not expected here ");
        }
    }

    @Test(expected = UnableToStoreOutputException.class)
    public void testStoreOutput_Exception() throws Exception{
        when(AmazonDynamoDBClientBuilder.defaultClient()).thenReturn(amazonDynamoDB);
        PowerMockito.whenNew(DynamoDB.class).withArguments(amazonDynamoDB).thenReturn(dynamoDB);
        when(dynamoDB.getTable(anyString())).thenReturn(table);
        when(amazonDynamoDB.putItem(any(PutItemRequest.class))).thenThrow(new IllegalArgumentException());

        try {
            writer.storeOutput("2d03b305", getProcessedMessage());
        } catch (UnableToStoreOutputException e){
            assertEquals(" Unexpected result ", Constants.UNABLE_TO_STORE_OUTPUT, e.getMessage());
            throw e;
        }
    }

    private String getProcessedMessage() {
        String response[] = {"A", "B", "C", "AB"};
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(response);
    }
}
