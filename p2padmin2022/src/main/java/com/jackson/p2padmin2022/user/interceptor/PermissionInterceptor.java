package com.jackson.p2padmin2022.user.interceptor;

import com.jackson.p2padmin2022.user.model.UserInfo;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * ClassName: PermissionInterceptor
 * Package: com.jackson.p2padmin2022.user.interceptor
 * Description:
 *
 * @Date: 8/31/2022 6:13 PM
 * @Author: JacksonYu
 */
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        if (userInfo == null) {
            request.getRequestDispatcher("/").forward(request, response);
            return false;
        }

        String path = request.getServletPath();
        Map<String, String[]> paramMap = request.getParameterMap();
        StringBuilder url = new StringBuilder(path + "?");
        for (String key : paramMap.keySet()) {
            url.append(key).append("=").append(paramMap.get(key)[0]).append("&");
        }
        url.deleteCharAt(url.length() - 1);

        Map<String, String> urlMap = userInfo.getMap();
        if (!urlMap.containsKey(url.toString()) && !urlMap.containsKey(path)){
            request.getRequestDispatcher("/noPermission").forward(request, response);
            return false;
        }

        return true;
    }
}
