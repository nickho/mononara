package com.kanjiportal.portal.dao.jpa;

import com.kanjiportal.portal.dao.TagDao;
import com.kanjiportal.portal.model.Tag;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 31, 2009
 * Time: 11:22:13 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
@AutoCreate
@Name("tagDao")
public class JpaTagDao implements TagDao {

    @PersistenceContext
    private EntityManager em;

    public Tag findByName(String tag) {
        return (Tag) em.createQuery("select t from Tag t where t.name = :name")
                .setParameter("name", tag)
                .getSingleResult();
    }

    public Tag findById(long id) {
        return em.find(Tag.class, id);
    }
}
