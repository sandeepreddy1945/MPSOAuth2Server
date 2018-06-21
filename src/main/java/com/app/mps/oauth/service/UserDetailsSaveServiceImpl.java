package com.app.mps.oauth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.mps.oauth.repository.UserDetailsRepo;
import com.app.mps.oauth.security.model.Authority;
import com.app.mps.oauth.security.model.User;

/**
 * 
 * @author Sandeep
 *
 */
@Service
public class UserDetailsSaveServiceImpl implements UserDetailsSaveService {

	@Autowired
	private UserDetailsRepo usr;

	@Override
	public User saveUser(User user) {
		
		return usr.saveUser(user);
	}

	@Override
	public User updateUser(User user) {
		
		return usr.updateUser(user);
	}

	@Override
	public List<User> fetchAllUsers() {
		
		return usr.fetchAllUsers();
	}

	@Override
	public User deleteUser(User user) {
		
		return usr.deleteUser(user);
	}

	@Override
	public Long deleteUser(Long userId) {
		
		return usr.deleteUser(userId);
	}

	@Override
	public List<Authority> listAllAuthorities() {

		return usr.listAllAuthorities();
	}

}
