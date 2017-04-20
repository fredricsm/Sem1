package inf101.v17.boulderdash.bdobjects;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import inf101.v17.boulderdash.maps.BDMap;

/**
 * An implementation of sand which simply disappears when the player walks over
 * it. Nothing to do here.
 *
 * @author larsjaffke
 *
 */
public class BDSand extends AbstractBDObject {

	private static final Image image = new Image(BDSand.class.getResourceAsStream("../bdobjects/sprites/SpriteTile.png"));
	private ImagePattern img = new ImagePattern(image, 4, 2, 8, 8, true);

	public BDSand(BDMap owner) {
		super(owner);
	}

	@Override
	public Paint getColor() {

		return img;
	}

	@Override
	public void step() {
		// DO NOTHING
	}
}
