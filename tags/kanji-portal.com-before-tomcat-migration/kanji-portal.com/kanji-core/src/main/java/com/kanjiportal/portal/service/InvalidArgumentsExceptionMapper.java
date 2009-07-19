package com.kanjiportal.portal.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jul 10, 2009
 * Time: 6:46:01 PM
 * To change this template use File | Settings | File Templates.
 */

//@Provider
public class InvalidArgumentsExceptionMapper implements ExceptionMapper<InvalidArgumentsException> {

    public Response toResponse(InvalidArgumentsException exception) {
        return Response.status(400).entity(exception).build();
    }

}

