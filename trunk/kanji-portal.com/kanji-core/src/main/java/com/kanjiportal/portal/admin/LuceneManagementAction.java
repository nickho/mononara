/**
 * This file is part of kanji-portal.com.
 *
 * kanji-portal.com is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * kanji-portal.com is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with kanji-portal.com.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2008-2009 Nicolas Radde <nicolas@radde.org>
 */
package com.kanjiportal.portal.admin;

import com.kanjiportal.portal.model.Dictionary;
import com.kanjiportal.portal.model.Kanji;
import com.kanjiportal.portal.model.Meaning;
import com.kanjiportal.portal.model.Translation;
import org.hibernate.*;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

import javax.persistence.EntityManager;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jul 15, 2009
 * Time: 6:57:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Name("luceneManagement")
@Scope(value = ScopeType.SESSION)
public class LuceneManagementAction implements LuceneManagement {

    private static final int BATCH_SIZE = 100;

    @In
    private FacesMessages facesMessages;

    @Logger
    private Log log;

    @In
    private EntityManager entityManager;

    public void reIndexKanji() {
        FullTextSession fullTextSession = (FullTextSession) entityManager.getDelegate();
        fullTextSession.setFlushMode(FlushMode.MANUAL);
        fullTextSession.setCacheMode(CacheMode.IGNORE);
        log.debug("Indexing kanjis ....");
        long beforeKanjis = System.currentTimeMillis();

        //Scrollable results will avoid loading too many objects in memory
        ScrollableResults results = fullTextSession.createCriteria(Kanji.class)
                .setFetchSize(BATCH_SIZE)
                .setFetchMode("meanings", FetchMode.JOIN)
                .setFetchMode("meanings.meaning.language", FetchMode.JOIN)
                .scroll(ScrollMode.FORWARD_ONLY);
        int index = 0;
        while (results.next()) {
            index++;
            fullTextSession.index(results.get(0)); //index each element
            if (index % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
                log.debug("flush()");
            }
        }

        log.debug("Indexing kanjis done");
        long afterKanjis = System.currentTimeMillis();

        facesMessages.add("Kanji Indexing done in #0 ms", afterKanjis - beforeKanjis);
    }

    public void purgeKanji() {
        FullTextEntityManager ftem = (FullTextEntityManager) entityManager;
        ftem.purgeAll(Kanji.class);
        ftem.purgeAll(Meaning.class);
    }

    public void optimizeKanji() {
        FullTextEntityManager ftem = (FullTextEntityManager) entityManager;
        ftem.getSearchFactory().optimize(Kanji.class);
        ftem.getSearchFactory().optimize(Meaning.class);
    }


    public void reIndexDictionary() {
        FullTextSession fullTextSession = (FullTextSession) entityManager.getDelegate();
        fullTextSession.setFlushMode(FlushMode.MANUAL);
        fullTextSession.setCacheMode(CacheMode.IGNORE);

        long beforeDictionary = System.currentTimeMillis();

        //Scrollable results will avoid loading too many objects in memory
        ScrollableResults results = fullTextSession.createCriteria(Dictionary.class)
                .setFetchSize(BATCH_SIZE)
                .setFetchMode("translations", FetchMode.JOIN)
                .setFetchMode("translations.language", FetchMode.JOIN)
                .scroll(ScrollMode.FORWARD_ONLY);
        int index = 0;
        while (results.next()) {
            index++;
            fullTextSession.index(results.get(0)); //index each element
            if (index % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
                log.debug("flush()");
            }
        }


        log.debug("Indexing dictionary done");
        long afterDictionary = System.currentTimeMillis();

        facesMessages.add("Dictionary Indexing done in #0 ms", afterDictionary - beforeDictionary);
    }


    public void purgeDictionary() {
        FullTextEntityManager ftem = (FullTextEntityManager) entityManager;
        ftem.purgeAll(Dictionary.class);
        ftem.purgeAll(Translation.class);
    }

    public void optimizeDictionary() {
        FullTextEntityManager ftem = (FullTextEntityManager) entityManager;
        ftem.getSearchFactory().optimize(Dictionary.class);
        ftem.getSearchFactory().optimize(Translation.class);
    }

}
