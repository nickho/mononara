package com.miragedev.mononara.core.dao;

import com.miragedev.mononara.core.model.Tag;
import org.springframework.test.jpa.AbstractJpaTests;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 4, 2008
 * Time: 11:32:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class TagDaoTest extends AbstractJpaTests {
    private TagDao tagDao;

    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    protected String[] getConfigLocations() {
        return new String[]{"classpath:META-INF/spring-config.xml"};
    }

    protected void onSetUpInTransaction() throws Exception {
        jdbcTemplate.execute("insert into tag (code) values ('tag_1')");
        jdbcTemplate.execute("insert into tag (code) values ('tag_2')");
        jdbcTemplate.execute("insert into tag (code) values ('tag_3')");
        jdbcTemplate.execute("insert into tag (code) values ('tag_4')");
    }

    public void testFindByCodeWhereTagExists() {
        Tag tag = tagDao.findByCode("tag_1");
        assertNotNull(tag);
        assertEquals("tag_1", tag.getCode());
    }

    public void testFindByCodeWhereTagDoesntExists() {
        Tag tag = tagDao.findByCode("tag_100000");
        assertNull(tag);
    }

    public void testNewTag() {
        Tag tag = new Tag();
        tag.setCode("tag_save");
        tagDao.save(tag);
        Tag result = tagDao.findByCode("tag_save");
        assertNotNull(result);
    }

    public void testFindByAll() {
        List<Tag> tags = tagDao.findAll();
        assertEquals(4, tags.size());
        assertEquals("tag_1", tags.get(0).getCode());
        assertEquals("tag_4", tags.get(3).getCode());
    }
}
