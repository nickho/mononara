/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal.service;

import com.kanjiportal.portal.dictionnary.Dictionnary;
import com.kanjiportal.portal.dictionnary.DictionnaryEntry;
import org.jboss.wsf.spi.annotation.WebContext;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * DictionnarySoapService
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement DictionnarySoapService
 */
@Stateless
@WebService
@WebContext(contextRoot = "/kanji-portal/services/soap", urlPattern = "/DictionnaryService")
public class DictionnaryServiceImpl implements DictionnaryService {

    @PersistenceContext
    private EntityManager em;

    @WebMethod
    public long getDictionnaryEntriesCount() {
        Long count = (Long) em.createQuery("select count(d) from DictionnaryEntry d").getSingleResult();
        System.out.println(count);
        return count.longValue();
    }

    @WebMethod
    public Dictionnary getDictionnaryEntries() {
        return new Dictionnary(em.createQuery("select d from DictionnaryEntry d").getResultList());
    }

    @WebMethod
    public DictionnaryEntry getDictionnaryEntryById(long id) {
        DictionnaryEntry de = (DictionnaryEntry) em.createQuery("select d from DictionnaryEntry d where d.id = :id")
                .setParameter("id", id)
                .getSingleResult();
        return de;
    }
}
