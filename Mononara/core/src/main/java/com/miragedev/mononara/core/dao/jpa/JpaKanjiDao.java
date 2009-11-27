package com.miragedev.mononara.core.dao.jpa;

import com.miragedev.mononara.core.dao.KanjiDao;
import com.miragedev.mononara.core.model.Kanji;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Dec 30, 2007
 * Time: 10:19:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class JpaKanjiDao extends JpaDaoSupport implements KanjiDao {

    public Kanji findById(long id) {
        return getJpaTemplate().find(Kanji.class, id);
    }

    public List<Kanji> findByCharacter(String kanji) {
        return getJpaTemplate().find("select k from Kanji k where k.character = ?1", kanji);
    }

    public void save(Kanji kanji) {
        getJpaTemplate().persist(kanji);
    }

    public Kanji update(Kanji kanji) {
        return getJpaTemplate().merge(kanji);
    }

    public void delete(Kanji kanji) {
        getJpaTemplate().remove(kanji);
    }

    public void flush() {
        getJpaTemplate().flush();
    }

    public Date findLastUpdate() {
        List<Kanji> kanjis = getJpaTemplate().find("select k from Kanji k order by k.update");
        if (kanjis.size() > 0) {
            Kanji kanji = kanjis.get(0);
            return kanji.getUpdate();
        } else {
            return null;
        }
    }

}
