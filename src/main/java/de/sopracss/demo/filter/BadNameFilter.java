package de.sopracss.demo.filter;


import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
