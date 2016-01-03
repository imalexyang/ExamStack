package com.examstack.portal.service;

import java.util.HashMap;
import java.util.List;

import com.examstack.common.domain.user.Department;
import com.examstack.common.domain.user.Group;
import com.examstack.common.domain.user.Role;
import com.examstack.common.domain.user.User;
import com.examstack.common.util.Page;

/**
 * @author Ocelot
 * @date 2014年6月8日 下午5:52:55
 */
public interface UserService {

	/**
	 * 添加一个用户，并授权。如果授权为（教师），则groupId无意义，如果授权为（学员）,groupId为教师管理的分组之一
	 * 
	 * @param user
	 * @param authority
	 * @param groupId
	 * @param roleMap
	 * @return
	 */
	public int addUser(User user, String authority, int groupId, HashMap<String, Role> roleMap);

	public void updateUser(User user, String oldPassword);
	
	/**
	 * 修改用户密码
	 * @param user
	 * @param olePwd
	 */
	public void updateUserPwd(User user, String oldPwd);

	/**
	 * 获取
	 * 
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<Group> getGroupListByUserId(int userId, Page<Group> page);

	
	/**
	 * 获取所有的角色并生成字典
	 * 
	 * @return
	 */
	public HashMap<String, Role> getRoleMap();

	/**
	 * 更新用户状态
	 * 
	 * @param idList
	 * @param enabled
	 */
	public void changeUserStatus(List<Integer> idList, boolean enabled);

	
	/**
	 * 添加用户到分组
	 * 
	 * @param userId
	 * @param groupId
	 */
	public void addUserGroup(int userId, int groupId);

	/**
	 * 删除分组
	 * @param userId
	 * @param groupId
	 * @param managerId 只能删除自己管理的分组中的数据
	 */
	public void deleteUserGroup(int userId, int groupId, int managerId);
	
	/**
	 * 获取所有部门信息
	 * @param page
	 * @return
	 */
	public List<Department> getDepList(Page<Department> page);
	
	/**
	 * 获取用户
	 * @param userName
	 * @return
	 */
	public User getUserByName(String userName);
}
