package com.kanjiportal.portal.admin;

import com.kanjiportal.portal.model.Kanji;
import com.kanjiportal.portal.model.KanjiMeaning;
import com.kanjiportal.portal.model.Meaning;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jul 15, 2009
 * Time: 6:57:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
@Name("luceneManagement")
@Scope(value = ScopeType.SESSION)
public class LuceneManagementAction implements LuceneManagement {

    @In
    private FacesMessages facesMessages;

    @Logger
    private Log log;

    @PersistenceContext
    private EntityManager entityManager;


    public void reIndexKanji() {
        FullTextEntityManager ftem = (FullTextEntityManager) entityManager;

        long beforeKanjis = System.currentTimeMillis();
        List<Kanji> kanjis = ftem.createQuery("select k from Kanji k").getResultList();
        ftem.purgeAll(Kanji.class);
        log.debug("Indexing kanjis ....");
        for (Kanji kanji : kanjis) {
            ftem.index(kanji);
        }
        log.debug("Indexing kanjis done");
        long afterKanjis = System.currentTimeMillis();

        facesMessages.add("Kanji Indexing done in #0 ms", afterKanjis - beforeKanjis);
    }

    public void reIndexMeaning() {
        FullTextEntityManager ftem = (FullTextEntityManager) entityManager;

        long beforeMeanings = System.currentTimeMillis();
        List<Meaning> meanings = ftem.createQuery("select m From Meaning m").getResultList();
        ftem.purgeAll(Meaning.class);
        log.debug("Indexing Meaning....");
        for (Meaning meaning : meanings) {
            ftem.index(meaning);
        }
        log.debug("Indexing Meaning done");
        long afterMeanings = System.currentTimeMillis();

        facesMessages.add("Meaning Indexing done in #0 ms", afterMeanings - beforeMeanings);
    }

    public void reIndexKanjiMeaning() {
        FullTextEntityManager ftem = (FullTextEntityManager) entityManager;

        long beforeKanjiMeanings = System.currentTimeMillis();
        List<KanjiMeaning> kanjiMeanings = ftem.createQuery("select km From KanjiMeaning km").getResultList();
        ftem.purgeAll(KanjiMeaning.class);
        log.debug("Indexing KanjiMeaning....");
        for (KanjiMeaning kanjiMeaning : kanjiMeanings) {
            ftem.index(kanjiMeaning);
        }
        log.debug("Indexing KanjiMeaning done");
        long afterKanjiMeanings = System.currentTimeMillis();

        facesMessages.add("KanjiMeaning Indexing done in #0 ms", afterKanjiMeanings - beforeKanjiMeanings);
    }
}
