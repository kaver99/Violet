package com.violet.web.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CompositeFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.violet.web.config.handler.AuthFailureHandler;
import com.violet.web.config.handler.AuthSuccessHandler;

@EnableWebSecurity
@EnableOAuth2Client
@RestController
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    AuthProvider authProvider;
	
	@Autowired
	AuthSuccessHandler successHandler;
	
	@Autowired
	AuthFailureHandler failureHandler;
	
	@Autowired
	OAuth2ClientContext oauth2ClientContext;

//	@Bean
//	@ConfigurationProperties("google")
//	public ClientResource google() {
//		return new ClientResource();
//	}
	
	@Bean
	@ConfigurationProperties("facebook")
	public ClientResource facebook() {
		return new ClientResource();
	}
	
	@Bean
	@ConfigurationProperties("kakao")
	public ClientResource kakao() {
		return new ClientResource();
	}
	
	@Bean
	@ConfigurationProperties("naver")
	public ClientResource naver() {
		return new ClientResource();
	}
	
	
	/**
	 * 인증 요청에 따른 리다이렉션을 위한 Bean 등록
	 * @param filter
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<OAuth2ClientContextFilter>();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}
	
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**", "/css/**", "/img/**", "/assets/**");
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		 // 권한 설정
        http.authorizeRequests()
            // ROLE_USER, ROLE_ADMIN으로 권한 분리 유알엘 정의
//            .antMatchers("/", "/login**", "/signup**", "/profile**", "/login-error**", "/error**").permitAll()
        	  .antMatchers("/**").permitAll()
//            .antMatchers("/**").access("ROLE_USER")
//            .antMatchers("/**").access("ROLE_ADMIN")
            .antMatchers("/management/**").access("ROLE_MANAGER")
        .and()
            // 로그인 페이지 및 성공 url, handler 그리고 로그인 시 사용되는 id, password 파라미터 정의
            .formLogin()
            .loginPage("/login.violet")
            .defaultSuccessUrl("/")
            .usernameParameter("username")
            .passwordParameter("password")
            .successHandler(successHandler)
            .failureHandler(failureHandler)
        .and()
            // Logout Setting
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout.violet"))
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID")
        .and()
	    	// csrf is used Setting
	        // csrf 설정을 사용하면 모든 request에 csrf 값을 함께 전달해야한다.
	    	// ex) <meta id="_csrf" name="_csrf" content="5809e5fc-4f27-4ced-bd7e-3355580acb39">
//	        .csrf().csrfTokenRepository(csrfTokenRepository())
        	.csrf()
        .and()
            // Filter Setting
        	.authenticationProvider(authProvider)
//        	.addFilterBefore(csrfHeaderFilter(), CsrfFilter.class)
        	.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
    }

	
	/**
	 * Social Login Filter
	 * URL : https://spring.io/guides/tutorials/spring-boot-oauth2/#_social_login_logout
	 * google : https://console.developers.google.com
	 * facebook : https://developers.facebook.com
	 * kakao : https://developers.kakao.com
	 * naver : https://developers.naver.com
	 * @return
	 */
	private Filter ssoFilter() {
		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();
		// application.properties에 등록했던 OAuth 리다이렉트 URL
//		filters.add(ssoFilter(google(), "/login/google")); 
		filters.add(ssoFilter(kakao(), "/login/kakao"));
		filters.add(ssoFilter(naver(), "/login/naver"));
		filters.add(ssoFilter(facebook(), "/login/facebook"));
		filter.setFilters(filters);
		
		return filter;
	}
	
	/**
	 * Social Loogin Filter
	 * URL : https://spring.io/guides/tutorials/spring-boot-oauth2/#_social_login_logout
	 * @param client
	 * @param path
	 * @return
	 */
	private Filter ssoFilter(ClientResource client, String path) {
		OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
		OAuth2RestTemplate oauth2RestTemplate = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
		filter.setRestTemplate(oauth2RestTemplate);
		UserInfoTokenServices userInfoTokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
		userInfoTokenServices.setRestTemplate(oauth2RestTemplate);
		filter.setTokenServices(userInfoTokenServices);
		filter.setAuthenticationSuccessHandler(successHandler);
		filter.setAuthenticationFailureHandler(failureHandler);
		
		return filter;
	}
	
	/**
	 * CSRF Token Repository
	 * @return
	 */
	private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
	
	/**
	 * HttpServletRequest CSRF Header Read Cookie Add Filter
	 * @return
	 */
	private Filter csrfHeaderFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request,
			                                HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
//				CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
				CsrfToken csrf = (CsrfToken) request.getAttribute("_csrf");
				System.out.println("csrf : " + csrf);
				if (csrf != null) {
					Cookie cookie = new Cookie("XSRF-TOKEN", csrf.getToken());
					cookie.setPath("/");
					response.addCookie(cookie);
				}
				filterChain.doFilter(request, response);
			}
		};
	}

}