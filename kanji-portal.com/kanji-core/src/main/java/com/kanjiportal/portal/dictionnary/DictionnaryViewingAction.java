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
package com.kanjiportal.portal.dictionnary;

import com.kanjiportal.portal.model.Dictionnary;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.web.RequestParameter;

import javax.persistence.EntityManager;

/**
 * DictionnaryServiceAction
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement DictionnaryServiceAction
 */
@Name("dictionnaryViewing")
@Scope(ScopeType.STATELESS)
@AutoCreate
public class DictionnaryViewingAction implements DictionnaryViewing {

    @In
    private EntityManager entityManager;

    @RequestParameter
    private long entryId;

    @Unwrap
    public Dictionnary getDictionnaryEntry() {
        return (Dictionnary) entityManager.createQuery("select distinct e from Dictionnary e where e.id = :id")
                .setParameter("id", entryId)
                .getSingleResult();
    }

}
