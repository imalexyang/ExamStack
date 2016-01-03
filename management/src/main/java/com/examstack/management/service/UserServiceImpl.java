package com.examstack.management.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examstack.common.domain.user.Department;
import com.examstack.common.domain.user.Group;
import com.examstack.common.domain.user.Role;
import com.examstack.common.domain.user.User;
import com.examstack.common.util.Page;
import com.examstack.common.util.StandardPasswordEncoderForSha1;
import com.examstack.management.persistence.UserMapper;

/**
 * @author Ocelot
 * @date 2014年6月8日 下午8:21:31
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserMapper userMapper;

	@Override
	@Transactional
	public int addUser(User user,String authority,int groupId,HashMap<String,Role> roleMap) {
		try {
			int userId = -1;
			userMapper.insertUser(user);
			userId = user.getUserId();
			userMapper.grantUserRole(userId, roleMap.get(authority).getRoleId());
			if(user.getDepId() != 0 && user.getDepId() != -1)
				userMapper.addUser2Dep(userId, user.getDepId());
			if("ROLE_TEACHER".equals(authority)){
				Group group = new Group();
				group.setGroupName("默认分组");
				group.setDefaultt(true);
				group.setUserId(userId);
				userMapper.addGroup(group);
			}if("ROLE_STUDENT".equals(authority)){
				//只能给学员分配自己已经有的分组
				List<Group> groupList = userMapper.getGroupListByUserId(user.getCreateBy(), null);
				boolean flag = false;
				for(Group group : groupList){
					if(group.getGroupId() == groupId){
						flag = true;
						break;
					}
				}
				if(groupId != 0){
					if(flag){
						userMapper.addUserGroup(userId, groupId);
					}else
						throw new Exception("不能将学员分配给一个不存在的分组");
				}
				
			}
			return userId;
		} catch (Exception e) {
			String cause = e.getCause().getMessage();
			throw new RuntimeException(cause);
		}
	}
	
	@Override
	public List<User> getUserListByRoleId(int roleId,Page<User> page) {
		// TODO Auto-generated method stub
		List<User> userList = userMapper.getUserListByRoleId(roleId, page);
		return userList;
	}
	
	@Override
	@Transactional
	public void updateUser(User user, String oldPassword) {
		// TODO Auto-generated method stub
		try {
			userMapper.updateUser(user, oldPassword);
			
			if(user.getDepId() != -1){
				userMapper.deleteUser2Dep(user.getUserId());
				userMapper.addUser2Dep(user.getUserId(), user.getDepId());
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Group> getGroupListByUserId(int userId, Page<Group> page) {
		// TODO Auto-generated method stub
		return userMapper.getGroupListByUserId(userId, page);
	}

	@Override
	public void addGroup(Group group) {
		// TODO Auto-generated method stub
		userMapper.addGroup(group);
	}

	@Override
	public HashMap<String, Role> getRoleMap() {
		// TODO Auto-generated method stub
		List<Role> roleList = userMapper.getRoleList();
		HashMap<String,Role> map = new HashMap<String,Role>();
		for(Role r : roleList){
			map.put(r.getAuthority(), r);
		}
		return map;
	}

	@Override
	public void changeUserStatus(List<Integer> idList,boolean enabled) {
		// TODO Auto-generated method stub
		userMapper.changeUserStatus(idList, enabled);
	}

	@Override
	public void updateGroup(int groupId, String groupName) {
		// TODO Auto-generated method stub
		userMapper.updateGroup(groupId, groupName);
	}

	@Override
	public void deleteGroup(int groupId) {
		// TODO Auto-generated method stub
		userMapper.deleteGroup(groupId);
	}

	@Override
	public void addUserGroup(int userId, int groupId) {
		// TODO Auto-generated method stub
		userMapper.addUserGroup(userId, groupId);
	}

	@Override
	public List<User> getUserListByGroupIdAndParams(int groupId, String authority, String searchStr, Page<User> page) {
		// TODO Auto-generated method stub
		return userMapper.getUserListByGroupIdAndParams(groupId, authority, searchStr, page);
	}

	@Override
	public void addUsers2Group(String[] userNames, int groupId,HashMap<String,Role> roleMap) {
		// TODO Auto-generated method stub
		List<User> userList = userMapper.getUserByNames(userNames,roleMap.get("ROLE_STUDENT").getRoleId());
		List<Integer> idList = new ArrayList<Integer>();
		for(User user : userList){
			idList.add(user.getUserId());
		}
		userMapper.addUsers2Group(idList, groupId);
	}

	@Override
	public void deleteUserGroup(int userId, int groupId, int managerId) {
		// TODO Auto-generated method stub
		userMapper.deleteUserGroup(userId, groupId, managerId);
	}

	@Override
	public List<Department> getDepList(Page<Department> page) {
		// TODO Auto-generated method stub
		return userMapper.getDepList(page);
	}

	@Override
	public void addDep(Department dep) {
		// TODO Auto-generated method stub
		userMapper.addDep(dep);
	}

	@Override
	public void updateDep(Department dep) {
		// TODO Auto-generated method stub
		userMapper.updateDep(dep);
	}

	@Override
	public void deleteDep(int depId) {
		// TODO Auto-generated method stub
		userMapper.deleteDep(depId);
	}

	@Override
	public void updateUserPwd(String userName, String password, String authority) throws Exception {
		// TODO Auto-generated method stub
		User user = userMapper.getUserByName(userName);
		if(user.getRoles().contains(authority) && !"ROLE_ADMIN".equals(authority))
			throw new Exception("教师只能更新学员的密码！");
		PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
		password = passwordEncoder.encode(password + "{" + userName + "}");
		User tmpUser = new User();
		tmpUser.setUserId(user.getUserId());
		tmpUser.setPassword(password);
		userMapper.updateUser(tmpUser, null);
		
	}
}
