package com.miragedev.mononara.core.dao.jpa;

import com.miragedev.mononara.core.dao.DictionnaryEntryDao;
import com.miragedev.mononara.core.model.DictionnaryEntry;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Dec 30, 2007
 * Time: 10:19:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class JpaDictionnaryEntryDao extends JpaDaoSupport implements DictionnaryEntryDao {
    public DictionnaryEntry findById(long id) {
        return getJpaTemplate().find(DictionnaryEntry.class, id);
    }

    public List<DictionnaryEntry> findBySpelling(String spelling) {
        return getJpaTemplate().find("select d from DictionnaryEntry d where d.spellingInKanji like concat(concat('%',?1),'%') order by d.spellingInKanji", spelling);
    }

    public DictionnaryEntry findByNumber(int number) {
        return (DictionnaryEntry) getJpaTemplate().find("select d from DictionnaryEntry d where d.id = ?1", number).get(0);
    }

    public int size() {
        EntityManager em = getJpaTemplate().getEntityManagerFactory().createEntityManager();
        Query query = em.createQuery("select Max(d.id) from DictionnaryEntry d");
        Long size = (Long) query.getSingleResult();
        if (size != null) {
            return size.intValue();
        } else {
            return 0;
        }
    }

    public void save(DictionnaryEntry dictionnaryEntry) {
        getJpaTemplate().persist(dictionnaryEntry);
    }

    public DictionnaryEntry update(DictionnaryEntry dictionnaryEntry) {
        return getJpaTemplate().merge(dictionnaryEntry);
    }

    public void delete(DictionnaryEntry dictionnaryEntry) {
        getJpaTemplate().remove(dictionnaryEntry);
    }
}