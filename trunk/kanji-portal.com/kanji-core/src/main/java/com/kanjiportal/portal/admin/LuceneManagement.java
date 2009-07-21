package com.kanjiportal.portal.admin;


/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jul 15, 2009
 * Time: 6:56:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface LuceneManagement {

    void reIndexKanji();

    void reIndexMeaning();

    void reIndexKanjiMeaning();

}
