package com.miragedev.mononara.core.service;

import com.miragedev.mononara.core.business.Basket;
import com.miragedev.mononara.core.business.Exam;
import com.miragedev.mononara.core.dao.DictionaryEntryDao;
import com.miragedev.mononara.core.dao.KanjiDao;
import junit.framework.Assert;
import org.springframework.test.jpa.AbstractJpaTests;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Oct 24, 2009
 * Time: 7:01:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class MononaraServiceImplTest extends AbstractJpaTests {

    private MononaraService mononaraService;
    private DictionaryEntryDao dictionaryEntryDao;
    private KanjiDao kanjiDao;

    public void setMononaraService(MononaraService mononaraService) {
        this.mononaraService = mononaraService;
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

    public void testEmptyExamWorkflow() {
        Basket basket = new Basket(0);
        Exam exam = mononaraService.startExam(basket);
        Assert.assertNull(exam);
    }

}
