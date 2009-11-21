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
package com.kanjiportal.portal.dao.jpa;

import com.kanjiportal.portal.dao.DictionaryDao;
import com.kanjiportal.portal.dao.SearchTooGenericException;
import com.kanjiportal.portal.model.Dictionary;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.jboss.seam.annotations.*;
import org.jboss.seam.log.Log;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Feb 5, 2009
 * Time: 10:08:29 PM
 * To change this template use File | Settings | File Templates.
 */
@AutoCreate
@Name("dictionaryDao")
public class JpaDictionaryDao implements DictionaryDao {

    @In
    private EntityManager entityManager;

    @Logger
    private Log log;

    private Map<String, Analyzer> analyzers;

    @Create
    public void init() {
        analyzers = new HashMap<String, Analyzer>();
        analyzers.put("fr", new FrenchAnalyzer());
        analyzers.put("en", new StandardAnalyzer());
    }


    public Dictionary findById(long id) {
        return entityManager.find(Dictionary.class, id);
    }

    public List<Dictionary> searchDictionaryByPattern(String pattern, String language, int page, int pageSize) throws SearchTooGenericException {
        FullTextEntityManager ftem = (FullTextEntityManager) entityManager;
        BooleanQuery finalQuery = new BooleanQuery();
        org.apache.lucene.search.Query languageQuery = null;
        org.apache.lucene.search.Query luceneQuery = buildLuceneQuery(pattern, language);

        List<Dictionary> res = null;

        languageQuery = new TermQuery(new Term("translations.language.codeIso63901", language));
        finalQuery.add(luceneQuery, BooleanClause.Occur.MUST);
        finalQuery.add(languageQuery, BooleanClause.Occur.MUST);

        try {
            res = ftem.createFullTextQuery(finalQuery, Dictionary.class)
                    .setMaxResults(pageSize)
                    .setFirstResult(page * pageSize)
                    .getResultList();
        } catch (BooleanQuery.TooManyClauses e) {
            throw new SearchTooGenericException(e);
        }

        return res;
    }

    public List<Dictionary> searchDictionaryByPattern(String pattern, int page, int pageSize) throws SearchTooGenericException {
        FullTextEntityManager ftem = (FullTextEntityManager) entityManager;
        List<Dictionary> res = null;

        try {
            res = ftem.createFullTextQuery(buildLuceneQuery(pattern, "default"), Dictionary.class)
                    .setMaxResults(pageSize)
                    .setFirstResult(page * pageSize)
                    .getResultList();
        } catch (BooleanQuery.TooManyClauses e) {
            throw new SearchTooGenericException(e);
        }

        return res;
    }

    public List<Dictionary> searchDictionnaryByTag(String tag, int page, int pageSize) {
        return entityManager.createQuery(new StringBuilder().append(
                "select d from Dictionary d left join d.tags t")
                .append(" where t.tag.code = :tag").toString())
                .setParameter("tag", tag)
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
    }

    public List<Dictionary> searchDictionnaryBySinceDate(Date since, int page, int pageSize) {
        return entityManager.createQuery(new StringBuilder().append(
                "select d from Dictionary d left join d.tags t")
                .append(" where d.updateDate >= :since")
                .append(" or t.updateDate >= :since").toString())
                .setParameter("since", since, TemporalType.TIMESTAMP)
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
    }

    private org.apache.lucene.search.Query buildLuceneQuery(String pattern, String language) {
        org.apache.lucene.search.Query luceneQuery = null;
        Map<String, Float> boostPerField = new HashMap<String, Float>();
        boostPerField.put("romaji", 5f);
        boostPerField.put("kana", 5f);
        boostPerField.put("kanji", 5f);
        boostPerField.put("translations.description", 3f);
        boostPerField.put("translations.detail", 2f);
        boostPerField.put("translations.note", 1f);
        String[] productFields = {"romaji", "kana", "kanji", "translations.description", "translations.detail", "translations.note"};

        Analyzer analyzer = analyzers.get(language);
        if (analyzer == null) {
            analyzer = new StandardAnalyzer();
        }
        QueryParser parser = new MultiFieldQueryParser(productFields, analyzer, boostPerField);

        parser.setAllowLeadingWildcard(true);

        try {
            luceneQuery = parser.parse(pattern);
            log.debug("lucene search : #0", luceneQuery.toString());
        } catch (ParseException e) {
            log.warn("lucene query error : #0", e.getMessage());
            return null;
        }
        return luceneQuery;
    }

}
