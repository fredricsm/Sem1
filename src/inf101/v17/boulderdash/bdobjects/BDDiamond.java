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

	private static final Image image = new Image(BDDiamond.class.getResourceAsStream("../bdobjects/sprites/Diamond1.gif"));
	ImagePattern img = new ImagePattern(image);

	public BDDiamond(BDMap owner) {
		super(owner);
	}

	@Override
	public Paint getColor() {
		return img;
	}
}
