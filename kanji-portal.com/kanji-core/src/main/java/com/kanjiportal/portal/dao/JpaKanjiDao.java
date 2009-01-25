package com.kanjiportal.portal.dao;

import com.kanjiportal.portal.kanji.Kanji;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 24, 2009
 * Time: 10:52:33 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
@AutoCreate
@Name("kanjiDao")
public class JpaKanjiDao implements KanjiDao {

    @PersistenceContext
    private EntityManager em;

    public List<Kanji> findByPattern(String pattern, int page, int pageSize) {
        return em.createQuery("select k from Kanji k where k.kanji like :pattern or lower(k.meaning) like :pattern or lower(k.description) like :pattern")
                .setParameter("pattern", pattern)
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
    }
}
