package com.examstack.portal.security.filter;

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

import com.examstack.common.domain.exam.Exam;
import com.examstack.common.domain.exam.ExamHistory;
import com.examstack.common.util.StandardPasswordEncoderForSha1;
import com.examstack.portal.security.UserDetailsServiceImpl;
import com.examstack.portal.security.UserInfo;
import com.examstack.portal.service.ExamService;

/**
 * 2013-7-13
 * @author scar
 * 重写以添加验证码工具
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public static final String VALIDATE_CODE = "validate_code";
	public static final String USERNAME = "j_username";
	public static final String PASSWORD = "j_password";
	public static final String SERI_NO = "j_seri_no";
	public static final String FLAG = "j_flag";
	//public static final String KAPTCHA_SESSION_KEY = com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

	private static Logger log = Logger.getLogger(AuthenticationFilter.class);
	@Autowired
	public ExamService examService;
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		String seriNo = this.obtainSeriNoParameter(request);
		String username = this.obtainUsername(request);
		String password = this.obtainPassword(request);
		String flag = this.obtainFlagParameter(request);
		
		//加盐
		String sh1Password = password + "{" + username + "}";
		PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
		String result = passwordEncoder.encode(sh1Password);
		log.info(result);
		if(!request.getMethod().equals("POST")){
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		if(seriNo != null && !"".equals(seriNo)){
			//准考证直接登录，需要验证准考证有效时间（只能考试期间登录）
			//准考证号对应的考试历史id需要保存到userInfo中，登录成功后，如果发现userInfo中考试历史id，则直接跳转到考试页面
			//跳转成功后清空该属性
			ExamHistory hist = examService.getUserExamHistBySeriNo(seriNo,1);
			if(hist != null){
				username = hist.getUserName();
				password = "";
			}else{
				throw new AuthenticationServiceException("准考证号码错误！");
			}
			
			Exam exam = examService.getExamById(hist.getExamId());
			if(exam.getApproved() == 0)
				throw new AuthenticationServiceException("无法参加一个未审核通过的考试！");
			Date now = new Date();
			if(exam.getEffTime().after(now) || exam.getExpTime().before(now))
				throw new AuthenticationServiceException("不在考试时间范围内，不允许使用准考证！");
			if(hist.getApproved() == 0)
				throw new AuthenticationServiceException("考试申请未审核，请联系管理员！");
			if(hist.getApproved() == 2)
				throw new AuthenticationServiceException("考试申请审核未通过，不能参加考试！");
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
			this.setDetails(request, authRequest);
			Authentication authentication = null;
			try{
				authentication = this.getAuthenticationManager().authenticate(authRequest);
				UserInfo userInfo = (UserInfo)authentication.getPrincipal();
				userInfo.setHistId(hist.getHistId());
				userInfo.setExamId(hist.getExamId());
				userInfo.setExamPaperId(hist.getExamPaperId());
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return authentication;
		} else {
			if("1".equals(flag)){
				throw new AuthenticationServiceException("准考证号码错误！");
			}
			if(!"".equals(username) && "".equals(password))
				throw new AuthenticationServiceException("请输入密码！");
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
			this.setDetails(request, authRequest);
			Authentication authentication = null;
			try {
				authentication = this.getAuthenticationManager().authenticate(authRequest);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new AuthenticationServiceException("用户名密码错误！");
			}
			UserInfo userDetails = (UserInfo)authentication.getPrincipal();
			
			if(!userDetails.getRolesName().contains("ROLE_STUDENT")){
				throw new AuthenticationServiceException("管理用户请从后台管理页面登录！");
			}
			return authentication;
		}
		
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
	
	protected String obtainSeriNoParameter(HttpServletRequest request){
        Object obj = request.getParameter(SERI_NO);
        return null == obj ? "" : obj.toString().trim().toUpperCase();
    }
	
	protected String obtainFlagParameter(HttpServletRequest request){
        Object obj = request.getParameter(FLAG);
        return null == obj ? "" : obj.toString().trim().toUpperCase();
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
