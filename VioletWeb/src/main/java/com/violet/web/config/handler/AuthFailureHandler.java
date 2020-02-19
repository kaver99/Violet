package com.violet.web.config.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	private static Logger log = LoggerFactory.getLogger(AuthFailureHandler.class);

	/* worring : onAuthenticationFailure 함수에 Authentication authentication 매개변수 추가시 실패 응답 인증 처리불가 */
	
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
//		HttpSession session = request.getSession();
//		if(session != null) {
//			session.invalidate();
//			SecurityContextHolder.getContext().setAuthentication(null);
//		}
    	
		request.setAttribute("status", "error");
		if(exception instanceof BadCredentialsException) {
			request.setAttribute("errMsg", "Email or password does not match.");
			log.error("Email or password does not match. " + exception);
		} else if(exception instanceof UsernameNotFoundException) {
			request.setAttribute("errMsg", "Nonexistent or unauthenticated email.");
			log.error("Nonexistent or unauthenticated email." + exception);
			
		} else {
			request.setAttribute("errMsg", "Error occurred during user processing.");
			
		}
        request.getRequestDispatcher("/login-error.violet").forward(request, response);

    }
}
