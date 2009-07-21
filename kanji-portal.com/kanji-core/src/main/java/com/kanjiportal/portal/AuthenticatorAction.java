/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal;

import com.kanjiportal.portal.model.User;
import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.*;
import org.jboss.seam.log.Log;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * AuthenticatorAction
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement AuthenticatorAction
 */
@AutoCreate
@Name("authenticator")
public class AuthenticatorAction implements Authenticator {
    @In
    private EntityManager em;

    @Out(required = false, scope = SESSION)
    private User user;

    @Logger
    private Log log;

    public boolean authenticate() {
        log.info("authenticate user #{identity.username}");
        List results = em.createQuery("select u from User u where u.username=#{identity.username} and u.password=#{identity.password}")
                .getResultList();

        if (results.size() == 0) {
            log.info("authentication of user #{identity.username} failed");
            return false;
        } else {
            user = (User) results.get(0);
            log.info("user #{identity.username} logged in");
            return true;
        }
    }
}
