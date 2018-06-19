package com.app.mps.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.mps.oauth.security.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT DISTINCT user FROM User user " + "INNER JOIN FETCH user.authorities AS authorities "
			+ "WHERE user.username = :username")
	User findByUsername(@Param("username") String username);

}
