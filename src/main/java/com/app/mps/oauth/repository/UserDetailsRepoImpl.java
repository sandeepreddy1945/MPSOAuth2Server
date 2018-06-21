package com.app.mps.oauth.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.app.mps.oauth.security.model.User;

/**
 * 
 * @author Sandeep
 *
 */
@Repository
public class UserDetailsRepoImpl implements UserDetailsRepo {

	@PersistenceContext
	private EntityManager entityManager;

	private BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();

	@Override
	public User saveUser(User user) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		// encode the password before saving it.
		user.setPassword(passEncoder.encode(user.getPassword()));
		session.save(user);
		session.getTransaction().commit();
		return user;
	}

	@Override
	public User updateUser(User user) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		user.setPassword(passEncoder.encode(user.getPassword()));
		session.merge(user); // TODO Need to update this to normal query.
		session.getTransaction().commit();
		return user;
	}

	@Override
	public List<User> fetchAllUsers() {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		Query query = session.createNamedQuery("@listAllUsers");
		session.getTransaction().commit();
		return query.getResultList();
	}

	@Override
	public User deleteUser(User user) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		session.delete(user); // TODO Need to update this to normal query.
		session.getTransaction().commit();
		return user;
	}

	@Override
	public Long deleteUser(Long userId) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		session.getNamedQuery("@deleteUserById").setParameter("userId", userId).executeUpdate();
		session.getTransaction().commit();
		return userId;
	}

}
