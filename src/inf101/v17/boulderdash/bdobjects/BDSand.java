package inf101.v17.boulderdash.bdobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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
	
	//Tiles from http://www.indiedb.com/games/chesslike-adventures-in-chess/images/tile-themes-sprite-sheet
	private static final Image image = new Image(BDSand.class.getResourceAsStream("../bdobjects/sprites/master-tileset.png"));
	ImagePattern img = new ImagePattern(image, 4, 4, 10, 5, true);
	
	
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
