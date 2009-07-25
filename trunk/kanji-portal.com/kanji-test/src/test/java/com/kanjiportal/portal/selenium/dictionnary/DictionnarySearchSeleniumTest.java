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


