package com.violet.web.config.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.violet.web.user.UserAddrEntity;
import com.violet.web.user.UserEntity;
import com.violet.web.user.UserRoleEntity;
import com.violet.web.user.UserService;

@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private static Logger log = LoggerFactory.getLogger(AuthSuccessHandler.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler;
	
	
	@SuppressWarnings("unchecked")
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
		
		UserEntity userEntity = new UserEntity();
		UserRoleEntity userRoleEntity = new UserRoleEntity();
		UserAddrEntity userAddrEntity = new UserAddrEntity();
		
		// Social login user에 대한 데이터 바인딩 처리
		if(request.getServletPath().contains("kakao")) {
			Map<String, Object> details = (Map<String, Object>) ((OAuth2Authentication) authentication).getUserAuthentication().getDetails();
			Map<String, Object> userPropData = (Map<String, Object>) details.get("properties");
	        Map<String, Object> userAccountData = (Map<String, Object>) details.get("kakao_account");
	        // 이메일 소유 여부
	        boolean hasEmail = (boolean) userAccountData.get("has_email");
	        // 이메일 유효 여부
	        boolean isEmailValid = (boolean) userAccountData.get("is_email_valid");
	        // 인증받은 이메일 여부
	        boolean isEmailVerified = (boolean) userAccountData.get("is_email_verified");
	        
	        // 이메일 소유, 유효, 인증 여부에 따른 username 맵핑
	        if(hasEmail & isEmailValid & isEmailVerified) {
    			userEntity.setUsername((String) userAccountData.get("email"));
    			
	        } else {
	        	throw new UsernameNotFoundException("Nonexistent or unauthenticated email");
	        	
	        }
	        userEntity.setPassword("N/A");
	        userEntity.setName((String) userPropData.get("nickname"));
	        userEntity.setImg_thumbnail((String) userPropData.get("thumbnail_image"));
	        userEntity.setSocialType("kakao");
	        userEntity.setSocialId(details.get("id").toString());
	        userEntity.setPhone("");
	        userEntity.setTelecom("");

	        // Social Login Role Access data 유효성 체크
	        if(authentication.getAuthorities().toString().contains("USER")) {
	        	userRoleEntity.setAuthority("USER");
	        	
	        }
	        userRoleEntity.setRole_name("고객");
	        userRoleEntity.setUsername(userEntity.getUsername());
	        userRoleEntity.setSocialType(userEntity.getSocialType());
	    
		} else if(request.getServletPath().contains("naver")) {
			Map<String, Object> details = (Map<String, Object>) ((OAuth2Authentication) authentication).getUserAuthentication().getDetails();
			Map<String, Object> userResponseData = (Map<String, Object>) details.get("response");

			userEntity.setUsername((String) userResponseData.get("email")); // 네이버 등록 시 사용한 이메일
			userEntity.setPassword("N/A");
			userEntity.setName((String) userResponseData.get("name"));
	        userEntity.setImg_thumbnail((String) userResponseData.get("profile_image"));
	        userEntity.setSocialType("naver");
	        userEntity.setSocialId(userResponseData.get("id").toString());
	        userEntity.setPhone("");
	        userEntity.setTelecom("");
	        
	        // Social Login Role Access data 유효성 체크
	        if(authentication.getAuthorities().toString().contains("USER")) {
	        	userRoleEntity.setAuthority("USER");
	        	
	        }
	        userRoleEntity.setRole_name("고객");
	        userRoleEntity.setUsername(userEntity.getUsername());
	        userRoleEntity.setSocialType(userEntity.getSocialType());
	        
		} else if(request.getServletPath().contains("facebook")) {
			
			userEntity.setSocialType("facebook");
			
			// Social Login Role Access data 유효성 체크
			if(authentication.getAuthorities().toString().contains("USER")) {
	        	userRoleEntity.setAuthority("USER");
	        	
	        }
	        userRoleEntity.setRole_name("고객");
			userRoleEntity.setUsername(userEntity.getUsername());
	        userRoleEntity.setSocialType(userEntity.getSocialType());
			
		} else {
			// Local login user에 대한 데이터 바인딩 처리
			userEntity = (UserEntity) authentication.getDetails();
			
		}
		
		int userCnt = 0;
		try {
			// Social & Local login user data insert or update			
			userCnt = userTrigger(userEntity, userRoleEntity, userAddrEntity);
			// 사용자 정보가 저장 되거나 업데이트 처리 과정에서 오류 발생 시 인증이 실패된 것으로 처리
			if(userCnt == 0) {
				throw new AuthenticationServiceException("Error occurred during user processing.");
			}
			
			// 사용자 권한 정보 리스트([ROLE_USER], [ROLE_MANAGER], [ROLE_ADMIN])
			// 현재 하나의 계정에 하나의 권한을 지정하여 사용하도록 설계
			// 권한 정보를 View에 넘겨서 사용하기 위해선 String값으로 가공이 필요
			// ex) userEntity.getAuthorities().get(0).toString() --> 'ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN'
			userEntity.setAuthorities((List<GrantedAuthority>) authentication.getAuthorities());
			
			// Session setting
			HttpSession session = request.getSession();
			if(session != null) {
				session.setAttribute("username", userEntity.getUsername());
				session.setAttribute("name", userEntity.getName());
				session.setAttribute("userRole", userEntity.getAuthorities().get(0).toString());
				session.setAttribute("imgThumbnail", userEntity.getImg_thumbnail());
				session.setAttribute("socialType", userEntity.getSocialType());
				
			}
			
			log.info("Login AuthSuccess : [ <" + userEntity.getSocialType() + "> " + userEntity.getUsername() + " / " + request.getRemoteAddr() + " ]");
			super.onAuthenticationSuccess(request, response, authentication);
			
		} catch (AuthenticationServiceException e) {
			e.printStackTrace();
			
			request.removeAttribute("code");
			request.removeAttribute("state");
			simpleUrlAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
		}
    }
	
	
	/**
	 * Social & Local login user data insert or update
	 * @param userEntity
	 * @param userRoleEntity
	 * @param userAddrEntity
	 * @return dataCnt
	 */
	public int userTrigger(UserEntity userEntity, UserRoleEntity userRoleEntity, UserAddrEntity userAddrEntity) {
		
		int dataCnt = 0;
		int userCnt = 0;
		try {
			Map<String, String> userData = new HashMap<String, String>(); 
			userData.put("username", userEntity.getUsername());
			userData.put("socialType", userEntity.getSocialType());
			
			userCnt = userService.findByUserNameCount(userData);
			
			if(userCnt == 0) {
				// 등록된 사용자가 없을 경우 사용자 정보 인서트
				dataCnt = userService.signupUser(userEntity, userRoleEntity, userAddrEntity);
				
			} else {
				// 등록된 사용자가 있을 경우 사용자 로그인 시간 업데이트
				dataCnt = userService.userAccessDateUpdate(userEntity.getUsername(), userEntity.getSocialType());
				
			}
			
		} catch(Exception e) {
			e.toString();
		}
		
		return dataCnt;
	}
}
