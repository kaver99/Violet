package com.violet.web.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("userRoleEntity")
public class UserRoleEntity implements GrantedAuthority {
    private static final long serialVersionUID = 1L;

    private String username;
    private String socialType;
    private String authority;
    private String role_name;
 
    
    public List<String> userRoleToString() {
    	ArrayList<String> list = new ArrayList<String>();
    	
    	list.add("username : " + username);
    	list.add("authority : " + authority);
    	list.add("role_name : " + role_name);
    	
    	return list;
    }
    
}