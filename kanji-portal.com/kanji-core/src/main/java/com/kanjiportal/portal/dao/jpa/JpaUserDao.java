package com.kanjiportal.portal.dao.jpa;

import com.kanjiportal.portal.dao.UserDao;
import com.kanjiportal.portal.model.User;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 31, 2009
 * Time: 11:41:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
@AutoCreate
@Name("userDao")
public class JpaUserDao implements UserDao {

    @PersistenceContext
    private EntityManager em;

    public User findByUsername(String username) {
        return (User) em.createQuery("from User u where u.username = :username")
                .setParameter("username", username)
                .getSingleResult();
    }

}
