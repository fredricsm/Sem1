package inf101.v17.boulderdash.bdobjects;

import java.util.Random;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.maps.BDMap;
import javafx.scene.media.AudioClip;

/**
 * Contains most of the logic associated with objects that fall such as rocks
 * and diamonds.
 *
 * @author larsjaffke
 *
 */
public abstract class AbstractBDFallingObject extends AbstractBDKillingObject {

	/**
	 * A timeout between the moment when an object can fall (e.g. the tile
	 * underneath it becomes empty) and the moment it does. This is necessary to
	 * make sure that the player doesn't get killed immediately when walking
	 * under a rock.
	 */
	protected static final int WAIT = 1;

	protected boolean falling = false;

	protected AudioClip rockFalling = new AudioClip(getClass().getResource("../bdobjects/soundEffects/BoulderImpact.wav").toString());
	protected AudioClip gemFalling = new AudioClip(getClass().getResource("../bdobjects/soundEffects/GemPling.wav").toString());

	/**
	 * A counter to keep track when the falling should be executed next, see the
	 * WAIT constant.
	 */
	protected double fallingTimeWaited = 0;

	public AbstractBDFallingObject(BDMap owner) {
		super(owner);
	}

	/**
	 * This method implements the logic of the object falling. It checks whether
	 * it can fall, depending on the object in the tile underneath it and if so,
	 * tries to prepare the move.
	 */

	public void fall() {
		// Wait until its time to fall
		if (falling && fallingTimeWaited < WAIT) {
			fallingTimeWaited++;
			return;
		}
		// The timeout is over, try and prepare the move
		fallingTimeWaited = 0;

		Position pos = owner.getPosition(this);

		// The object cannot fall if it is on the lowest row.
		if (pos.getY() > 0) {
			try {
				// Get the object in the tile below.

				Position below = pos.moveDirection(Direction.SOUTH);
				IBDObject under = owner.get(below);
				IBDObject currentObject = owner.get(getPosition());

				// Implementation of rock slide functionality.
				int x = currentObject.getX();
				int y = currentObject.getY();
				IBDObject left = null;
				IBDObject diaLeft = null;
				IBDObject right = null;
				IBDObject diaRight = null;

				if (x < getMap().getWidth() - 1 && y > 0) {
					diaRight = owner.get(x + 1, y - 1);
				}

				if (x < getMap().getWidth() - 1) {
					right = owner.get(x + 1, y);
				}

				if (x > 0 && y > 0) {
					diaLeft = owner.get(x - 1, y - 1);
				}

				if (x > 0) {
					left = owner.get(x - 1, y);
				}

				if (falling) {
					// fall one step if tile below is empty or killable
					if (under instanceof BDEmpty || under instanceof IBDKillable) {
						prepareMoveTo(Direction.SOUTH);

					}

					else {

						falling = false;
						// The sounds made by gems and rock falling down

						if (currentObject instanceof BDRock) {
							rockFalling.setVolume(0.4);
							rockFalling.play();
						}

						else if (currentObject instanceof BDDiamond) {
							gemFalling.setVolume(0.1);
							gemFalling.play();
						}
					}
				}

				// Code excerpt responsible for sliding rocks left or right.
				else if (under instanceof BDRock || under instanceof BDWall || under instanceof BDDiamond) {

					if ((diaRight instanceof BDEmpty && right instanceof BDEmpty)
							|| (diaRight instanceof BDPlayer && right instanceof BDEmpty)) {
						if (right instanceof BDEmpty || left instanceof BDEmpty) {
							Random rand = new Random();
							if (rand.nextInt(100) % 2 == 0) {
								prepareMoveTo(Direction.EAST);
							}
						}
					} else if (diaLeft instanceof BDEmpty && left instanceof BDEmpty
							|| diaLeft instanceof BDPlayer && left instanceof BDEmpty) {
						if (right instanceof BDEmpty || left instanceof BDEmpty) {
							Random rand = new Random();
							if (rand.nextInt(100) % 2 == 0) {
								prepareMoveTo(Direction.WEST);
							}
						}
					}

				} else {
					// start falling if tile below is empty

					falling = (under instanceof BDEmpty);
					fallingTimeWaited = 1;
				}
			}

			catch (IllegalMoveException e) {
				// This should never happen.
				System.out.println(e);
				System.exit(1);
			}
		}
	}

	@Override
	public void step() {
		// (Try to) fall if possible

		fall();
		super.step();
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
}
