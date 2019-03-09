package com.violet.web.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Mapper
public class UserRepository implements UserService {

	private static Logger log = LoggerFactory.getLogger(UserRepository.class);
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Override
	public UserEntity findByUserName(Map<String, String> userData) {
		UserEntity userEntity = null;
		
		try {
			userEntity = sqlSession.selectOne("userMapper.findByUsername", userData);
			// Role Setting
			userEntity.setAuthorities(findByUserRole(userData));
			
		} catch (Exception e) {
			log.error("User Find Error : " + e);
			
		}
		return userEntity;
	}
	
	/**
	 * Role Access Data
	 * USER, MANAGER, ADMIN
	 * @param userData
	 * @return authorities
	 */
	public List<GrantedAuthority> findByUserRole(Map<String, String> userData) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(); 
		
		try {
			List<String> userRole = sqlSession.selectList("userMapper.findByUserRole", userData);
			
			for (String authority : userRole) { 
				authorities.add(new SimpleGrantedAuthority("ROLE_"+authority));
				
			}
			
		} catch (Exception e) {
			log.error("User Role Find Error : " + e);
			
		}
		
		return authorities;
	}
	
	
	@Override
	public int findByUserNameCount(Map<String, String> userData) {
		int dataCnt = 0;
		
		try {
			dataCnt = sqlSession.selectOne("userMapper.findByUserNameCount", userData);
			
		} catch (Exception e) {
			log.error("User Count Error : " + e);
			
		}
		return dataCnt;
	}
	
	@Transactional(rollbackFor=SQLException.class)
	@Override
	public int signupUser(UserEntity userEntity, UserRoleEntity userRoleEntity, UserAddrEntity userAddrEntity) {
		
		// AutoCommit false
//		sqlSession = sqlSessionFactory.openSession(false);

		int dataCnt = 0;
		int roleCnt = 0;
		
		try {
			dataCnt = sqlSession.insert("userMapper.signupUser", userEntity);
			
			if(dataCnt > 0) {
				roleCnt = signupUserRole(userRoleEntity);
				
				if(roleCnt > 0 && userAddrEntity.getPost_code() != null) {
					signupAddr(userAddrEntity);
				}
			}
		
		} catch (Exception e) {
			log.error("User Insert Error : " + e);
//			sqlSession.rollback(true);
			
		} finally {
//			sqlSession.commit(true);
//			sqlSession.close();
			
		}
			
		return dataCnt;
	}

	@Override
	public int signupAddr(UserAddrEntity userAddrEntity) throws SQLException {
		return sqlSession.insert("userMapper.signupAddr", userAddrEntity);
	}

	@Override
	public int signupUserRole(UserRoleEntity userRoleEntity) throws SQLException {
		return sqlSession.insert("userMapper.signupUserRole", userRoleEntity);
	}


	@Override
	public int userAccessDateUpdate(String username, String socialType) {
		// AutoCommit false
		sqlSession = sqlSessionFactory.openSession(false);
		
		int dataCnt = 0;
		Map<String, String> userData = new HashMap<String, String>();
		userData.put("username", username);
		userData.put("socialType", socialType);
		
		try {
			dataCnt = sqlSession.insert("userMapper.userAccessDateUpdate", userData);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("User Role Insert Error : " + e);
			
		}
		
		return dataCnt;
	}
	
	@Override
	public UserAddrEntity findByUserAddr(String username, String socialType) {
		UserAddrEntity userAddrEntity = null;
		
		Map<String, String> userData = new HashMap<String, String>();
		userData.put("username", username);
		userData.put("socialType", socialType);
		
		try {
			userAddrEntity = sqlSession.selectOne("userMapper.findByUserAddr", userData);
			
		} catch (Exception e) {
			log.error("User Address Find Error : " + e);
			
		}
		return userAddrEntity;
	}
	
}
