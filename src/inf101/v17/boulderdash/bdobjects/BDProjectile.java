package inf101.v17.boulderdash.bdobjects;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.maps.BDMap;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

public class BDProjectile extends AbstractBDKillingObject implements IBDMovingObject {

	private static Image image = new Image(BDWall.class.getResourceAsStream("../bdobjects/sprites/laserShot.gif"));
	private static ImagePattern img = new ImagePattern(image);
	private AudioClip impact = new AudioClip(getClass().getResource("../bdobjects/soundEffects/impact.wav").toString());;

	protected static final double MIN_PAUSE = 1;
	protected double movedSince = 0;
	protected double pause = MIN_PAUSE;
	private Position nextPos;
	private Direction dir;


	BDProjectile(BDMap owner) {
		super(owner);

		// TODO Auto-generated constructor stub

	}
	
	public void setDir(Direction dir){
		this.dir = dir;
	}

	public void setNextPos() {
		
		Position cur = owner.getPosition(this);
		nextPos = cur.moveDirection(dir);
		IBDObject nextObject = owner.get(nextPos);	
		
		if (nextObject instanceof BDEmpty || nextObject instanceof BDBug) {
			try {
				prepareMove(nextPos);
			} catch (IllegalMoveException e) {
				e.printStackTrace();
			}
		}

		else if (nextObject instanceof BDRock || nextObject instanceof BDSand || nextObject instanceof BDWall || nextObject instanceof BDDiamond) {
			getMap().set(getX(), getY(), new BDEmpty(owner));
			impact.setVolume(0.3);
			impact.play();
		}
	}

	public void step() {

		if(movedSince == pause){
			setNextPos();
			movedSince = 0;
		}
		else{
			movedSince++;
		}
			super.step();
		}
	
	
	
	public void setProperties(){
		  image = new Image(BDWall.class.getResourceAsStream("../bdobjects/sprites/laserShot.gif"));
		  img = new ImagePattern(image);
		  impact = new AudioClip(getClass().getResource("../bdobjects/soundEffects/impact.wav").toString());;
	}

	@Override
	public Paint getColor() {
		if (image == null) {
			setProperties();
		}
		return img;
	}

}
