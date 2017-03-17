package inf101.v17.boulderdash.bdobjects;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.maps.BDMap;

/**
 * An implementation of the player.
 *
 * @author larsjaffke
 *
 */
public class BDPlayer extends AbstractBDMovingObject implements IBDKillable {

	/**
	 * Is the player still alive?
	 */
	protected boolean alive = true;

	/**
	 * The direction indicated by keypresses.
	 */
	protected Direction askedToGo;

	/**
	 * Number of diamonds collected so far.
	 */
	protected int diamondCnt = 0;

	public BDPlayer(BDMap owner) {
		super(owner);
	}

	@Override
	public Color getColor() {
		return Color.BLUE;
	}

	/**
	 * @return true if the player is alive
	 */
	public boolean isAlive() {
		return alive;
	}

	public void keyPressed(KeyCode key) {

		if (key == KeyCode.LEFT) {
			askedToGo = Direction.WEST;
		} else if (key == KeyCode.RIGHT) {
			askedToGo = Direction.EAST;
		} else if (key == KeyCode.UP) {
			askedToGo = Direction.NORTH;
		} else if (key == KeyCode.DOWN) {
			askedToGo = Direction.SOUTH;
		}

	}

	@Override
	public void kill() {
		this.alive = false;
	}

	/**
	 * Returns the number of diamonds collected so far.
	 *
	 * @return
	 */
	public int numberOfDiamonds() {
		return diamondCnt;
	}

	@Override
	public void step() {

		if (askedToGo != null) {
			Position p = getNextPosition();
			Position nextpos = p.moveDirection(askedToGo);
			IBDObject targetObj = owner.get(nextpos);

			if (owner.canGo(p, askedToGo) == true) {

				try {

					if (targetObj instanceof BDRock) {

						if (((BDRock) targetObj).push(askedToGo)) {
							prepareMoveTo(askedToGo);
						}
					} else if (targetObj instanceof BDSand) {
						prepareMoveTo(askedToGo);
					} else if (targetObj instanceof BDEmpty) {
						prepareMoveTo(askedToGo);
					} else if (targetObj instanceof BDDiamond) {
						prepareMoveTo(askedToGo);
					}

				} catch (IllegalMoveException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				IBDObject obj = owner.get(getNextPosition());
				if (obj instanceof BDDiamond) {
					diamondCnt += 1;
				}

			}

			askedToGo = null;

		}

		super.step();
	}

	@Override
	public boolean isKillable() {
		return true;
	}
}
