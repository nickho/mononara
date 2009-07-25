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
package com.kanjiportal.portal.service;

import com.kanjiportal.portal.model.Knowledge;
import com.kanjiportal.portal.model.service.KnowledgeParam;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 31, 2009
 * Time: 9:42:09 PM
 * To change this template use File | Settings | File Templates.
 */
public interface KnowledgeService {

    public boolean login(String user, String password);

    public int update(List<KnowledgeParam> list);

    public List<Knowledge> getKnowledgesByTag(String tagName);

    public List<Knowledge> getKnowledgesByTagWithPaging(String tagName, int page, int pageSize);
}
