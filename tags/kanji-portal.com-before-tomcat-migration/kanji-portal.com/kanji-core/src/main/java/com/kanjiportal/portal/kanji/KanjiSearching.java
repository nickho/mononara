/******************************************************************************
 * JBoss, a division of Red Hat                                               *
 * Copyright 2006, Red Hat Middleware, LLC, and individual                    *
 * contributors as indicated by the @authors tag. See the                     *
 * copyright.txt in the distribution for a full listing of                    *
 * individual contributors.                                                   *
 *                                                                            *
 * This is free software; you can redistribute it and/or modify it            *
 * under the terms of the GNU Lesser General Public License as                *
 * published by the Free Software Foundation; either version 2.1 of           *
 * the License, or (at your option) any later version.                        *
 *                                                                            *
 * This software is distributed in the hope that it will be useful,           *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU           *
 * Lesser General Public License for more details.                            *
 *                                                                            *
 * You should have received a copy of the GNU Lesser General Public           *
 * License along with this software; if not, write to the Free                *
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA         *
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.                   *
 ******************************************************************************/
package com.kanjiportal.portal.kanji;

import com.kanjiportal.portal.dao.KanjiDao;
import com.kanjiportal.portal.model.Kanji;
import org.jboss.seam.log.Log;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;

@Local
public interface KanjiSearching {
    public int getPageSize();

    public void setPageSize(int pageSize);

    public String getSearchString();

    public void setSearchString(String searchString);

    public String getSearchPattern();

    public void find();

    public void nextPage();

    public boolean isNextPageAvailable();

    public void setKanjiDao(KanjiDao kanjiDao);

    public List<Kanji> getKanjis();

    public String getSearchStatus();

    public void setLogger(Log logger);

    Map<Long, String> getSearchMeanings();

    void setSearchMeanings(Map<Long, String> searchMeanings);
}
