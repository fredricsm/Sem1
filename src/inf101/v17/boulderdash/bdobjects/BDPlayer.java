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

	Image image  = new Image(BDPlayer.class.getResourceAsStream("../bdobjects/sprites/Adventure.gif"));

	ImagePattern player = new ImagePattern(image,1,1,1,1,true);
	
	AudioClip moveSound = new AudioClip(getClass().getResource("../bdobjects/soundEffects/slime4.wav").toString());;
	AudioClip diamondSound = new AudioClip(getClass().getResource("../bdobjects/soundEffects/coin.wav").toString());;
	AudioClip stoneSound = new AudioClip(getClass().getResource("../bdobjects/soundEffects/ConcreteBlockMoving.wav").toString());;
	AudioClip splat = new AudioClip(getClass().getResource("../bdobjects/soundEffects/Splat.wav").toString());;

// Hvordan returnere et nytt bilde for hvert tastetrykk?  

//	public Paint runner(KeyCode key){
//	
//	int x = 0;
//	int y = 0;
//	
//	ImagePattern img = new ImagePattern(image,x,y,2,1,true);
//	
//	if(key == KeyCode.LEFT){
//	
//	}
//
//	
//	return img;
//}
//	
	
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
