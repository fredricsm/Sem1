package inf101.v17.boulderdash.bdobjects;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import inf101.v17.boulderdash.maps.BDMap;

/**
 * A diamond object. All its logic is implemented in the abstract superclass.
 *
 * @author larsjaffke
 *
 */
public class BDDiamond extends AbstractBDFallingObject {

	Image image  = new Image(getClass().getResourceAsStream("../bdobjects/sprites/hud_gem_yellow.png"));
	ImagePattern img = new ImagePattern(image, 1, 1, 1, 1, true);

	
	public BDDiamond(BDMap owner) {
		super(owner);
	}

	@Override
	public Paint getColor() {
		return img;
	}

}
