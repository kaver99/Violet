package com.violet.web.user;

import java.sql.SQLException;
import java.util.Map;

public interface UserService {

	public UserEntity findByUserName(Map<String, String> userData);

	public int findByUserNameCount(Map<String, String> userData);
	
	public int signupAddr(UserAddrEntity userAddrEntity) throws SQLException;

	public int signupUserRole(UserRoleEntity userRoleEntity) throws SQLException;

	int signupUser(UserEntity userEntity, UserRoleEntity userRoleEntity, UserAddrEntity userAddrEntity);
	
	int userAccessDateUpdate(String username, String socialType);
	
	public UserAddrEntity findByUserAddr(String username, String socialType);
}
