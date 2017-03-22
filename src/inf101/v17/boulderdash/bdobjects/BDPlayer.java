package inf101.v17.boulderdash.bdobjects;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Scale;
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

	Image imageR = new Image(BDPlayer.class.getResourceAsStream("../bdobjects/sprites/MegaMan.png"));
	Image imageL = new Image(BDPlayer.class.getResourceAsStream("../bdobjects/sprites/MegaMan2.png"));
	ImagePattern player = new ImagePattern(imageR, 0, 0, 8, 3, true);

	AudioClip moveSound = new AudioClip(getClass().getResource("../bdobjects/soundEffects/slime4.wav").toString());;
	AudioClip diamondSound = new AudioClip(getClass().getResource("../bdobjects/soundEffects/coin.wav").toString());;
	AudioClip stoneSound = new AudioClip(getClass().getResource("../bdobjects/soundEffects/ConcreteBlockMoving.wav").toString());;
	AudioClip splat = new AudioClip(getClass().getResource("../bdobjects/soundEffects/Splat.wav").toString());;

	// Hvordan returnere et nytt bilde for hvert tastetrykk?

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
	public Paint getColor() {
		return player;

	}

	/**
	 * @return true if the player is alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * Will move character in the direction corresponding to key pressed and
	 * update sprite image with correct image.
	 */
	public void keyPressed(KeyCode key) {

		if (key == KeyCode.LEFT) {

			askedToGo = Direction.WEST;
			player = new ImagePattern(imageL, 4, 3, 8, 3, true);
		}

		else if (key == KeyCode.RIGHT) {
			askedToGo = Direction.EAST;
			player = new ImagePattern(imageR, 5, 3, 8, 3, true);
		} else if (key == KeyCode.UP) {
			askedToGo = Direction.NORTH;
			player = new ImagePattern(imageL, 1, 4, 8, 3, true);
		} else if (key == KeyCode.DOWN) {
			askedToGo = Direction.SOUTH;
			player = new ImagePattern(imageL, 7, 3, 8, 3, true);

		}
	}

	@Override
	public void kill() {
		splat.setVolume(0.3);
		splat.play();
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
							stoneSound.setRate(2);
							stoneSound.setVolume(0.7);
							stoneSound.play();

						}
					}

					else if (targetObj instanceof BDSand) {
						prepareMoveTo(askedToGo);
						moveSound.setVolume(0.1);
						moveSound.play();
					}

					else if (targetObj instanceof BDEmpty) {
						prepareMoveTo(askedToGo);
						moveSound.setVolume(0.1);
						moveSound.play();
					}

					else if (targetObj instanceof BDDiamond) {
						prepareMoveTo(askedToGo);
						diamondSound.play();
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
