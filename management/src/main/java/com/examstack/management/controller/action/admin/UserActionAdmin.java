package com.examstack.management.controller.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examstack.common.domain.exam.Message;
import com.examstack.common.domain.user.User;
import com.examstack.common.util.StandardPasswordEncoderForSha1;
import com.examstack.management.security.UserInfo;
import com.examstack.management.service.UserService;

@Controller
public class UserActionAdmin {
	
	@Autowired
	private UserService userService;

	
	/**
	 * 添加用户
	 * 
	 * @param user
	 * @param groupId
	 *            如果添加的用户为学员，必须指定groupId。如果添加的用户为教师，则groupId为任意数字
	 * @return
	 */
	@RequestMapping(value = { "/admin/add-user-{authority}-{groupId}" }, method = RequestMethod.POST)
	public @ResponseBody Message addUser(@RequestBody User user, @PathVariable String authority,
			@PathVariable Integer groupId) {
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user.setCreateTime(new Date()); 
		String password = user.getPassword() + "{" + user.getUserName().toLowerCase() + "}";
		PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
		String resultPassword = passwordEncoder.encode(password);
		user.setPassword(resultPassword);
		user.setEnabled(true);
		user.setCreateBy(userInfo.getUserid());
		user.setUserName(user.getUserName().toLowerCase());
		Message message = new Message();
		try {
			userService.addUser(user, authority, groupId, userInfo.getRoleMap());
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

	
	@RequestMapping(value = { "/admin/update-teacher" }, method = RequestMethod.POST)
	public @ResponseBody Message updateTeacher(@RequestBody User user) {
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user.setCreateTime(new Date());
		String password = user.getPassword() + "{" + user.getUserName() + "}";
		PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
		String resultPassword = "";
		if(user.getPassword() != null)
			resultPassword = "".equals(user.getPassword().trim()) ? "" : passwordEncoder.encode(password);
		user.setPassword(resultPassword);
		user.setEnabled(true);
		Message message = new Message();
		try {
			userService.updateUser(user, null);
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
	
	
	@RequestMapping(value = "/admin/change-teacher-status-{userId}-{enabled}", method = RequestMethod.GET)
	public @ResponseBody Message changeTeacherStatus(Model model,@PathVariable("userId") int userId,@PathVariable("enabled") boolean enabled){
		
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(userId);
		userService.changeUserStatus(idList, enabled);
		return new Message();
	}
}
