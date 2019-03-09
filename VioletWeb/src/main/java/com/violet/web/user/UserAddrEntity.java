package com.violet.web.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("UserAddrEntity")
public class UserAddrEntity {

	int idx;
	String username;
	String socialType;
	String addr_alias;
	String post_code;
	String addr;
	String sub_addr;
	
	public List<String> userAddrToString() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("idx : " + idx);
		list.add("username : " + username);
		list.add("addr_alias : " + addr_alias);
		list.add("post_code : " + post_code);
		list.add("addr : " + addr);
		list.add("sub_addr : " + sub_addr);
		
		return list;
	}
	
	
}