package com.kanjiportal.portal.selenium.kanji;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;


/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 24, 2009
 * Time: 10:22:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class KanjiSearchSeleniumTest extends SeleneseTestCase {

    public void setUp() {
        selenium = new DefaultSelenium("localhost", 3333, "*iexplore", "http://localhost:6060/");
        selenium.start();
    }

    public void testSearchIchi() throws Exception {
        selenium.open("/kanji-portal/kanjis/search.seam");
        selenium.type("searchCriteria:searchString", "un");
        selenium.click("searchCriteria:findKanjis");
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if (selenium.isElementPresent("link=?")) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }

        selenium.click("link=?");
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if (selenium.isElementPresent("char")) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }

        verifyTrue(selenium.isTextPresent("Henshall (GRJC) : 1"));
    }

    public void testSearchAi() throws Exception {
        selenium.open("/kanji-portal/kanjis/search.seam");
        selenium.type("searchCriteria:searchString", "amour");
        selenium.click("searchCriteria:findKanjis");
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if (selenium.isElementPresent("link=?")) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }

        selenium.click("link=?");
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if (selenium.isElementPresent("char")) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }

        verifyTrue(selenium.isTextPresent("Henshall (GRJC) : 417"));
    }


    public void tearDown() {
        selenium.stop();
    }

}
