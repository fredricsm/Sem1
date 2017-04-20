package inf101.v17.boulderdash.maps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.bdobjects.BDBug;
import inf101.v17.boulderdash.bdobjects.BDDiamond;
import inf101.v17.boulderdash.bdobjects.BDDoor;
import inf101.v17.boulderdash.bdobjects.BDEmpty;
import inf101.v17.boulderdash.bdobjects.BDPlayer;
import inf101.v17.boulderdash.bdobjects.BDProjectile;
import inf101.v17.boulderdash.bdobjects.BDRock;
import inf101.v17.boulderdash.bdobjects.BDSand;
import inf101.v17.boulderdash.bdobjects.BDWall;
import inf101.v17.boulderdash.bdobjects.IBDObject;
import inf101.v17.datastructures.IGrid;
import inf101.v17.datastructures.MyGrid;
import javafx.scene.media.AudioClip;

/**
 * An implementation of a map
 * 
 * @author larsjaffke
 *
 */
public class BDMap {
	AudioClip ambiance = new AudioClip(getClass().getResource("../bdobjects/soundEffects/Cavern.wav").toString());;
	AudioClip noCanGo = new AudioClip(getClass().getResource("../bdobjects/soundEffects/LowFQThump.wav").toString());;

	/**
	 * Stores the data of the map
	 */
	private IGrid<IBDObject> grid;
	/**
	 * A separate reference to the player, since it is accessed quite
	 * frequently.
	 */
	protected BDPlayer player;
	
	protected BDProjectile bullet;
//	Not implemented
//	Map<IBDObject, Position> posOverview = new HashMap<>();

	/**
	 * Main constructor of this class.
	 * 
	 * @param map
	 *            A grid of characters, where each character represents a type
	 *            of {@link #IBDObject}: ' ' - {@link #BDEmpty}, '*' -
	 *            {@link BDWall}, '#' - {@link #BDSand}, 'd' -
	 *            {@link #BDDiamond}, 'b' - {@link BDBug}, 'r' -
	 *            {@link #BDRock}, 'p' - {@link #BDPlayer}
	 * 
	 * @param playerColor
	 *            The player object has to be initialized separately.
	 */
	public BDMap(IGrid<Character> map) {
		setGrid(new MyGrid<IBDObject>(map.getWidth(), map.getHeight(), null));
		this.player = new BDPlayer(this);
		fillGrid(map);

		// Sets the ambiance sound in the mine
		ambiance.setCycleCount(5);
		ambiance.setVolume(0.7);
		ambiance.play();
	}

	/**
	 * True of the object can move in direction dir, false otherwise.
	 * 
	 * @param obj
	 * @param dir
	 * @return
	 */
	public boolean canGo(IBDObject obj, Direction dir) {
		return canGo(this.getPosition(obj), dir);
	}

	/**
	 * True if an object can move to positio (x, y), false otherwise.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean canGo(int x, int y) {
		if (!isValidPosition(x, y)) {

			noCanGo.setVolume(0.3);
			noCanGo.play();
			return false;

		} else if (getGrid().get(x, y) instanceof BDWall) {

			noCanGo.setVolume(0.3);
			noCanGo.play();
			return false;
		}

		return true;

	}

	/**
	 * See {@link #canGo(int, int)}
	 * 
	 * @param pos
	 * @return
	 */
	public boolean canGo(Position pos) {
		return canGo(pos.getX(), pos.getY());
	}

