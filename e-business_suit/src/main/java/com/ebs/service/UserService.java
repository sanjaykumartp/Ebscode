package com.ebs.service;


import java.util.List;

import com.ebs.entity.GroupData;
import com.ebs.entity.User;
import com.ebs.model.UserModel;

public interface UserService {

	User register(UserModel userModel);

	User getUserByUserName(String userName);

	GroupData newGroup(GroupData groupData);

	GroupData getGroupDataByGroupName(String groupName);
	
	void deleteGroupbyName(String groupName);

	GroupData assignPrograms(String groupName, GroupData groupData);

	GroupData modifyGroup(String groupName, GroupData groupData);

	List<GroupData> getAllGroupCreations();
}
