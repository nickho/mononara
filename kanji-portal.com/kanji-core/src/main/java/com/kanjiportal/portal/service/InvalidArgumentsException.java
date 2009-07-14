package com.kanjiportal.portal.service;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jul 10, 2009
 * Time: 6:31:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvalidArgumentsException extends Exception {

    public InvalidArgumentsException(String message, Throwable ex) {
        super(message, ex);
    }

    public InvalidArgumentsException(String message) {
        super(message);
    }

}
