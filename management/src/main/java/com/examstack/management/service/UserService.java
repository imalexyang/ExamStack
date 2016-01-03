package com.examstack.management.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

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

	public List<User> getUserListByRoleId(int roleId, Page<User> page);

	public void updateUser(User user, String oldPassword);

	/**
	 * 获取
	 * 
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<Group> getGroupListByUserId(int userId, Page<Group> page);

	/**
	 * 添加一个分组
	 * 
	 * @param group
	 */
	public void addGroup(Group group);

	/**
	 * 更新分组
	 * 
	 * @param groupId
	 * @param groupName
	 */
	public void updateGroup(int groupId, String groupName);

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
	 * 删除分组
	 * 
	 * @param groupId
	 */
	public void deleteGroup(int groupId);

	/**
	 * 添加用户到分组
	 * 
	 * @param userId
	 * @param groupId
	 */
	public void addUserGroup(int userId, int groupId);

	/**
	 * 根据groupid和关键字来查询用户
	 * 
	 * @param groupId
	 *            0 则为全部用户
	 * @param searchStr
	 * @param page
	 * @return
	 */
	public List<User> getUserListByGroupIdAndParams(int groupId, String authority, String searchStr, Page<User> page);
	
	/**
	 * 将一批用户插入到用户分组
	 * @param userNames
	 * @param groupId
	 * @param roleMap
	 */
	public void addUsers2Group(String[] userNames,int groupId,HashMap<String,Role> roleMap);
	
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
	 * 新增部门
	 * @param dep
	 */
	public void addDep(Department dep);
	
	/**
	 * 更新部门
	 * @param dep
	 */
	public void updateDep(Department dep);
	
	/**
	 * 删除一个部门
	 * @param depId
	 */
	public void deleteDep(int depId);
	
	/**
	 * 重置用户密码
	 * @param userName
	 * @param password
	 */
	public void updateUserPwd(String userName,String password,String authority) throws Exception;
}
