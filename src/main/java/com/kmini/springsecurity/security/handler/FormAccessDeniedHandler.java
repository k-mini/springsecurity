package com.kmini.springsecurity.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kmini.springsecurity.util.WebUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormAccessDeniedHandler implements AccessDeniedHandler {

    private String errorPage;

    private ObjectMapper mapper = new ObjectMapper();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        String deniedUrl = errorPage + "?exception=" + accessDeniedException.getMessage();
//        response.sendRedirect(deniedUrl);

        if (WebUtil.isAjax(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(this.mapper.writeValueAsString(ResponseEntity.status(HttpStatus.FORBIDDEN).build()));

        } else {
            String deniedUrl = errorPage + "?exception=" + "Access is Denied"; // accessDeniedException.getMessage();
            redirectStrategy.sendRedirect(request, response, deniedUrl);
        }
    }

    public void setErrorPage(String errorPage) {
        if ((errorPage != null) && !errorPage.startsWith("/")) {
            throw new IllegalArgumentException("errorPage must begin with '/'");
        }
        this.errorPage = errorPage;
    }
}
