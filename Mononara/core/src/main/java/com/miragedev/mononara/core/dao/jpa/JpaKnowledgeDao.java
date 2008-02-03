package com.miragedev.mononara.core.dao.jpa;

import com.miragedev.mononara.core.dao.KnowledgeDao;
import com.miragedev.mononara.core.model.Kanji;
import com.miragedev.mononara.core.model.Knowledge;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 2, 2008
 * Time: 6:21:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class JpaKnowledgeDao extends JpaDaoSupport implements KnowledgeDao {

    public Knowledge findById(long id) {
        return getJpaTemplate().find(Knowledge.class, id);
    }

    public List<Knowledge> findByTag(String tag) {
        return getJpaTemplate().find("SELECT kn FROM Knowledge kn WHERE kn.tag.code = ?1", tag);
    }

    public Knowledge findByTagAndKanji(String tag, Kanji kanji) {
        return (Knowledge) getJpaTemplate().find("SELECT kn FROM Knowledge kn WHERE kn.tag.code = ?1 and kn.kanji.id =?2", tag, kanji.getId()).get(0);
    }

    public void save(Knowledge knowledge) {
        getJpaTemplate().persist(knowledge);
    }

    public Knowledge update(Knowledge knowledge) {
        return getJpaTemplate().merge(knowledge);
    }

    public void delete(Knowledge knowledge) {
        getJpaTemplate().remove(knowledge);
    }
}