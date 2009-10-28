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
    private EntityManager entityManager;

    @Out(required = false, scope = SESSION)
    private User user;

    @Logger
    private Log log;

    public boolean authenticate() {
        log.info("authenticate user #{identity.username}");
        List results = entityManager.createQuery("select u from User u where u.username=#{identity.username} and u.password=#{identity.password}")
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
