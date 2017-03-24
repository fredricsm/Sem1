package inf101.v17.boulderdash.bdobjects.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.bdobjects.AbstractBDFallingObject;
import inf101.v17.boulderdash.bdobjects.BDBug;
import inf101.v17.boulderdash.bdobjects.BDDiamond;
import inf101.v17.boulderdash.bdobjects.IBDObject;
import inf101.v17.boulderdash.maps.BDMap;
import inf101.v17.datastructures.IGrid;
import inf101.v17.datastructures.MyGrid;

public class BugTest {

	private BDMap map;

	@Before
	public void setup() {
	}

	@Test
	public void bugMoves() {
		IGrid<Character> grid = new MyGrid<>(4, 4, ' ');
		grid.set(2, 2, 'b');
		map = new BDMap(grid);

		// find the bug
		Position bugPos = new Position(2,2);
		IBDObject bug = map.get(bugPos);
		assertTrue(bug instanceof BDBug);
		
		for(int i = 0; i < 100; i++) {
			map.step();
			if(map.get(bugPos) != bug) { // bug has moved
				// reported position should be different
				assertNotEquals(bugPos, map.getPosition(bug));
				// bug moved –  we're done
				return;
			}
			
		}
		
		fail("Bug should have moved in 100 steps!");
	}

	@Test
	public void bugMovesNotWall() {
		IGrid<Character> grid = new MyGrid<>(4, 4, '*');
		grid.set(2, 2, 'b');
		map = new BDMap(grid);

		// find the bug
		Position bugPos = new Position(2,2);
		IBDObject bug = map.get(bugPos);
		assertTrue(bug instanceof BDBug);
		
		for(int i = 0; i < 100; i++) {
			map.step();
			if(map.get(bugPos) == bug) { // get position of bug and bugPos
				// reported position should not be different
				assertEquals(bugPos, bug.getPosition());
				// Bug is kept still
				return;
			}
			
		}
		
		fail("Bug should not have moved");
	}

	
	
	@Test
	public void bugMovesNotSand() {
		IGrid<Character> grid = new MyGrid<>(4, 4, '#');
		grid.set(2, 2, 'b');
		map = new BDMap(grid);

		// find the bug
		Position bugPos = new Position(2,2);
		IBDObject bug = map.get(bugPos);
		assertTrue(bug instanceof BDBug);
		
		for(int i = 0; i < 100; i++) {
			map.step();
			if(map.get(bugPos) == bug) { // get position of bug and bugPos
				// reported position should not be different
				assertEquals(bugPos, bug.getPosition());
				// Bug is kept still
				return;
			}
			
		}
		
		fail("Bug should not have moved");
	}
	@Test
	public void bugMovesAsExpected() {
		IGrid<Character> grid = new MyGrid<>(4, 4, ' ');
		grid.set(2, 2, 'b');
		map = new BDMap(grid);

		// find the bug
		Position bugPos = new Position(2,2);
		IBDObject bug = map.get(bugPos);
		assertTrue(bug instanceof BDBug);
		
		for(int i = 0; i < 100; i++) {
			map.canGo(bug, Direction.NORTH);
			if(map.get(bugPos) == bug) { // get position of bug and bugPos
				// reported position should not be different
				assertNotEquals(bugPos, bug);
				// Bug is kept still
				return;
			}
			
		}
		
		fail("Bug should not have moved");
	}


}
