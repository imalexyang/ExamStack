package com.examstack.management.controller.page;

import java.util.Collection;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.examstack.common.util.MenuItem;
import com.examstack.management.security.UserInfo;
import com.examstack.management.service.SystemService;
import com.examstack.management.service.UserService;


@Controller
public class BasePage {

	@Autowired
	private UserService userService;
	@Autowired
	private SystemService systemService;

	/**
	 * 网站首页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage(Model model, HttpServletRequest request) {

		return "redirect:home";
	}
	/**
	 * 用户登录界面
	 * @param model
	 * @param result
	 * @return
	 */
	@RequestMapping(value = { "/user-login-page" }, method = RequestMethod.GET)
	public String loginPage(Model model, @RequestParam(value = "result", required = false, defaultValue = "") String result) {
		if("failed".equals(result)){
			model.addAttribute("result", "无效的用户名或者密码");
		}
		return "login";
	}
	/**
	 * 管理员登陆
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/admin/home" }, method = RequestMethod.GET)
	public String adminHomePage(Model model, HttpServletRequest request) {

		return "redirect:/admin/dashboard";
	}
	
	
	/**
	 * 管理员登陆
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/teacher/home" }, method = RequestMethod.GET)
	public String teacherHomePage(Model model, HttpServletRequest request) {

		return "redirect:/teacher/dashboard";
	}

	/**
	 * 判断不同角色返回的页面
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = { "home" }, method = RequestMethod.GET)
	public String directToBaseHomePage(Model model, HttpServletRequest request) {


		if (SecurityContextHolder.getContext().getAuthentication() == null){
			return "login";
		}
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().endsWith("anonymousUser")){
			return "login";
		}
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<? extends GrantedAuthority> grantedAuthorities = userInfo.getAuthorities();
		//保存角色字典，用于根据角色代码返回角色id
		userInfo.setRoleMap(userService.getRoleMap());
		//这里获取用户的菜单信息
		if (grantedAuthorities.contains(new GrantedAuthorityImpl("ROLE_ADMIN"))) {
			LinkedHashMap<String,MenuItem> map = (LinkedHashMap<String, MenuItem>) systemService.getMenuItemsByAuthority("ROLE_ADMIN");
			userInfo.setMenuMap(map);
			return "redirect:admin/home";
		} else if (grantedAuthorities.contains(new GrantedAuthorityImpl("ROLE_TEACHER"))) {
			LinkedHashMap<String,MenuItem> map = (LinkedHashMap<String, MenuItem>) systemService.getMenuItemsByAuthority("ROLE_TEACHER");
			userInfo.setMenuMap(map);
			return "redirect:teacher/home";
		} else if (grantedAuthorities.contains(new GrantedAuthorityImpl("ROLE_STUDENT"))) {
			
			return "login";
		} else {
			return "login";
		}
	}
	
	
	@RequestMapping(value = "/user-detail/{userName}", method = RequestMethod.GET)
	public String userInfoPage(Model model, HttpServletRequest request, @PathVariable("userName") String userName) {

		return "redirect:/admin/home";
	}
	
	public enum UserType {
		admin, teacher, student;
	}

	
	
}








