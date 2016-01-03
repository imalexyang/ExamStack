package com.examstack.portal.controller.page;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examstack.common.util.MenuItem;
import com.examstack.portal.security.UserInfo;

@Controller
public class MenuPage {

	/**
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "common-page/top-menu" }, method = RequestMethod.GET)
	public String showTopMenuPage(Model model, HttpServletRequest request) {

		UserInfo userInfo = "anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal()) ? null : (UserInfo) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();


		List<MenuItem> list = new ArrayList<MenuItem>();

		if (userInfo != null) {
			LinkedHashMap<String, MenuItem> map = userInfo.getMenuMap();
			for (Map.Entry<String, MenuItem> entry : map.entrySet()) {
				list.add(entry.getValue());
			}
			model.addAttribute("topMenuList", list);
		}
		
		System.out.println(request.getParameter("topMenuId"));
		System.out.println(request.getParameter("leftMenuId"));
		model.addAttribute("topMenuId", request.getParameter("topMenuId"));
		model.addAttribute("leftMenuId", request.getParameter("leftMenuId"));
		
		return "common/top-menu";
	}

	@RequestMapping(value = { "common-page/left-menu" }, method = RequestMethod.GET)
	public String showLeftMenuPage(Model model, HttpServletRequest request) {
		
		String topMenuId =request.getParameter("topMenuId");
		String leftMenuId =request.getParameter("leftMenuId");

		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		

		List<MenuItem> list = new ArrayList<MenuItem>();

		LinkedHashMap<String, MenuItem> map = userInfo.getMenuMap().get(topMenuId).getChildMap();
		for (Map.Entry<String, MenuItem> entry : map.entrySet()) {
			list.add(entry.getValue());
		}

		model.addAttribute("leftMenuList", list);
		System.out.println(request.getParameter("topMenuId"));
		System.out.println(request.getParameter("leftMenuId"));
		model.addAttribute("topMenuId", topMenuId);
		model.addAttribute("leftMenuId", leftMenuId);

		return "common/left-menu";
		
		

	}

	@RequestMapping(value = { "common-page/footer" }, method = RequestMethod.GET)
	public String showFooterPage(Model model, HttpServletRequest request) {

		return "common/footer";
	}
}
