package com.miragedev.mononara.core.service.proxy;

import com.miragedev.mononara.core.service.resources.DictionaryListKP;
import com.miragedev.mononara.core.service.resources.KanjiListKP;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jul 26, 2009
 * Time: 9:25:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/resource/rest")
@Consumes("application/xml")
public interface KanjiPortalResource {

    @GET
    @Path("/kanji/since/{since}")
    public KanjiListKP findKanjisSince(@PathParam("since") String since);

    @GET
    @Path("/kanji/since/{since}/{page}/{item}")
    public KanjiListKP findKanjisSinceWithPaging(@PathParam("since") String since,
                                                 @PathParam("page") int page,
                                                 @PathParam("item") int itemPerPage);

    @GET
    @Path("/dictionary/since/{since}/{page}/{item}")
    public DictionaryListKP searchDictionarySinceWithPaging(@PathParam("since") String since,
                                                            @PathParam("page") int page,
                                                            @PathParam("item") int itemPerPage);

    @GET
    @Path("/dictionary/since/{since}")
    public DictionaryListKP searchDictionarySince(@PathParam("since") String since);
}
