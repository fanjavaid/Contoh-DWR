/**
 * 
 */
package com.fanjavaid.test;


import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fanjavaid.ic.controller.ItemController;
import com.fanjavaid.ic.model.Item;

/**
 * @author fanjavaid
 *
 */
public class ItemTest {
	private ItemController controller;

	@Before
	public void setUp() {
		controller = new ItemController();
	}
	
	//@Test
	public void insert() {
		controller.create("Item B", 4, "2016-03-20", new int[]{13,14});
	}
	
	//@Test
	public void update() {
		controller.update("Item B (updated)", 40, "2016-03-20", new int[]{5,13,14}, 3);
	}
	
	//@Test
	public void delete() {
		controller.deleteItem(3);
	}
	
	@Test
	public void selectAll() {
		List<Item> items = controller.fetchAll(0, 100);
		
		for (Item item : items) {
			System.out.println(item.toString());
		}
	}
	
	//@Test
	public void selectById() {
		Item item = controller.fetchById(1);
		
		System.out.println(item.toString());
	}
}
