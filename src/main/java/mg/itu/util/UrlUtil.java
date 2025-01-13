package mg.itu.util;

import jakarta.servlet.http.HttpServletRequest;

public class UrlUtil {
    public static String getBaseUrl(HttpServletRequest request){
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
