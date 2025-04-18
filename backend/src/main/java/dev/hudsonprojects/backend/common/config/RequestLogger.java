package dev.hudsonprojects.backend.common.config;

import dev.hudsonprojects.backend.appuser.AppUserEndpoint;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Order(-2)
@Component
public class RequestLogger implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLogger.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        LOGGER.info("REQUEST {} {}", req.getMethod(), req.getRequestURL());
        chain.doFilter(request, response);
    }
}
