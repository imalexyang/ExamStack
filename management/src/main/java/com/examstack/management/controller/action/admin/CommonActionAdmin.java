package com.examstack.management.controller.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examstack.common.domain.exam.Message;
import com.examstack.common.domain.question.Field;
import com.examstack.common.domain.question.KnowledgePoint;
import com.examstack.common.domain.question.Tag;
import com.examstack.common.domain.user.Department;
import com.examstack.management.security.UserInfo;
import com.examstack.management.service.QuestionService;
import com.examstack.management.service.UserService;

@Controller
public class CommonActionAdmin {
	@Autowired
	private QuestionService questionService;
	@Autowired
	private UserService userService;
	
	/**
	 * 添加专业
	 * @param field
	 * @return
	 */
	@RequestMapping(value = "/admin/common/field-add", method = RequestMethod.POST)
	public @ResponseBody Message addField(@RequestBody Field field){
		
		Message message = new Message();
		try{
			questionService.addField(field);
		}catch(Exception e){
			message.setResult(e.getClass().getName());
			e.printStackTrace();
		}
		
		return message;
	}
	
	/**
	 * 添加知识点
	 * @param point
	 * @return
	 */
	@RequestMapping(value = "/admin/common/point-add", method = RequestMethod.POST)
	public @ResponseBody Message addPoint(@RequestBody KnowledgePoint point){
		
		Message message = new Message();
		try{
			questionService.addKnowledgePoint(point);
		}catch(Exception e){
			message.setResult(e.getClass().getName());
			e.printStackTrace();
		}
		
		return message;
	}
	
	/**
	 * 添加标签
	 * @param tag
	 * @return
	 */
	@RequestMapping(value = "/admin/common/tag-add", method = RequestMethod.POST)
	public @ResponseBody Message addTag(@RequestBody Tag tag){
		
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		tag.setCreator(userInfo.getUserid());
		Message message = new Message();
		try{
			questionService.addTag(tag);
		}catch(Exception e){
			message.setResult(e.getClass().getName());
			e.printStackTrace();
		}
		
		return message;
	}
	
	/**
	 * 添加专业
	 * @param field
	 * @return
	 */
	@RequestMapping(value = "/admin/common/dep-add", method = RequestMethod.POST)
	public @ResponseBody Message addDep(@RequestBody Department dep){
		
		Message message = new Message();
		try{
			userService.addDep(dep);
		}catch(Exception e){
			message.setResult(e.getClass().getName());
			e.printStackTrace();
		}
		
		return message;
	}
	
	/**
	 * 获取专业下的知识点数据
	 * @param fieldId
	 * @return
	 */
	@RequestMapping(value = "/admin/common/get-knowledge-point/{fieldId}", method = RequestMethod.GET)
	public @ResponseBody
	Message getQuestionPointByFieldId(@PathVariable int fieldId) {
		Message message = new Message();
		try {
			HashMap<Integer, String> pointMap = new HashMap<Integer, String>();
			List<KnowledgePoint> pointList = questionService
					.getKnowledgePointByFieldId(fieldId,null);
			for (KnowledgePoint point : pointList) {
				pointMap.put(point.getPointId(), point.getPointName());
			}
			message.setObject(pointMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message.setResult(e.getClass().getName());
		}
		return message;
	}
		
	/**
	 * 删除一个专业
	 * @param model
	 * @param fieldId
	 * @return
	 */
	@RequestMapping(value = "/admin/common/delete-field-{fieldId}", method = RequestMethod.GET)
	public @ResponseBody Message deleteField(Model model,@PathVariable("fieldId") int fieldId){
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(fieldId);
		Message msg = new Message();
		try {
			questionService.deleteFieldByIdList(idList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.setResult(e.getClass().getName());
		}
		return msg;
		
	}
	
	/**
	 * 删除一个知识分类
	 * @param model
	 * @param pointId
	 * @return
	 */
	@RequestMapping(value = "/admin/common/delete-point-{pointId}", method = RequestMethod.GET)
	public @ResponseBody Message deleteKnowledgePoint(Model model,@PathVariable("pointId") int pointId){
		//TO.DO 严欢完善下
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(pointId);
		Message msg = new Message();
		try {
			questionService.deleteKnowledgePointByIdList(idList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.setResult(e.getClass().getName());
		}
		return msg;
		
	}
	
	/**
	 * 删除一个tag
	 * @param model
	 * @param tagId
	 * @return
	 */
	@RequestMapping(value = "/admin/common/tag-delete-{tagId}", method = RequestMethod.GET)
	public @ResponseBody Message deleteTag(Model model,@PathVariable("tagId") int tagId){
		
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(tagId);
		
		Message msg = new Message();
		try {
			questionService.deleteTagByIdList(idList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.setResult(e.getClass().getName());
		}
		return msg;
	}
	
	/**
	 * 删除一个专业
	 * @param model
	 * @param fieldId
	 * @return
	 */
	@RequestMapping(value = "/admin/common/delete-dep-{depId}", method = RequestMethod.GET)
	public @ResponseBody Message deleteDep(Model model,@PathVariable("depId") int depId){
		
		Message msg = new Message();
		try {
			userService.deleteDep(depId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.setResult(e.getClass().getName());
		}
		return msg;
	}
}
