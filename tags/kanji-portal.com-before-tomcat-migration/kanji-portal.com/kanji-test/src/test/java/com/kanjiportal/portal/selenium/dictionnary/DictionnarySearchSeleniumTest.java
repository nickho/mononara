package com.kanjiportal.portal.selenium.dictionnary;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 24, 2009
 * Time: 10:22:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class DictionnarySearchSeleniumTest extends SeleneseTestCase {

    public void setUp() {
        selenium = new DefaultSelenium("localhost", 3333, "*iexplore", "http://localhost:6060/");
        selenium.start();
    }

    /**
     * Look for the 'Feu' word and click on the selected vocabulary word
     *
     * @throws Exception
     */
    public void testSearchFeu() throws Exception {
        selenium.open("/kanji-portal/dictionnary/search.seam");
        selenium.type("searchCriteria:searchString", "le feu");
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
                if (selenium.isTextPresent("Copyright")) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }

        verifyTrue(selenium.isTextPresent("le feu"));
    }

    /**
     * Look for the 'Mardi' word and click on the result.
     *
     * @throws Exception
     */
    public void testSearchMardi() throws Exception {
        selenium.open("/kanji-portal/dictionnary/search.seam");
        selenium.type("searchCriteria:searchString", "mardi");
        selenium.click("searchCriteria:findKanjis");
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if (selenium.isElementPresent("link=???")) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }

        selenium.click("link=???");
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if (selenium.isTextPresent("Copyright")) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }

        verifyTrue(selenium.isTextPresent("mardi"));
    }


    public void tearDown() {
        selenium.stop();
    }

}


