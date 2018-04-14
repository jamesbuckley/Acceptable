package utils;

import httphelpers.HttpRequest;

public class RequestUtils {

    public static String prepareRequestMapKey(HttpRequest request){

        if(request.getRequestType().equals(HttpRequest.HTTPRequestType.GET_REQUEST)){
            String queryString = request.getQueryString();
            return queryString.length() == 0 ? request.getUrlString() : request.getUrlString() + "?" + queryString;
        }else if(request.getRequestType().equals(HttpRequest.HTTPRequestType.POST_REQUEST)){
            String jsonBody = request.getJSONBody();
            return jsonBody.length() == 0 ? request.getUrlString() : request.getUrlString() + "%" + jsonBody;
        }else{
            return request.getUrlString();
        }
    }
}
