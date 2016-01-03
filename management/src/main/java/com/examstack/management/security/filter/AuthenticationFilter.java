package com.examstack.management.security.filter;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.examstack.common.domain.user.User;
import com.examstack.common.util.StandardPasswordEncoderForSha1;
import com.examstack.management.security.UserInfo;
import com.examstack.management.service.UserService;

/**
 * 2013-7-13
 * @author scar
 * 重写以添加验证码工具
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public static final String VALIDATE_CODE = "validate_code";
	public static final String USERNAME = "j_username";
	public static final String PASSWORD = "j_password";
	//public static final String KAPTCHA_SESSION_KEY = com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

	private static Logger log = Logger.getLogger(AuthenticationFilter.class);
	@Autowired
	public UserDetailsService userDetailsService;
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		if(!request.getMethod().equals("POST")){
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		String username = this.obtainUsername(request);
		String password = this.obtainPassword(request);
		
		//加盐
		String sh1Password = password + "{" + username + "}";
		PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
		String result = passwordEncoder.encode(sh1Password);
		log.info(result);
		UserInfo userDetails = (UserInfo) userDetailsService.loadUserByUsername(username);
		
		
		/*this.checkValidateCode(request);*/
		if(!passwordEncoder.matches(userDetails.getPassword(), result) || "0".equals(userDetails.getEnabled()) || userDetails == null){
			//System.out.println("用户名或密码错误！");
			throw new AuthenticationServiceException("用户名或密码错误！");
		}
		if(!userDetails.getRolesName().contains("ROLE_ADMIN") && !userDetails.getRolesName().contains("ROLE_TEACHER")){
			throw new AuthenticationServiceException("非管理用户，操作无效！");
		}
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		this.setDetails(request, authRequest);
		Authentication authentication = null;
		try{
			authentication = this.getAuthenticationManager().authenticate(authRequest);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return authentication;
	}
	
	protected void checkValidateCode(HttpServletRequest request){
		HttpSession session = request.getSession();
		
		String sessionValidateCode = this.obtainSessionValidateCode(session);
		//将验证码session清空
		//session.setAttribute(KAPTCHA_SESSION_KEY, null);
		String validateCodeParameter = this.obtainValidateCodeParameter(request);
		
		if(StringUtils.isEmpty(validateCodeParameter) || !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)){
			//System.out.println("验证码错误！sessionValidateCode=" + sessionValidateCode + "  validateCodeParameter=" + validateCodeParameter);
			throw new AuthenticationServiceException("验证码错误！");
		}
	}

	protected String obtainValidateCodeParameter(HttpServletRequest request){
        Object obj = request.getParameter(VALIDATE_CODE);
        return null == obj ? "" : obj.toString().trim().toUpperCase();
    }

    protected String obtainSessionValidateCode(HttpSession session){
        //Object obj = session.getAttribute(KAPTCHA_SESSION_KEY);
        //return null == obj ? "" : obj.toString();
    	return null;
    }
    
    @Override
	protected String obtainPassword(HttpServletRequest request) {
		// TODO Auto-generated method stub
    	Object obj = request.getParameter(PASSWORD);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Object obj = request.getParameter(USERNAME);
		return null == obj ? "" : obj.toString().trim().toLowerCase();
	}
}