	/**
	 * Cf. {@link #canGo(IBDObject, Direction)}
	 * 
	 * @param pos
	 * @param dir
	 * @return
	 */
	public boolean canGo(Position pos, Direction dir) {
		switch (dir) {
		case NORTH:
			return canGo(pos.getX(), pos.getY() + 1);
		case SOUTH:
			return canGo(pos.getX(), pos.getY() - 1);
		case EAST:
			return canGo(pos.getX() + 1, pos.getY());
		case WEST:
			return canGo(pos.getX() - 1, pos.getY());
		default:
			break;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * This method is used to initialize the map with a given map of characters,
	 * see also {@link #BDMap(IGrid, BDPlayer)}.
	 * 
	 * @param inputmap
	 */
	private void fillGrid(IGrid<Character> inputmap) {
		int width = inputmap.getWidth();
		int height = inputmap.getHeight();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				IBDObject obj = makeObject(inputmap.get(x, y), x, y);
				getGrid().set(x, y, obj);

			}
		}
	}

	/**
	 * Initialize an object according to the given character, see also
	 * {@link #BDMap(IGrid, BDPlayer)}.
	 * 
	 * @param c
	 * @param x
	 * @param y
	 * @return
	 */
	private IBDObject makeObject(Character c, int x, int y) {
		if (c == 'p') {
			return this.player;
		} else if (c == '*') {
			return new BDWall(this);
		} else if (c == 'b') {
			try {
				return new BDBug(this, new Position(x, y), 1, 10);
			} catch (IllegalMoveException e) {
				e.printStackTrace();
			}
		} else if (c == '#') {
			return new BDSand(this);
		} else if (c == ' ') {
			return new BDEmpty(this);
		} else if (c == 'd') {
			return new BDDiamond(this);
		}
		else if (c == 'r') {
			return new BDRock(this);
		}
	
		else if (c == 'e'){
			return new BDDoor(this);
		}
		
		System.err.println("Illegal character in map definition at (" + x + ", " + y + "): '" + c + "'");
		return new BDEmpty(this);
		// alternatively, throw an exception
		// throw new IllegalArgumentException();
	}

	/**
	 * get the object in (x, y)
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public IBDObject get(int x, int y)  {
		return getGrid().get(x, y);
	}

	/**
	 * see {@link #get(int, int)}
	 * 
	 * @param pos
	 * @return
	 */
	public IBDObject get(Position pos) {
		return getGrid().get(pos.getX(), pos.getY());
	}

	/**
	 * Returns the height of this map.
	 * 
	 * @return
	 */
	public int getHeight() {
		return getGrid().getHeight();
	}

	/**
	 * Given a position pos, this method computes the nearest n empty fields.
	 * This method is used when a bug dies and had to spread a certain number of
	 * diamonds in neighboring fields.
	 * 
	 * @param pos
	 * @param n
	 * @return
	 */
	public List<Position> getNearestEmpty(Position pos, int n) {
		List<Position> empty = new ArrayList<>();
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				if (get(x, y) instanceof BDEmpty) {
					empty.add(new Position(x, y));
				}
			}
		}
		if (empty.size() < n) {
			throw new IllegalArgumentException("There are less than " + n + " empty fields on this map");
		}
		Collections.sort(empty, new Comparator<Position>() {

			@Override
			public int compare(Position o1, Position o2) {
				return pos.distanceTo(o1) - pos.distanceTo(o2);
			}
		});
		List<Position> nearby = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			nearby.add(empty.get(i));
		}
		return nearby;
	}

	/**
	 * Get the player in this map.
	 * 
	 * @returns
	 */
	public BDPlayer getPlayer() {
		return player;
	}
	
	
	/**
	 * Get the bullet in this map.
	 * 
	 * @returns
	 */
	public BDProjectile getProjectile() {
		return bullet;
	}

	/**
	 * Get the position of an object in this map
	 * 
	 * @param object
	 * @return
	 */
	
	
	public Position getPosition(IBDObject object) {

		
		for (int x = 0; x < getGrid().getWidth(); x++) {
			for (int y = 0; y < getGrid().getHeight(); y++) {
				if (getGrid().get(x, y) == object) {
					Position p = new Position(x, y);
					return p;					
				}
			}
		}
		return null;
	}

	/**
	 * get the width of this map.
	 * 
	 * @return
	 */
	public int getWidth() {
		return getGrid().getWidth();
	}

	/**
	 * Check whether (x, y) is a valid position in this map.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isValidPosition(int x, int y) {
		return x > -1 && x < getGrid().getWidth() && y > -1 && y < getGrid().getHeight();
	}

	/**
	 * set tile (x, y) to element.
	 * 
	 * @param x
	 * @param y
	 * @param element
	 */
	public void set(int x, int y, IBDObject element) {
		if (!isValidPosition(x, y)) {
			throw new IndexOutOfBoundsException();
		}
		getGrid().set(x, y, element);
	}

	// setter kun step metoden.
	public void step() {

		for (int x = 0; x < getWidth()-1; x++) {
			for (int y = 0; y < getHeight()-1; y++) {
			
				getGrid().get(x, y).step();
			
				
			}
		}
		

	}
	

	public IGrid<IBDObject> getGrid() {
		return grid;
	}

	public void setGrid(IGrid<IBDObject> grid) {
		this.grid = grid;
	}
	
}
