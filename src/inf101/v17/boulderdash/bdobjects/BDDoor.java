package inf101.v17.boulderdash.bdobjects;

import inf101.v17.boulderdash.maps.BDMap;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

public class BDDoor extends AbstractBDObject {

	private static final Image image = new Image(BDWall.class.getResourceAsStream("../bdobjects/sprites/Door.gif"));
	private ImagePattern img = new ImagePattern(image);

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
		// Does nothing. Its a Door.
	}

}
