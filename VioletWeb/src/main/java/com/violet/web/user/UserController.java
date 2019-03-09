package com.violet.web.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.violet.web.util.MultipartImgUploader;

@Controller
public class UserController {
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * Login Page
	 * @param request
	 * @return
	 */
	@GetMapping("/login.violet")
	public String loginGet(HttpServletRequest request) {
//		// 이전페이지 호출
//		String referer = request.getHeader("Referer");
//		request.getSession().setAttribute("prevPage", referer);
		return "login/login";
	}
	
	
	@RequestMapping("/login-error.violet")
	public String loginError(HttpServletRequest request) {
		return "login/login";
	    
	}
	
	
	/**
	 * Signup page Show
	 * @return Signup page
	 */
	@RequestMapping("/signup.violet")
	public String showSignup() {
		return "login/signup/signup";
	}
	
	
	/**
	 * Signup Submit Request
	 * @return Login page
	 */
	@PostMapping("/signup.violet")
	public String submitSignup(HttpServletRequest request
							  ,@RequestPart MultipartFile imageUpload
							  ,@RequestParam("terms-of-service-chkbox") char term_ServiceYn
							  ,@RequestParam("personal-information-chkbox") char per_InfomationYn
							  ,@RequestParam("event-alarm-chkbox") char event_AlarmYn
							  ,@RequestParam("username") String username
							  ,@RequestParam("password") String password
							  ,@RequestParam("name") String name
							  ,@RequestParam("phone") String phone
							  ,@RequestParam("telecom") String telecom
							  ,@RequestParam("post_code") String post_code
							  ,@RequestParam("addr") String addr
							  ,@RequestParam("sub_addr") String sub_addr) {

		// Data Submit Process
		// 1. File Upload
		UserEntity userEntity = new UserEntity();
		UserAddrEntity userAddrEntity = new UserAddrEntity();
		UserRoleEntity userRoleEntity = new UserRoleEntity();
		MultipartImgUploader imgUploadUtil = new MultipartImgUploader();
		String destinationFilePath = "";
		
		try {
			if(!imageUpload.isEmpty()) {
				destinationFilePath = imgUploadUtil.imgUploader(imageUpload);
				if(destinationFilePath.equalsIgnoreCase("Upload-Error")) {
					request.setAttribute("status", "error");
					request.setAttribute("errMsg", "Thumbnail Image Upload Fail.");
					return "login/signup/signup";
				}
			}
			// 2. User Data insert (User + Role + Address Data Insert 구현 필요)
			userEntity.setUsername(username);
			userEntity.setName(name);
			userEntity.setPassword((String)Sha512DigestUtils.shaHex(password.getBytes()));
			userEntity.setPhone(phone);
			userEntity.setTelecom(telecom);
			userEntity.setImg_thumbnail(destinationFilePath);
			userEntity.setSocialType("local");
			userEntity.setSocialId("");
			
			// 3. User Role insert
			userRoleEntity.setUsername(username);
			userRoleEntity.setAuthority("USER");
			userRoleEntity.setRole_name("고객");
			userRoleEntity.setSocialType(userEntity.getSocialType());
			
			// 4. User Default Address Data insert
			if(post_code.length() > 0) {
				userAddrEntity.setUsername(username);
				userAddrEntity.setSocialType(userEntity.getSocialType());
				userAddrEntity.setAddr_alias("Default");
				userAddrEntity.setPost_code(post_code);
				userAddrEntity.setAddr(addr);
				userAddrEntity.setSub_addr(sub_addr);
				
			}
			
			int userCnt = userService.signupUser(userEntity, userRoleEntity, userAddrEntity);
			
			if(userCnt == 0) {
				request.setAttribute("status", "error");
				request.setAttribute("errMsg", "There was an error registering the user.");
				return "login/signup/signup";
				
			}
				
		} catch (Exception e) {
			log.error("User Signup Fail : " + e);
			// error msg 전달 방식 적용 필요
			request.setAttribute("status", "error");
			request.setAttribute("errMsg", "Error occurred during user processing.");
			return "login/signup/signup";

		} 
		
		return "redirect:/login.violet";
	}
	
	
	/**
	 * UserName Ajax User Checker
	 * @ResponseBody : data return Method(view unused)
	 * @return Data Row Count Value
	 */
	@PostMapping("/signup/userChk.violet")
	@ResponseBody
	public String UserNameChk(@RequestParam String username) {
		
		Map<String, String> userData = new HashMap<String, String>();
		userData.put("username", username);
		userData.put("socialType", "local");
		int dataCnt = userService.findByUserNameCount(userData);
		return String.valueOf(dataCnt);
	}
	
	
	/**
	 * Profile page Show
	 * @return Profile page
	 */
	@GetMapping("/profile.violet")
	public String showProfile(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String socialType = (String) session.getAttribute("socialType");
		
		Map<String, String> userData = new HashMap<String, String>();
        userData.put("username", username);
        userData.put("socialType", socialType);
        // user search
		UserEntity userEntity = userService.findByUserName(userData);
		// user address search
		UserAddrEntity userAddrData = userService.findByUserAddr(username, socialType);
		
		request.setAttribute("userEntity", userEntity);
		request.setAttribute("userAddrData", userAddrData);
		
		return "login/profile/profile";
	}
	
	/**
	 * Profile UserData Modify
	 * @param request
	 * @return
	 */
	@PostMapping("/profile.violet")
	public String profilePost(HttpServletRequest request) {
		
		return "/";
		
	}
	
	
	/**
	 * Profile UserData Modify
	 * @param request
	 * @return
	 */
	@PostMapping("/emailCert.violet")
	public String emailCertPost(HttpServletRequest request) {
		
		return "";
		
	}

}
