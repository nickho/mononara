package com.miragedev.mononara.core.service;

import com.miragedev.mononara.core.dao.DictionaryEntryDao;
import com.miragedev.mononara.core.dao.KanjiDao;
import com.miragedev.mononara.core.model.Kanji;
import com.miragedev.mononara.core.model.Knowledge;
import com.miragedev.mononara.core.model.Tag;
import com.miragedev.mononara.core.service.resources.KanjiListKP;
import junit.framework.Assert;
import org.springframework.test.jpa.AbstractJpaTests;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Oct 24, 2009
 * Time: 7:01:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class KanjiServiceImplTest extends AbstractJpaTests {

    private KanjiService kanjiService;
    private DictionaryEntryDao dictionaryEntryDao;
    private KanjiDao kanjiDao;

    public void setKanjiService(KanjiService kanjiService) {
        this.kanjiService = kanjiService;
    }

    public void setDictionaryEntryDao(DictionaryEntryDao dictionaryEntryDao) {
        this.dictionaryEntryDao = dictionaryEntryDao;
    }

    public void setKanjiDao(KanjiDao kanjiDao) {
        this.kanjiDao = kanjiDao;
    }

    protected String[] getConfigLocations() {
        return new String[]{"classpath:META-INF/spring-config.xml"};
    }

    protected void onSetUpInTransaction() throws Exception {
        //jdbcTemplate.execute("insert into tag (code) values ('tag_1')");
        //jdbcTemplate.execute("insert into tag (code) values ('tag_2')");
        //jdbcTemplate.execute("insert into tag (code) values ('tag_3')");
        //jdbcTemplate.execute("insert into tag (code) values ('tag_4')");
    }


    public void testImportKanjisAndTags() {
        KanjiListKP list = buildKanjiList("jlpt4", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        kanjiService.importKanjisAndTags(list);

        List<Kanji> kanjis = sharedEntityManager.createQuery("select k from Kanji k")
                .getResultList();
        Assert.assertEquals(10, kanjis.size());
        for (Kanji kanji : kanjis) {
            Assert.assertNotNull(kanji.getCharacter());
            Assert.assertEquals(1, kanji.getTags().size());
            Assert.assertEquals("jlpt4", kanji.getTags().get(0).getCode());
        }

        List<Knowledge> knowledges = sharedEntityManager.createQuery("select k from Knowledge k")
                .getResultList();
        Assert.assertEquals(10, knowledges.size());
        for (Knowledge knowledge : knowledges) {
            Assert.assertNotNull(knowledge.getKanji());
            Assert.assertNotNull(knowledge.getTag());
            //System.out.println("hop : "+knowledge);
        }

    }


    private KanjiListKP buildKanjiList(String tagCode, int... ids) {
        KanjiListKP kanjiListKP = new KanjiListKP();
        List<Kanji> kanjis = new ArrayList<Kanji>();
        Tag tag = new Tag();
        tag.setCode(tagCode);
        tag.setName("name : " + tagCode);
        for (int i = 0; i < ids.length; i++) {
            Kanji kanji = new Kanji();
            kanji.setId(ids[i]);
            kanji.setCharacter("Ch " + ids[i]);
            kanji.setDescription("Description of kanji : " + ids[i]);
            kanji.addTag(tag);
            kanjis.add(kanji);
        }
        kanjiListKP.setKanjis(kanjis);
        kanjiListKP.setNbKanjis(kanjis.size());
        kanjiListKP.setTotalCount(-1);
        return kanjiListKP;
    }

}