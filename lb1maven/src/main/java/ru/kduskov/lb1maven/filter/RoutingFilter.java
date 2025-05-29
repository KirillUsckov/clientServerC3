package ru.kduskov.lb1maven.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import ru.kduskov.lb1maven.servlets.PhotoServlet;

import java.io.IOException;

@WebFilter("/api/v1/*")
@Slf4j
public class RoutingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String method = req.getMethod();
        String uri = req.getRequestURI();

        log.info("[REQUEST] %s %s%n", method, uri);

        chain.doFilter(request, response); // обязательно!
    }



    @Override
    public void destroy() {
    }
}
