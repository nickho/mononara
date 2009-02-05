package com.kanjiportal.portal.dao.jpa;

import com.kanjiportal.portal.dao.DictionnaryDao;
import com.kanjiportal.portal.dictionnary.DictionnaryEntry;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Feb 5, 2009
 * Time: 10:08:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
@AutoCreate
@Name("dictionnaryDao")
public class JpaDictionnaryDao implements DictionnaryDao {

    @PersistenceContext
    private EntityManager em;

    public List<DictionnaryEntry> findDictionnaryEntriesByPatternWithPaging(String pattern, int page, int pageSize) {
        List<DictionnaryEntry> des = em.createQuery("select d from DictionnaryEntry d where d.description like :pattern or d.romaji like :pattern or d.kana like :pattern or d.kanji like :pattern")
                .setParameter("pattern", pattern)
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
        return des;
    }
}
