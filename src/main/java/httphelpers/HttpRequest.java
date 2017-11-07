package httphelpers;

import com.meterware.httpunit.*;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class HttpRequest {

    private static String baseUrl;
    private WebRequest webRequest;
    private static Map<String, String> defaultHeaders;
    WebConversation wc;

    private HttpRequest(HttpRequestBuilder builder){
        this.webRequest = builder.webRequest;
        wc = new WebConversation();
    }

    public static void setBaseUrl(String baseUrl) {
        HttpRequest.baseUrl = baseUrl;
    }

    public static void setDefaultHeaders(Map<String, String> defaultHeaders){
        HttpRequest.defaultHeaders = defaultHeaders;
    }

    public WebResponse executeRequest() throws IOException, SAXException {
        return this.wc.getResponse(this.webRequest);
    }

    static String getBaseUrl(){
        return baseUrl;
    }

    static Map<String, String> getDefaultHeaders(){
        return defaultHeaders;
    }

    public static class HttpRequestBuilder {

        private String requestUrl;
        private Map<String, String> requestHeaders;
        private WebRequest webRequest;

        public HttpRequestBuilder(String requestUrl){
            this.requestUrl = requestUrl;
        }

        public HttpRequestBuilder requestHeaders(Map<String, String> requestHeaders){
            for (Map.Entry<String, String> entry : requestHeaders.entrySet())
            {
                this.webRequest.setHeaderField(entry.getKey(), entry.getValue());
            }
            return this;
        }

        public HttpRequestBuilder setParameter(String name, String value){
            this.webRequest.setParameter(name,value);
            return this;
        }

        public HttpRequestBuilder getRequest(){
            this.webRequest = new GetMethodWebRequest(HttpRequest.getBaseUrl() + requestUrl);
            return this;
        }

        public HttpRequestBuilder postJsonRequest(Map<String, String> jsonBody){
            ByteArrayInputStream jsonBodyByteIS = this.getJsonBody(jsonBody);
            this.webRequest = new PostMethodWebRequest(HttpRequest.getBaseUrl() + requestUrl, jsonBodyByteIS, "application/json");
            return this;
        }

        public HttpRequest build(){
            for (Map.Entry<String, String> entry : HttpRequest.getDefaultHeaders().entrySet())
            {
                this.webRequest.setHeaderField(entry.getKey(), entry.getValue());
            }
            return new HttpRequest(this);
        }

        private ByteArrayInputStream getJsonBody(Map<String, String> jsonStringMap){
            JSONObject jsonObject = new JSONObject(jsonStringMap);
            return new ByteArrayInputStream(jsonObject.toString().getBytes(Charset.forName("UTF-8")));
        }
    }
}
