package com.odeniz.dev.orbit.configration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ConfigurationManager configurationManager;

    public CustomLoginSuccessHandler() {
        setDefaultTargetUrl("/orbit");
        setAlwaysUseDefaultTargetUrl(false);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            int sessionTimeout = configurationManager.getParamAsInteger("session.timeout", 60 * 30);
            session.setMaxInactiveInterval(sessionTimeout);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
