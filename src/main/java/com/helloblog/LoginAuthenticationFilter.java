package com.helloblog;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        HttpSession getsession = request.getSession();
        String captruecode = (String) getsession.getAttribute("captruecode");
        String verification = request.getParameter("captruecode");

        if (!verification.contentEquals(captruecode)) {
            try {
                response.sendRedirect("/error");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return super.attemptAuthentication(request, response);
    }
}