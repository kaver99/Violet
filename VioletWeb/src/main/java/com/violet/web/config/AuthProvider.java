package com.violet.web.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.violet.web.user.UserEntity;
import com.violet.web.user.UserService;


@Component("authProvider")
public class AuthProvider implements AuthenticationProvider {
	private static Logger log = LoggerFactory.getLogger(AuthProvider.class); 
	
	@Autowired
	UserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getName();
        String user_password = (String) authentication.getCredentials();
        String enc_userPw = Sha512DigestUtils.shaHex(user_password); 
        
        Map<String, String> userData = new HashMap<String, String>();
        userData.put("username", username);
        userData.put("socialType", "local");
        
        int userCnt = userService.findByUserNameCount(userData);
        
        if(userCnt == 0) {
//        	log.error("Authentication failed : [ " + username + " ] " + " Not Found Exception.");
        	throw new UsernameNotFoundException("Authentication failed : [ " + username + " ] " + " Not Found Exception.");
        	
        }
        UserEntity userEntity = userService.findByUserName(userData);
        
        // Validation
        // user 정보가 없을경우. 패스워드가 일치하지 않는 경우. 패스워드가 "N/A"로 들어오는 경우 차단
        if(userEntity == null || !userEntity.getPassword().equals(enc_userPw) || user_password.equalsIgnoreCase("N/A")) {
//        	log.error("Authentication failed : [ " + username + " ] " + " is password Inconsistency.");
        	throw new BadCredentialsException("Authentication failed : [ " + username + " ] " + " is password Inconsistency.");
        	
        }
        
        WebAuthenticationDetails auth = (WebAuthenticationDetails) authentication.getDetails();
        userEntity.setRemoteIp(auth.getRemoteAddress());
        
        UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(username, user_password, userEntity.getAuthorities());
        // 사용자 부가정보 데이터 삽입
        userAuth.setDetails(userEntity);
        
        // 로그인 성공시 로그인 사용자 정보 반환
        return userAuth;

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	} 


}
