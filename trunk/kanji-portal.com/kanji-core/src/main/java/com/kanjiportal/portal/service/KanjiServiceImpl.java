package com.kanjiportal.portal.service; /**
 * ${NAME}
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement ${NAME}
 */

import com.kanjiportal.portal.kanji.Kanji;
import com.kanjiportal.portal.kanji.KanjiList;
import com.kanjiportal.portal.dictionnary.DictionnaryEntry;
import org.jboss.wsf.spi.annotation.WebContext;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@WebService
@WebContext(contextRoot = "/kanji-portal/services/soap", urlPattern = "/KanjiService")
public class KanjiServiceImpl implements KanjiService {

    @PersistenceContext
    private EntityManager em;

    @WebMethod
    public long getKanjisCount() {
        Long count = (Long) em.createQuery("select count(k) from Kanji k").getSingleResult();
        System.out.println(count);
        return count.longValue();
    }

    @WebMethod
    public KanjiList getKanjis() {
        return new KanjiList(em.createQuery("select k from Kanji k").getResultList());
    }

    @WebMethod
    public Kanji getKanjiById(long id) {
        Kanji k = (Kanji) em.createQuery("select d from Kanji d where d.id = :id")
                .setParameter("id", id)
                .getSingleResult();
        return k;
    }

}