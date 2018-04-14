package httphelpers;

import com.meterware.httpunit.*;
import org.json.JSONObject;
import org.xml.sax.SAXException;
import utils.RequestUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;

public class HttpRequest {

    public enum HTTPRequestType{
        GET_REQUEST,
        POST_REQUEST;
    }

    private static String baseUrl;
    private static Map<String, String> defaultHeaders;
    private static Boolean recordRequest = false;

    private WebRequest webRequest;
    private WebConversation wc;
    private Enum requestType;
    private String messageBody;

    private HttpRequest(HttpRequestBuilder builder){
        this.webRequest = builder.webRequest;
        wc = new WebConversation();
        this.requestType = builder.requestType;
        this.messageBody = builder.messageBody;
    }

    public static void setBaseUrl(String baseUrl) {
        HttpRequest.baseUrl = baseUrl;
    }

    public static void setDefaultHeaders(Map<String, String> defaultHeaders){
        HttpRequest.defaultHeaders = defaultHeaders;
    }

    public WebResponse executeRequest() throws IOException, SAXException {
        WebResponse response = this.wc.getResponse(this.webRequest);
        if(recordRequest){
             createURLMapEntry(this, response);
        }
        return response;
    }

    public Enum getRequestType(){
        return this.requestType;
    }

    public String getQueryString(){
        return this.webRequest.getQueryString();
    }

    public String getJSONBody(){
        return this.messageBody;
    }

    public String getUrlString(){
        try {
            return this.webRequest.getURL().getPath();
        }catch (Exception e){
           throw new RuntimeException(e.getMessage());
        }
    }

    private static String getBaseUrl(){
        return baseUrl;
    }

    private static Map<String, String> getDefaultHeaders(){
        return defaultHeaders;
    }

    public static void setRecordRequest(Boolean recordRequest) {
        HttpRequest.recordRequest = recordRequest;
    }

    private static void createURLMapEntry(HttpRequest request, WebResponse response){
        Properties prop = new Properties();
        try(FileWriter fw = new FileWriter("request.map", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            String mapKey = RequestUtils.prepareRequestMapKey(request);
            prop.setProperty(mapKey, response.getText());
            prop.store(out, null);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static class HttpRequestBuilder {

        private String requestUrl;
        private Map<String, String> requestHeaders;
        private WebRequest webRequest;
        private Enum requestType;
        private String messageBody;

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
            this.requestType = HTTPRequestType.GET_REQUEST;
            return this;
        }

        public HttpRequestBuilder postJsonRequest(Map<String, String> jsonBody){
            ByteArrayInputStream jsonBodyByteIS = this.getJsonBody(jsonBody);
            this.webRequest = new PostMethodWebRequest(HttpRequest.getBaseUrl() + requestUrl, jsonBodyByteIS, "application/json");
            this.requestType = HTTPRequestType.POST_REQUEST;
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
            this.messageBody = jsonObject.toString();
            return new ByteArrayInputStream(jsonObject.toString().getBytes(Charset.forName("UTF-8")));
        }
    }
}
