package com.examstack.management.controller.page.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.examstack.common.domain.news.News;
import com.examstack.common.domain.user.Department;
import com.examstack.common.domain.user.User;
import com.examstack.common.util.Page;
import com.examstack.common.util.PagingUtil;
import com.examstack.management.security.UserInfo;
import com.examstack.management.service.NewsService;
import com.examstack.management.service.UserService;

@Controller
public class SystemPageAdmin {
	
	@Autowired
	private UserService userService;
	@Autowired
	private NewsService newsService;
	/**
	 * 管理员列表
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/system/admin-list", method = RequestMethod.GET)
	private String adminListPage(Model model, HttpServletRequest request) {
		int index = 1;
		if (request.getParameter("page") != null)
			index = Integer.parseInt(request.getParameter("page"));
		Page<User> page = new Page<User>();
		page.setPageNo(index);
		page.setPageSize(10);
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Department> depList = userService.getDepList(null);
		List<User> userList = userService.getUserListByRoleId(userInfo.getRoleMap().get("ROLE_ADMIN").getRoleId(), page);
		String pageStr = PagingUtil.getPagelink(index, page.getTotalPage(), "", "admin/system/admin-list");
		model.addAttribute("depList", depList);
		model.addAttribute("userList", userList);
		model.addAttribute("pageStr", pageStr);
		return "sys-admin-list";
	}
	
	/**
	 * 添加管理员
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/system/admin-add", method = RequestMethod.GET)
	private String adminAddPage(Model model, HttpServletRequest request) {
		return "";
	}
	
	/**
	 * 数据备份
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/system/backup", method = RequestMethod.GET)
	private String backupPage(Model model, HttpServletRequest request) {
		return "";
	}
	
	/**
	 * 系统公告
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/system/news-list", method = RequestMethod.GET)
	private String newsListPage(Model model, HttpServletRequest request, @RequestParam(value="page",required=false,defaultValue="1") int page) {
		
		Page<News> pageModel = new Page<News>();
		pageModel.setPageNo(page);
		pageModel.setPageSize(10);
		List<News> newsList = newsService.getNewsList(pageModel);
		
		String pageStr = PagingUtil.getPagelink(page, pageModel.getTotalPage(), "", "admin/system/news-list");
		model.addAttribute("newsList", newsList);
		model.addAttribute("pageStr", pageStr);
		return "news-list";
	}
	
	/**
	 * 发布公告
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/system/news-add", method = RequestMethod.GET)
	private String newsAddPage(Model model, HttpServletRequest request) {
		return "";
	}
}
