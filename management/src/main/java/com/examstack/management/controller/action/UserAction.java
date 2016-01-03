package com.examstack.management.controller.action;

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
import com.examstack.common.domain.user.Group;
import com.examstack.common.domain.user.User;
import com.examstack.common.util.StandardPasswordEncoderForSha1;
import com.examstack.management.security.UserInfo;
import com.examstack.management.service.UserService;

@Controller
public class UserAction {
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
	@RequestMapping(value = { "/secure/add-user-{authority}-{groupId}" }, method = RequestMethod.POST)
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

	@RequestMapping(value = "/secure/add-group", method = RequestMethod.POST)
	public @ResponseBody Message addGroup(@RequestBody Group group) {

		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Message message = new Message();
		group.setUserId(userInfo.getUserid());
		try {
			userService.addGroup(group);
		} catch (Exception e) {
			e.printStackTrace();
			message.setResult(e.getClass().getName());
		}

		return message;
	}
	

	@RequestMapping(value = { "/secure/update-user" }, method = RequestMethod.POST)
	public @ResponseBody Message updateUser(@RequestBody User user) {
		//UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
	
	@RequestMapping(value = "/secure/reset-pwd-user/{password}/{userName}", method = RequestMethod.GET)
	public @ResponseBody Message resetUserPwd(Model model,@PathVariable("password") String password,@PathVariable("userName") String userName){
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String authority = "ROLE_TEACHER";
		if(userInfo.getAuthorities().toString().contains("ROLE_ADMIN"))
			authority = "ROLE_ADMIN";
		//TODO
		Message msg = new Message();
		try {
			userService.updateUserPwd(userName, password, authority);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.setResult(e.getMessage());
		}
		return msg;
	}
	
	@RequestMapping(value = "/secure/change-user-status-{userId}-{enabled}", method = RequestMethod.GET)
	public @ResponseBody Message changeUserStatus(Model model,@PathVariable("userId") int userId,@PathVariable("enabled") boolean enabled){
		
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(userId);
		userService.changeUserStatus(idList, enabled);
		return new Message();
	}
	
	@RequestMapping(value = "/secure/modify-group-{groupId}-{groupName}", method = RequestMethod.GET)
	public @ResponseBody Message modifyGroup(@PathVariable("groupId") int groupId,@PathVariable("groupName") String groupName){
		Message msg = new Message();
		try{
			userService.updateGroup(groupId, groupName);
			
		}catch(Exception e){
			msg.setResult(e.getClass().getName());
		}
		return msg;
	}
	
	@RequestMapping(value = "/secure/delete-group-{groupId}", method = RequestMethod.GET)
	public @ResponseBody Message deleteGroup(@PathVariable("groupId") int groupId){
		Message msg = new Message();
		try{
			userService.deleteGroup(groupId);
			
		}catch(Exception e){
			msg.setResult(e.getClass().getName());
		}
		return msg;
	}
	
	@RequestMapping(value = "/secure/add-user-group-{userId}-{groupId}", method = RequestMethod.GET)
	public @ResponseBody Message addUserGroup(@PathVariable("userId") int userId, @PathVariable("groupId") int groupId){
		Message msg = new Message();
		try{
			userService.addUserGroup(userId, groupId);
		}catch(Exception e){
			msg.setResult(e.getClass().getName());
		}
		return msg;
	}
	
	@RequestMapping(value = "/secure/add-user-group-{groupId}", method = RequestMethod.POST)
	public @ResponseBody Message addUserGroupWithUserNameList(@RequestBody String userNames,@PathVariable("groupId") int groupId){
		Message msg = new Message();
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try{
			String[] names = userNames.split(";");
			
			userService.addUsers2Group(names,groupId,userInfo.getRoleMap());
		}catch(Exception e){
			msg.setResult(e.getClass().getName());
		}
		return msg;
	}
	
	@RequestMapping(value = "/secure/delete-user-group-{userId}-{groupId}", method = RequestMethod.GET)
	public @ResponseBody Message addUserGroupWithUserNameList(@PathVariable("userId") int userId,@PathVariable("groupId") int groupId){
		Message msg = new Message();
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try{
						
			userService.deleteUserGroup(userId, groupId, userInfo.getUserid());
		}catch(Exception e){
			msg.setResult(e.getClass().getName());
		}
		return msg;
	}
}
