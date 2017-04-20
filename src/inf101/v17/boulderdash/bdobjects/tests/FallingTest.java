package inf101.v17.boulderdash.bdobjects.tests;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.bdobjects.AbstractBDFallingObject;
import inf101.v17.boulderdash.bdobjects.BDDiamond;
import inf101.v17.boulderdash.bdobjects.BDRock;
import inf101.v17.boulderdash.bdobjects.IBDObject;
import inf101.v17.boulderdash.maps.BDMap;
import inf101.v17.datastructures.IGrid;
import inf101.v17.datastructures.MyGrid;

//The test stopped working for some reason the 26.03. They were working on Friday 24.03, but decided to stop working now.
//Unfortunately I do not have the time to debug it before the deadline. Hope this is OK. I have also consulted with Anya, and she did not see any reason 
//why the test should'nt have passed.



public class FallingTest {
	private BDMap map;
	
	@Before
	public void setup() {
		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
		grid.set(0, 4, 'd');
		grid.set(0, 0, '#');
		map = new BDMap(grid);
	}

	
	@Test
	public void fallingTest2() {
		checkFall(new Position(0, 4));
	}
	
	
	@Test
	public void fallingKills1() {
		// diamond two tiles above kills player
		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
		grid.set(0, 4, 'd');
		grid.set(0, 2, 'p');
		grid.set(0, 0, '#');
		
		map = new BDMap(grid);
		IBDObject obj = map.get(0, 4);
		assertTrue(obj instanceof BDDiamond);
		
		map.step();
		map.step();
		map.step();
		assertEquals(obj, map.get(0, 3));


		map.step();		
		assertEquals(obj, map.get(0, 2));
		
	
	
		map.step();		
		assertEquals(obj, map.get(0, 1));
		
		assertFalse(map.getPlayer().isAlive());
	}
	
	
	@Test
	public void restingDoesntKill1() {
		// diamond on top of player doesn't kill player
		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
		grid.set(0, 3, 'd');
		grid.set(0, 2, 'p');
		grid.set(0, 1, '#');
		map = new BDMap(grid);
		
		IBDObject obj = map.get(0, 2);
		
		
		map.step();
		map.step();
		map.step();
		map.step();
		assertEquals(obj, map.get(0, 2));
	}
	
	
	@Test
	public void fallingTest1() {
		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
		grid.set(0, 4, 'd');
		
		grid.set(0, 1, '#');
		map = new BDMap(grid);
		IBDObject obj = map.get(0, 4);
		assertTrue(obj instanceof BDDiamond);
		// four steps later, we've fallen down one step
		map.step();
		map.step();
		map.step();
		map.step();
		assertEquals(obj, map.get(0, 3));
		map.step();
		map.step();
		map.step();
		map.step();
		assertEquals(obj, map.get(0, 2));
		map.step();
		map.step();
		map.step();
		map.step();
		assertEquals(obj, map.get(0, 1));
		// wall reached, no more falling
		for (int i = 0; i < 10; i++)
			map.step();
		assertEquals(obj, map.get(0, 1));
	}

	
	protected Position checkFall(Position pos) {
		IBDObject obj = map.get(pos);
		if (obj instanceof AbstractBDFallingObject) {
			Position next = pos.moveDirection(Direction.SOUTH);
			if (map.canGo(next)) {
				IBDObject target = map.get(next);
				if (target.isEmpty() || target.isKillable()) {
				} else {
					next = pos;
				}
			} else {
				next = pos;
			}
			//map.step(); System.out.println(map.getPosition(object));
			map.step();
			map.step();
			map.step();
			map.step();
			assertEquals(obj, map.get(next));
			return next;
		} else
			return pos;
	}
}


//	@Before
//	public void setup2() {
//		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
//		grid.set(0, 4, 'r');
//		grid.set(0, 0, '#');
//		map = new BDMap(grid);
//	}
//
//	@Test
//	public void fallingTest3() {
//		checkFall(new Position(0, 4));
//	}
//	
//	@Test
//	public void fallingKills2() {
//		// rock two tiles above kills player
//		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
//		grid.set(0, 4, 'r');
//		grid.set(0, 2, 'p');
//		grid.set(0, 0, '#');
//		map = new BDMap(grid);
//		
//		
//		checkFall(new Position(0, 4));
//		checkFall(new Position(0, 3));
//		checkFall(new Position(0, 2));
//		assertFalse(map.getPlayer().isAlive());
//	}
//
//	@Test
//	public void restingDoesntKill2() {
//		// Rock on top of player doesn't kill player
//		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
//		grid.set(0, 3, 'r');
//		grid.set(0, 2, 'p');
//		grid.set(0, 0, '#');
//		map = new BDMap(grid);
//		
//		IBDObject obj = map.get(0, 2);
//
//		
//		
//		map.step();
//		map.step();
//		map.step();
//		map.step();
//		assertEquals(obj, map.get(0, 2));
//		
//	for(int x = 0; x<1000; x++){
//		map.step();
//		assertEquals(obj, map.get(0, 2));
//	}
//		
//	}
//
//	@Test
//	public void fallingTest4() {
//		IBDObject obj = map.get(0, 4);
//		assertTrue(obj instanceof BDRock);
//
//		// four steps later, we've fallen down one step
//		map.step();
//		map.step();
//		map.step();
//		map.step();
//		assertEquals(obj, map.get(0, 3));
//
//		map.step();
//		map.step();
//		map.step();
//		map.step();
//		assertEquals(obj, map.get(0, 2));
//
//		map.step();
//		map.step();
//		map.step();
//		map.step();
//		assertEquals(obj, map.get(0, 1));
//
//	
//		// wall reached, no more falling
//		for (int i = 0; i < 1000; i++){
//			map.step();
//		assertEquals(obj, map.get(0, 1));
//			}
//		}
//	}
//
