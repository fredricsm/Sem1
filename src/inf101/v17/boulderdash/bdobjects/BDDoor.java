package inf101.v17.boulderdash.bdobjects;

import inf101.v17.boulderdash.maps.BDMap;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

public class BDDoor  extends AbstractBDObject {

	
	private static final Image image = new Image(BDWall.class.getResourceAsStream("../bdobjects/sprites/ArrowDown.gif"));
	ImagePattern img = new ImagePattern(image);
	AudioClip splat = new AudioClip(getClass().getResource("../bdobjects/soundEffects/Splat.wav").toString());;
	
	
	public BDDoor(BDMap owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}

	
	
	
	@Override
	public Paint getColor() {
		// TODO Auto-generated method stub
		return img;
	}

	@Override
	public void step() {
//Does nothing. Its a Door.		
	}

}
