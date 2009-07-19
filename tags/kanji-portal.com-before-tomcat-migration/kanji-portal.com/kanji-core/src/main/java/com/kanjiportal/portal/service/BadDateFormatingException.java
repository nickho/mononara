package com.kanjiportal.portal.service;

import java.text.ParseException;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jul 10, 2009
 * Time: 6:43:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class BadDateFormatingException extends InvalidArgumentsException {

    public BadDateFormatingException(ParseException ex) {
        super("Invalid date", ex);
    }
}
