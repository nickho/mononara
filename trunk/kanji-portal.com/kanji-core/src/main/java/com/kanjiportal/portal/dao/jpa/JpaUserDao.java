package com.kanjiportal.portal.dao.jpa;

import com.kanjiportal.portal.dao.UserDao;
import com.kanjiportal.portal.model.User;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import javax.persistence.EntityManager;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 31, 2009
 * Time: 11:41:40 PM
 * To change this template use File | Settings | File Templates.
 */
@AutoCreate
@Name("userDao")
public class JpaUserDao implements UserDao {

    @In
    private EntityManager entityManager;

    public User findByUsername(String username) {
        return (User) entityManager.createQuery("from User u where u.username = :username")
                .setParameter("username", username)
                .getSingleResult();
    }

}
