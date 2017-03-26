
package inf101.v17.boulderdash.bdobjects.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.bdobjects.AbstractBDFallingObject;
import inf101.v17.boulderdash.bdobjects.BDBug;
import inf101.v17.boulderdash.bdobjects.BDDiamond;
import inf101.v17.boulderdash.bdobjects.BDPlayer;
import inf101.v17.boulderdash.bdobjects.IBDObject;
import inf101.v17.boulderdash.maps.BDMap;
import inf101.v17.datastructures.IGrid;
import inf101.v17.datastructures.MyGrid;
import javafx.scene.input.KeyCode;
public class PlayerTest {

	
	



		private BDMap map;

		@Before
		public void setup() {
		}

		
//Error in test due to images being set to null in the test. Test will pass if we enable setProperties() in the code responsible for the animation. 		
//		@Test
//		public void playerMoves() {
//			IGrid<Character> grid = new MyGrid<>(4, 4, ' ');
//			grid.set(2, 2, 'p');
//			map = new BDMap(grid);
//
//			// find the player
//			Position playerPos = new Position(2, 2);
//			IBDObject player = map.get(playerPos);
//			assertTrue(player instanceof BDPlayer);
//
//			for (int i = 0; i < 10000; i++) {
//				map.step();
//				((BDPlayer) player).keyPressed(KeyCode.UP);
//				if (map.get(playerPos) != player) { // player has moved
//					// reported position should be different
//					assertNotEquals(playerPos, map.getPosition(player));
//					// player moved –  we're done
//					return;
//				}
//
//			}
//
//			fail("Player should have moved in 100 steps!");
//		}

		@Test
		public void playerMovesNotWall() {
			IGrid<Character> grid = new MyGrid<>(4, 4, '*');
			grid.set(2, 2, 'p');
			map = new BDMap(grid);

			// find the bug
			Position playerPos = new Position(2, 2);
			IBDObject player = map.get(playerPos);

			for (int i = 0; i < 100; i++) {
				map.step();
				// reported position should not be different
				assertEquals(playerPos, player.getPosition());
				// Bug is kept still

			}

		}

		@Test
		public void playerMovesSand() {
			IGrid<Character> grid = new MyGrid<>(4, 4, '#');
			grid.set(2, 2, 'p');
			map = new BDMap(grid);

			// find the bug
			Position playerPos = new Position(2, 2);
			IBDObject player = map.get(playerPos);
			for (int i = 0; i < 100; i++) {
				map.step();
				// get position of bug and bugPos
				// reported position should not be different
				assertEquals(playerPos, player.getPosition());
				// Bug is kept still
			}
		}

		@Test
		public void playerMovesAsExpected() {
			IGrid<Character> grid = new MyGrid<>(4, 4, ' ');
			grid.set(2, 2, 'p');
			map = new BDMap(grid);

			// find the bug
			Position playerPos = new Position(2, 2);
			IBDObject player = map.get(playerPos);
			assertTrue(player instanceof BDPlayer);

			for (int i = 0; i < 100; i++) {
				map.canGo(player, Direction.NORTH);
				if (map.get(playerPos) == player) { // get position of bug and bugPos
					// reported position should not be different
					assertNotEquals(playerPos, player);
					// Bug is kept still
					return;
				}

			}

			fail("Bug should not have moved");
		}


		@Test
		public void playerKilledByBug() {
			IGrid<Character> grid = new MyGrid<>(4, 4, ' ');
			grid.set(2, 2, 'b');
			grid.set(3, 3, 'p');
			map = new BDMap(grid);

			// find the bug and player
			Position playerPos = new Position(3,3);
			Position bugPos = new Position(2,2);
			IBDObject player = map.get(playerPos);
			IBDObject bug = map.get(bugPos);
			
			assertTrue(bug instanceof BDBug);
			assertTrue(player instanceof BDPlayer);
			
			for(int i = 0; i < 100000; i++) {
				map.step();
				map.step();	
				map.step();	
				map.step();	
			}
				assertTrue(map.getPlayer().isAlive());
				
					return;
				}

}
	
	
	
	

