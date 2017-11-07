import com.google.common.base.Joiner;
import com.meterware.httpunit.*;
//import dataproviders.StatusDataProviders;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
//import org.apache.metamodel.query.SelectItem;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.json.JSONObject;


import java.io.ByteArrayInputStream;

public class StatusServiceTest {

    private org.slf4j.Logger logger = LoggerFactory.getLogger( this.getClass().getSimpleName() );

    String baseURL = "http://localhost:8083/soteria-status-service/";

    @Test(dependsOnMethods = "createStatus")
    @Feature("StatusService")
    @Description("Get Current Status")
    public void getCurrentStatus() throws Exception{
        WebConversation wc = new WebConversation();
        WebRequest req = new GetMethodWebRequest( baseURL+"status/getCurrentStatus");
        req.setHeaderField("Client-Id", "S0teriaStatus");
        req.setHeaderField("Client-Password", "S0ter4a-St@tu$");
        req.setParameter("ownerId", "2d049d1a-8d95-11e7-b307-c36a705ca323");
        WebResponse response = wc.getResponse(req);
        Assert.assertNotNull(response);

    }

    @Test
    @Feature("StatusService")
    @Description("Create a status for owner Id")
    public void createStatus() throws Exception{
        JSONObject jsonRequest = new JSONObject("{ \"ownerId\": \"jamesd1a-8d95-11e7-b307-c36a705ca323\", \"statusId\": \"testStatus\" , \"reasonId\": TestReason}");

        WebConversation wc = new WebConversation();
        WebRequest req = new PostMethodWebRequest(baseURL+"status/createStatus", new ByteArrayInputStream(jsonRequest.toString().getBytes("UTF-8")),
                "application/json");
        req.setHeaderField("Client-Id", "S0teriaStatus");
        req.setHeaderField("Client-Password", "S0ter4a-St@tu$");
        WebResponse response = wc.getResponse(req);
        Assert.assertNotNull(response);
    }

//    @Test (dataProvider = "csvDataProvider", dataProviderClass = StatusDataProviders.class)
//    public void dataDrivenTest(SelectItem[] cols, Object[] data) throws Exception{
//        String aRow = Joiner.on("|").join( data );
//        logger.info( aRow );
//        assert true;
//    }

    //S0teriaStatus
    //a42f5089a8130dfc7a462132839da62c

    //def clientId = request.getHeader("Client-Id");
    //def clientPassword = request.getHeader("Client-Password")
}
