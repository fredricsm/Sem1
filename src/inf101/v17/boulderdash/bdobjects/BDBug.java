package inf101.v17.boulderdash.bdobjects;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.maps.BDMap;

/**
 * An implementation of a bug.
 *
 * @author larsjaffke
 *
 */
public class BDBug extends AbstractBDKillingObject implements IBDKillable {

	/**
	 * The amount of diamonds a bug turns into after it got killed.
	 */
	protected static final int DEATH_DIAMONDS = 8;

	/**
	 * The minimum amount of steps any bug has to pause between two moves.
	 */
	protected static final int MIN_PAUSE = 2;

	/**
	 * The position where the bug spawns when the program is loaded.
	 */
	protected Position initialPos;

	// Counts the number of skipped steps.
	protected int movedSince = 0;

	// images and sound for bug.
	private static final Image image = new Image(BDWall.class.getResourceAsStream("../bdobjects/sprites/SlimeGif.gif"));
	private ImagePattern img = new ImagePattern(image);
	private AudioClip splat = new AudioClip(getClass().getResource("../bdobjects/soundEffects/Splat.wav").toString());;

	/**
	 * This field contains the sequence of moves the bug performs repeatedly.
	 * Can be anything, but a logical set up happens in the
	 * initTrajectory()-method.
	 */
	protected List<Position> path;

	/**
	 * Every bug can move at a different speed, this field determines how many
	 * steps it has to wait in between two moves. If nothing is specified, the
	 * value is set to the constant MIN_PAUSE.
	 */
	protected int pause = MIN_PAUSE;

	/**
	 * The standard constructor, where pause is set to MIN_PAUSE and radius to
	 * 1.
	 *
	 * @param owner
	 * @param initialPos
	 * @throws IllegalMoveException
	 */
	public BDBug(BDMap owner, Position initialPos) throws IllegalMoveException {
		super(owner);
		this.initialPos = initialPos;
		// initTrajectory();
	}

	/**
	 * With this constructor both radius and pause can be specified as well.
	 * Note that the value of pause always has to be at least MIN_PAUSE,
	 * otherwise the given value has no effect.
	 *
	 * @param owner
	 * @param initialPos
	 * @param radius
	 * @param pause
	 * @throws IllegalMoveException
	 */
	public BDBug(BDMap owner, Position initialPos, int radius, int pause) throws IllegalMoveException {
		super(owner);
		this.initialPos = initialPos;
//		this.radius = radius;
		this.pause = pause < MIN_PAUSE ? MIN_PAUSE : pause;
		// initTrajectory();
	}
	/**
	 * 
	 * Method for generating a random direction that the bug can follow.
	 *  
	 */
	public Direction randomDir() {
		Random rand = new Random();
		Direction dir = null;
		int x = rand.nextInt(4);
		switch (x) {

		case 0:
			return dir = Direction.EAST;

		case 1:
			return dir = Direction.WEST;

		case 2:
			return dir = Direction.SOUTH;

		case 3:
			return dir = Direction.NORTH;
		}
		return dir;
	
	}
	@Override
	public Paint getColor() {
		return img;
	}

	@Override
	public void kill() {
		// If a bug is killed it turns into a set of diamonds. Find the
		// DEATH_DIAMONDS nearest
		// empty positions in the map and fill them with diamonds.
		splat.play();
		Collection<Position> toDiamonds = owner.getNearestEmpty(owner.getPosition(this), DEATH_DIAMONDS);
		for (Position p : toDiamonds) {
			owner.set(p.getX(), p.getY(), new BDDiamond(owner));
		}
	}

	/**
	 * Sets the next position and returns the current one. If lastDirection is equal to the 
	 * next direction, a new direction is set.
	 *  
	 */
	private void setNextPos() {
		Position cur = owner.getPosition(this);
		Position nextOne = cur.moveDirection(randomDir());

		IBDObject nextObject = owner.get(nextOne);
		Position lastOne = null;

		try {
			// If there is a rock or a diamond in the next position, the bug
			// cannot move.
			
			if(nextObject instanceof BDEmpty && nextOne != lastOne){		
				lastOne = cur;
				prepareMove(nextOne.getX(), nextOne.getY());
			}
			else{
				return;
			}
			

		} 
		catch (IllegalMoveException e) {
			// If the bug cannot move where it's supposed to, e.g. when a wall
			// is in its
			// path, there is something wrong with the map -> kill the program.
			System.err.println(e.getMessage());
			System.err.println("You made a mistake with one of the bugs " + " setting up your map.");
			System.exit(1);
		}
	}

	@Override
	public void step() {
		// Only execute the bug's movement after it had its' pause.
		// Sets pause so that it will not kill player immediately after its been
		// freed.

		if (movedSince == pause) {
			// Set the next position according to the path
			setNextPos();
			// Check if the bug will hit the player after the next step
			// and if so, kill the player.
			checkAndKillKillable();
			// Update the map.
			// Reset the pause counter
			movedSince = 0;
			// The bug has not moved, so increase the pause counter.
		} else {
			movedSince++;
		}
		super.step();
	}

	@Override
	public boolean isKillable() {
		return true;
	}
}
