package com.ebs.service;

import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ebs.custom.service.CustomUserDetails;
import com.ebs.entity.GroupData;
import com.ebs.entity.User;
import com.ebs.model.UserModel;
import com.ebs.repository.GroupRepository;
import com.ebs.repository.UserRepository;




@Service
public class UserServiceImplimentation implements UserService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	GroupRepository groupRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User register(UserModel userModel) {
		System.out.println("Register Method Calling");
		User user = new User();
		if (!(userRepo.findByEmail(userModel.getEmail())==null)) {
			throw new DuplicateKeyException("Email ID Already Exsists");
		}else if (!(userRepo.findByUserName(userModel.getUserName())==null)) {
			throw new DuplicateKeyException("UserName Already Exsists");
		}else {
			user.setEmail(userModel.getEmail().toLowerCase());
			user.setUserName(userModel.getUserName().toLowerCase());
			user.setRole("ROLE_"+userModel.getRole().toUpperCase());
			if (userModel.getPassword().equals(userModel.getMatchingPassword())) {
				user.setPassword(passwordEncoder.encode(userModel.getPassword()));
			}else {
				throw new InputMismatchException();
			}
//			user.setPassword(passwordEncoder.encode(userModel.getPassword()));
			userRepo.save(user);
			
		}
		return user;
	}


	@Override
	public User getUserByUserName(String userName) {
		User user = userRepo.findByUserName(userName);
		return user;
	}
	/*
	 * Creating a group, if Group is already exist in the database it shows exception
	 */
	@Override
	public GroupData newGroup(GroupData groupData) {
		GroupData savedGroup = groupData;
		if (!(groupRepository.findByGroupName(savedGroup.getGroupName()) == null))
		{
			throw new DuplicateKeyException("GroupName Already Exsists");
		}else {
		  savedGroup=groupRepository.save(groupData);
		}
		return savedGroup;
	}
	/*
	 * list of groups
	 */
	@Override
	public List<GroupData> getAllGroupCreations() {
		List<GroupData> groupList = null;
		groupList = groupRepository.findAll();
		return groupList;
	}

	/*
	 * Deleting a Group using GroupName
	 */
	@Override
	public void deleteGroupbyName(String groupName) {
	GroupData gc =	groupRepository.findByGroupName(groupName);
	groupRepository.delete(gc);
	}
	/*
	 * Assign Programs to the group
	 */
	@Override
	public GroupData assignPrograms(String groupName,  GroupData groupData) {
		GroupData savedPrograms = groupData;
		savedPrograms=groupRepository.findByGroupName(savedPrograms.getGroupName());
		savedPrograms.setGroupName(groupData.getGroupName());
		savedPrograms.setAssignPrograms(groupData.getAssignPrograms());
		
		groupRepository.save(savedPrograms);
		
		return savedPrograms;
	}
	/*
	 * Modify the Group 
	 */
	@Override
	public GroupData modifyGroup(String groupName, GroupData groupData) {
		//List<GroupCreation> groups=null;
		GroupData modifySaved = groupData;
		if(groupRepository.findAll() != null) {
		//GroupCreation modifySaved = groupCreation;
		modifySaved=groupRepository.findByGroupName(modifySaved.getGroupName());
		modifySaved.setGroupName(groupData.getGroupName());	
		modifySaved.setAssignPrograms(groupData.getAssignPrograms());
		modifySaved.setDescription(groupData.getDescription());
		groupRepository.save(modifySaved);
		}else {
			throw new DuplicateKeyException("Group is Not Exsists");
		}
		return modifySaved;
	}


	@Override
	public GroupData getGroupDataByGroupName(String groupName) {
		GroupData gc = groupRepository.findByGroupName(groupName);
		return gc;
	}




	

	


	

	



}
