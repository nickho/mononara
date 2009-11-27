package com.miragedev.mononara.core.dao.jpa;

import com.miragedev.mononara.core.dao.DictionaryEntryDao;
import com.miragedev.mononara.core.model.DictionaryEntry;
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
public class JpaDictionaryEntryDao extends JpaDaoSupport implements DictionaryEntryDao {
    public DictionaryEntry findById(long id) {
        return getJpaTemplate().find(DictionaryEntry.class, id);
    }

    public List<DictionaryEntry> findBySpelling(String spelling) {
        return getJpaTemplate().find("select d from DictionaryEntry d where d.spellingInKanji like concat(concat('%',?1),'%') order by d.spellingInKanji", spelling);
    }

    public DictionaryEntry findByNumber(int number) {
        return (DictionaryEntry) getJpaTemplate().find("select d from DictionaryEntry d where d.id = ?1", number).get(0);
    }

    public int size() {
        EntityManager em = getJpaTemplate().getEntityManagerFactory().createEntityManager();
        Query query = em.createQuery("select Max(d.id) from DictionaryEntry d");
        Long size = (Long) query.getSingleResult();
        if (size != null) {
            return size.intValue();
        } else {
            return 0;
        }
    }

    public void save(DictionaryEntry dictionnaryEntry) {
        getJpaTemplate().persist(dictionnaryEntry);
    }

    public DictionaryEntry update(DictionaryEntry dictionnaryEntry) {
        return getJpaTemplate().merge(dictionnaryEntry);
    }

    public void delete(DictionaryEntry dictionnaryEntry) {
        getJpaTemplate().remove(dictionnaryEntry);
    }

    public DictionaryEntry findByKanjiAndKana(String kanji, String kana) {
        List<DictionaryEntry> list = getJpaTemplate().find("select d from DictionaryEntry d " +
                "where d.spellingInKanji = ?1 and d.spellingInKana = ?2 " +
                "order by d.spellingInKanji",
                kanji, kana);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}