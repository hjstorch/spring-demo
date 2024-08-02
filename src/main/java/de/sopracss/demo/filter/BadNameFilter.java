package de.sopracss.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Component
public class BadNameFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String nameParameter = request.getParameter("myname");
        if (null != nameParameter && nameParameter.contains("bad")) {
            request.setAttribute("myname", "World");
            request.setAttribute("badname", true);
        }

        chain.doFilter(request, response);
    }
}
