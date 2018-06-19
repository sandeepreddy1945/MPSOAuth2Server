/**
 * 
 */
package com.app.mps.oauth.repository;

import java.util.List;

import com.app.mps.oauth.security.model.User;

/**
 * @author Sandeep
 *
 */
public interface UserDetailsRepo {

	public User saveUser(User user);

	public User updateUser(User user);

	public List<User> fetchAllUsers();

	public User deleteUser(User user);

	public Long deleteUser(Long userId);
}
