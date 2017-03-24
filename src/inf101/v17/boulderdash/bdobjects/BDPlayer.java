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
	ImagePattern playerColor = new ImagePattern(imageR, 0, 0, 8, 3, true);

	AudioClip moveSound = new AudioClip(getClass().getResource("../bdobjects/soundEffects/slime4.wav").toString());;
	AudioClip diamondSound = new AudioClip(getClass().getResource("../bdobjects/soundEffects/coin.wav").toString());;
	AudioClip stoneSound = new AudioClip(getClass().getResource("../bdobjects/soundEffects/ConcreteBlockMoving.wav").toString());;
	AudioClip splat = new AudioClip(getClass().getResource("../bdobjects/soundEffects/Splat.wav").toString());;

	int countL = 1;
	int countR = 1;
	int countU = 1;
	int countD = 1;
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
		return playerColor;

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

			if (countL <= 3) {

				if (countL == 1) {
					askedToGo = Direction.WEST;
					playerColor = new ImagePattern(imageL, 4, 3, 8, 3, true);
					countL++;
					if (countL > 3) {
						countL = 1;
					}
				} else if (countL == 2) {
					askedToGo = Direction.WEST;
					playerColor = new ImagePattern(imageL, 5, 3, 8, 3, true);
					countL++;
					if (countL > 3) {
						countL = 1;
					}

				} else if (countL == 3) {
					askedToGo = Direction.WEST;
					playerColor = new ImagePattern(imageL, 6, 3, 8, 3, true);
					countL++;

					if (countL > 3) {
						countL = 1;
					}
				}
			}
		}

		else if (key == KeyCode.RIGHT) {

			if (countR <= 3) {

				if (countR == 1) {
					askedToGo = Direction.EAST;
					playerColor = new ImagePattern(imageR, 5, 3, 8, 3, true);
					countR++;
					if (countR > 3) {
						countR = 1;
					}
				} else if (countR == 2) {
					askedToGo = Direction.EAST;
					playerColor = new ImagePattern(imageR, 4, 3, 8, 3, true);
					countR++;
					if (countR > 3) {
						countR = 1;
					}

				} else if (countR == 3) {
					askedToGo = Direction.EAST;
					playerColor = new ImagePattern(imageR, 3, 3, 8, 3, true);
					countR++;

					if (countR > 3) {
						countR = 1;
					}
				}
			}
		}

		else if (key == KeyCode.UP) {


			if (countU <= 2) {

				if (countU == 1) {
					askedToGo = Direction.NORTH;
					playerColor = new ImagePattern(imageR, 7, 1, 8, 3, true);
					countU++;
					if (countU > 2) {
						countU = 1;
					}
				} else if (countU == 2) {
					askedToGo = Direction.NORTH;
					playerColor = new ImagePattern(imageR, 8, 1, 8, 3, true);
					countU++;
					if (countU > 2) {
						countU = 1;
					}
				}
			}
			

		} else if (key == KeyCode.DOWN) {

			if (countD <= 2) {

				if (countD == 1) {
					askedToGo = Direction.SOUTH;
					playerColor = new ImagePattern(imageR, 5, 1, 8, 3, true);
					countD++;
					if (countD > 2) {
						countD = 1;
					}
				} else if (countD == 2) {
					askedToGo = Direction.SOUTH;
					playerColor = new ImagePattern(imageL, 4, 1, 8, 3, true);
					countD++;
					if (countD > 2) {
						countD = 1;
					}
				}
			}
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

	
	/**
	 * Method is modified	
	 */	
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
						diamondCnt += 1;
					}

				} catch (IllegalMoveException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
