package com.violet.web.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("UserEntity")
public class UserEntity implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private String username;
    private String password;
    private String name;
    private String phone;
    private String telecom; 
    private String create_date;
    private String access_date;
    private String img_thumbnail;
	private boolean accountNonExpired = true; 
	private boolean accountNonLocked = true; 
	private boolean credentialsNonExpired = true; 
	private boolean enabled = true;
	private List<GrantedAuthority> authorities;
	private String remoteIp;
	private String socialType;
	private String socialId;
	private char term_ServiceYn;
	private char per_InfomationYn;
	private char event_AlarmYn;
	
	
	@Override
	public List<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
	public List<String> userEntityToString() {
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("username : " + username);
		list.add("password : " + password);
		list.add("name : " + name);
		list.add("phone : " + phone);
		list.add("telecom : " + telecom);
		list.add("img_thumbnail : " + img_thumbnail);
		
		return list;
		
	}
}
