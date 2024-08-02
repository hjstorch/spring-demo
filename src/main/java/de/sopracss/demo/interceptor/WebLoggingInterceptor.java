package de.sopracss.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class WebLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        response.addHeader("x-creator", "spring-boot");
        log.debug("Got request from: {} to: {}", request.getRemoteAddr(), request.getRequestURI());
        log.debug("Request is: {}, parameters: {}", request.getContentType(), request.getParameterMap().toString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if(null != modelAndView) {
            String viewName = modelAndView.getViewName();
            log.debug("View is: {}", viewName);
            log.debug("Model is: {}", modelAndView.getModel());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        log.debug("Sending response to: {}", request.getRemoteAddr());
        log.debug("Response Body: {}", response.toString());
        log.debug("Handled by: {}", handler.getClass().getCanonicalName());
    }


}
