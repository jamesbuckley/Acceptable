import categories.LegacyTest;
import categories.QuickTest;
import categories.RestTest;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Issue;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.model.SeverityLevel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DummyTest {

    @Test
    @Issue("A trivial fix")
    @Description("A trivial test")
    public void dummyTest() throws Exception{
        assert true;
    }

    @Test
    @Features("Feature 1")
    @Description("A test with a wait")
    public void waitingForSomeSeconds () throws Exception{
        Thread.sleep(5000);
        assert true;
    }

    @Test
    @Issue("ISSUE-1")
    public void testGoodLogin() throws Exception {
        WebConversation wc = new WebConversation();
        WebRequest req = new GetMethodWebRequest( "http://localhost:8080" );
        WebResponse resp = wc.getResponse( req );
        System.out.print(resp.getText());

        assertNotNull(resp);
        assertEquals("{json=string value}", resp.getText());
    }

    @Test
    @Features("Feature 2")
    @Description("A failed test")
    public void failingTest () throws Exception{
        Thread.sleep(200);
        assert false;
    }
}
