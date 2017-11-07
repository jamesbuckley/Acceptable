import com.meterware.httpunit.*;
//import dataproviders.StatusDataProviders;
import httphelpers.HttpRequest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
//import org.apache.metamodel.query.SelectItem;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.HashMap;
import java.util.Map;

public class StatusServiceTest {

    @BeforeClass
    public void createBuilder(){
        HttpRequest.setBaseUrl("http://localhost:8083/soteria-status-service/");
        Map<String, String> defaultHeaders = new HashMap<>();
        defaultHeaders.put("Client-Id","S0teriaStatus");
        defaultHeaders.put("Client-Password", "S0ter4a-St@tu$");
        HttpRequest.setDefaultHeaders(defaultHeaders);
    }

    @Test
    @Feature("StatusService")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Create a status for owner Id")
    public void createStatus() throws Exception{

        Map<String, String> jsonBodyMap = new HashMap<>();
        jsonBodyMap.put("ownerId", "jamesd1a-8d95-11e7-b307-c36a705ca323");
        jsonBodyMap.put("statusId", "testStatus");
        jsonBodyMap.put("reasonId", "TestReason");
        HttpRequest request = new HttpRequest.HttpRequestBuilder("status/createStatus")
                .postJsonRequest(jsonBodyMap)
                .build();
        WebResponse response = request.executeRequest();
        Assert.assertEquals(response.getResponseCode(), 200);
    }

    @Test(dependsOnMethods = "createStatus")
    @Feature("StatusService")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get Current Status for owner Id")
    public void getCurrentStatus() throws Exception{
        HttpRequest request = new HttpRequest.HttpRequestBuilder("status/getCurrentStatus")
                .getRequest()
                .setParameter("ownerId", "jamesd1a-8d95-11e7-b307-c36a705ca323")
                .build();
        WebResponse response = request.executeRequest();
        Assert.assertTrue(response.getText().contains("testStatus"));
    }

//    @Test (dataProvider = "csvDataProvider", dataProviderClass = StatusDataProviders.class)
//    public void dataDrivenTest(SelectItem[] cols, Object[] data) throws Exception{
//        String aRow = Joiner.on("|").join( data );
//        logger.info( aRow );
//        assert true;
//    }

}
