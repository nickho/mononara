package com.miragedev.mononara.core.dao;

import com.miragedev.mononara.core.model.DictionaryEntry;
import org.springframework.test.jpa.AbstractJpaTests;

import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Dec 30, 2007
 * Time: 10:31:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class DictionaryEntryDaoTest extends AbstractJpaTests {
    private DictionaryEntryDao dictionaryEntryDao;

    public void setKanjiDao(DictionaryEntryDao dictionaryEntryDao) {
        this.dictionaryEntryDao = dictionaryEntryDao;
    }

    protected String[] getConfigLocations() {
        return new String[]{"classpath:META-INF/spring-config.xml"};
    }

    protected void onSetUpInTransaction() throws Exception {
        jdbcTemplate.execute("insert into dictionaryentry (id, spellinginkanji, spellinginkana, spellinginromaji, description) values (1, '???', '??.?.??', 'ei.ga.kan', 'blah')");
        //jdbcTemplate.execute("insert into address (id, street_number, street_name) values (3, 123, 'Dover Street')");
    }

    public void testFindByIdWhereKanjiExists() {
        DictionaryEntry dictionnaryEntry = dictionaryEntryDao.findById(1);
        assertNotNull(dictionnaryEntry);
        assertEquals("???", dictionnaryEntry.getSpellingInKanji());
    }

    public void testFindByIdWhereKanjiDoesNotExist() {
        DictionaryEntry dictionnaryEntry = dictionaryEntryDao.findById(999999);
        assertNull(dictionnaryEntry);
    }

    public void testFindBySpelling1Exists() {
        List<DictionaryEntry> dictionnaryEntries = dictionaryEntryDao.findBySpelling("?");
        assertNotNull(dictionnaryEntries);
        assertEquals(1, dictionnaryEntries.size());
        assertEquals("???", dictionnaryEntries.get(0).getSpellingInKanji());
    }

    /*
    public void testFindByNameWhereRestaurantExists() {
      List<Restaurant> restaurants = restaurantDao.findByName("Veggie Village");
      assertEquals(1, restaurants.size());
      Restaurant restaurant = restaurants.get(0);
      assertEquals("Veggie Village", restaurant.getName());
      assertEquals("Main Street", restaurant.getAddress().getStreetName());
      assertEquals(2, restaurant.getEntrees().size());
    }

    public void testFindByNameWhereRestaurantDoesNotExist() {
      List<Restaurant> restaurants = restaurantDao.findByName("No Such Restaurant");
      assertEquals(0, restaurants.size());
    }


    public void testDeleteRestaurantAlsoDeletesAddress() {
      String restaurantName = "Dover Diner";
      int preRestaurantCount = jdbcTemplate.queryForInt("select count(*) from restaurant");
      int preAddressCount = jdbcTemplate.queryForInt("select count(*) from address where street_name = 'Dover Street'");
      Restaurant restaurant = restaurantDao.findByName(restaurantName).get(0);
      restaurantDao.delete(restaurant);
      List<Restaurant> results = restaurantDao.findByName(restaurantName);
      assertEquals(0, results.size());
      int postRestaurantCount = jdbcTemplate.queryForInt("select count(*) from restaurant");
      assertEquals(preRestaurantCount - 1, postRestaurantCount);
      int postAddressCount = jdbcTemplate.queryForInt("select count(*) from address where street_name = 'Dover Street'");
      assertEquals(preAddressCount - 1, postAddressCount);
    }

    public void testDeleteRestaurantDoesNotDeleteEntrees() {
      String restaurantName = "Dover Diner";
      int preRestaurantCount = jdbcTemplate.queryForInt("select count(*) from restaurant");
      int preEntreeCount = jdbcTemplate.queryForInt("select count(*) from entree");
      Restaurant restaurant = restaurantDao.findByName(restaurantName).get(0);
      restaurantDao.delete(restaurant);
      List<Restaurant> results = restaurantDao.findByName(restaurantName);
      assertEquals(0, results.size());
      int postRestaurantCount = jdbcTemplate.queryForInt("select count(*) from restaurant");
      assertEquals(preRestaurantCount - 1, postRestaurantCount);
      int postEntreeCount = jdbcTemplate.queryForInt("select count(*) from entree");
      assertEquals(preEntreeCount, postEntreeCount);
    }
    */
}