package com.examstack.management.controller.action.admin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examstack.common.domain.exam.Message;
import com.examstack.common.domain.news.News;
import com.examstack.common.domain.user.User;
import com.examstack.common.util.StandardPasswordEncoderForSha1;
import com.examstack.management.security.UserInfo;
import com.examstack.management.service.NewsService;
import com.examstack.management.service.UserService;

@Controller
public class SystemActionAdmin {
	
	@Autowired
	private UserService userService;
	@Autowired
	private NewsService newsService;
	/**
	 * 添加用户
	 * 
	 * @param user
	 * @param groupId
	 *            如果添加的用户为学员，必须指定groupId。如果添加的用户为教师，则groupId为任意数字
	 * @return
	 */
	@RequestMapping(value = { "/admin/add-admin" }, method = RequestMethod.POST)
	public @ResponseBody Message addUser(@RequestBody User user) {
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Message message = new Message();
		if(!userInfo.getAuthorities().toString().contains("ROLE_ADMIN")){
			message.setResult("权限错误！");
			message.setMessageInfo("只有管理员才可以添加管理员！");
			return message;
		}
		user.setCreateTime(new Date()); 
		String password = user.getPassword() + "{" + user.getUserName().toLowerCase() + "}";
		PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
		String resultPassword = passwordEncoder.encode(password);
		user.setPassword(resultPassword);
		user.setEnabled(true);
		user.setCreateBy(userInfo.getUserid());
		user.setUserName(user.getUserName().toLowerCase());
		
		try {
			userService.addUser(user, "ROLE_ADMIN", -1, userInfo.getRoleMap());
		} catch (Exception e) {
			// TODO Auto-generated catch block

			if(e.getMessage().contains(user.getUserName())){
				message.setResult("duplicate-username");
				message.setMessageInfo("重复的用户名");
			} else if(e.getMessage().contains(user.getNationalId())){
				message.setResult("duplicate-national-id");
				message.setMessageInfo("重复的身份证");
			} else if(e.getMessage().contains(user.getEmail())){
				message.setResult("duplicate-email");
				message.setMessageInfo("重复的邮箱");
			} else if(e.getMessage().contains(user.getPhoneNum())){
				message.setResult("duplicate-phone");
				message.setMessageInfo("重复的电话");
			} else{
				message.setResult(e.getCause().getMessage());
				e.printStackTrace();
			}
		}
		return message;
	}
	
	@RequestMapping(value = { "/admin/add-news" }, method = RequestMethod.POST)
	public @ResponseBody Message addUser(@RequestBody News news){
		Message msg = new Message();
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		try {
			news.setUserId(userInfo.getUserid());
			newsService.addNews(news);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setResult(e.getClass().getName());
			e.printStackTrace();
		}
		return msg;
	}
}
