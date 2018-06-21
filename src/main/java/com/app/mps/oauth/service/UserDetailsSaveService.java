/**
 * 
 */
package com.app.mps.oauth.service;

import java.util.List;

import com.app.mps.oauth.security.model.Authority;
import com.app.mps.oauth.security.model.User;

/**
 * @author Sandeep
 *
 */
public interface UserDetailsSaveService {

	public User saveUser(User user);

	public User updateUser(User user);

	public List<User> fetchAllUsers();

	public User deleteUser(User user);

	public Long deleteUser(Long userId);
	
	public List<Authority> listAllAuthorities();
}
