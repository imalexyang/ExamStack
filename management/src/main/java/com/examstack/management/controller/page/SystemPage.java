package com.examstack.management.controller.page;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examstack.management.service.UserService;



@Controller
public class SystemPage {

	@Autowired
	private UserService userService;

	/**
	 * 系统备份页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/sys-backup", method = RequestMethod.GET)
	private String sysBackUpPage(Model model, HttpServletRequest request) {
		return "sys-backup";
	}

	
}



















