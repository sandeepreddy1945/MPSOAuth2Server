package com.app.mps.oauth.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
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

	@Override
	public User saveUser(User user) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		return user;
	}

	@Override
	public User updateUser(User user) {
		Session session = entityManager.unwrap(Session.class);
		session.beginTransaction();
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
		// TODO need to add implementation for this.
		return null;
	}

}
