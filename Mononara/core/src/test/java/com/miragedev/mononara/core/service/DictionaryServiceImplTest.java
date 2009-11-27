package com.miragedev.mononara.core.service;

import com.miragedev.mononara.core.dao.DictionaryEntryDao;
import com.miragedev.mononara.core.dao.KanjiDao;
import com.miragedev.mononara.core.model.DictionaryEntry;
import com.miragedev.mononara.core.service.resources.DictionaryKP;
import com.miragedev.mononara.core.service.resources.DictionaryListKP;
import com.miragedev.mononara.core.service.resources.DictionaryTagKP;
import com.miragedev.mononara.core.service.resources.TranslationKP;
import junit.framework.Assert;
import org.springframework.test.jpa.AbstractJpaTests;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Oct 24, 2009
 * Time: 7:01:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class DictionaryServiceImplTest extends AbstractJpaTests {

    private DictionaryService dictionaryService;
    private DictionaryEntryDao dictionaryEntryDao;
    private KanjiDao kanjiDao;

    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
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

    /*
    public void testOnlineSync() throws URISyntaxException {
        KanjiPortalResource kanjiPortalResource  = EasyMock.createMock(KanjiPortalResource.class);
        EasyMock.expect(kanjiPortalResource.findKanjisSinceWithPaging("1999-12-31", 0, 100)).andReturn(buildKanjiList("jlpt4",1,2,3,4,5,6,7,8,9,10));
        EasyMock.expect(kanjiPortalResource.findKanjisSinceWithPaging("1999-12-31", 1, 100)).andReturn(buildKanjiList("none"));
        EasyMock.expect(kanjiPortalResource.searchDictionarySinceWithPaging("1999-12-31", 0, 100)).andReturn(buildDictionaryList("jlpt4",1,2,3,4,5,6,7,8,9,10));
        EasyMock.expect(kanjiPortalResource.searchDictionarySinceWithPaging("1999-12-31", 1, 100)).andReturn(buildDictionaryList("none"));
        EasyMock.replay(kanjiPortalResource);
        ((MononaraServiceImpl)mononaraService).setKanjiPortalResource(kanjiPortalResource);

        mononaraService.onlineSync();

    }
    */


    public void testImportDictionaryAndTags() {
        DictionaryListKP list = buildDictionaryList("jlpt4", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        dictionaryService.importDictionaryAndTags(list);

        List<DictionaryEntry> entries = sharedEntityManager.createQuery("select d from DictionaryEntry d")
                .getResultList();
        Assert.assertEquals(10, entries.size());
        for (DictionaryEntry entry : entries) {
            Assert.assertNotNull(entry.getSpellingInKanji());
            Assert.assertEquals(1, entry.getTags().size());
            Assert.assertEquals("jlpt4", entry.getTags().get(0).getCode());
        }

    }


    private DictionaryListKP buildDictionaryList(String tagCode, int... ids) {
        DictionaryListKP dictionaryListKP = new DictionaryListKP();
        DictionaryTagKP tag = new DictionaryTagKP();
        tag.setCode(tagCode);
        tag.setValue("name : " + tagCode);
        for (int i = 0; i < ids.length; i++) {
            DictionaryKP entry = new DictionaryKP();
            entry.setKanji("ChKanji " + ids[i]);
            entry.setKana("ChKana " + ids[i]);
            entry.setRomaji("ChRomaji " + ids[i]);
            DictionaryKP.Tags tags = new DictionaryKP.Tags();
            entry.setTags(tags);
            entry.getTags().getTag().add(tag);
            TranslationKP trans = new TranslationKP();
            trans.setLanguage("fr");
            trans.setValue("description : " + ids[i]);
            DictionaryKP.Translations transs = new DictionaryKP.Translations();
            entry.setTranslations(transs);
            entry.getTranslations().getTranslation().add(trans);
            dictionaryListKP.getEntries().add(entry);
        }
        dictionaryListKP.setCount(dictionaryListKP.getEntries().size());
        dictionaryListKP.setTotal(-1);
        return dictionaryListKP;
    }


}