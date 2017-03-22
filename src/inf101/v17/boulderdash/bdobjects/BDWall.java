package inf101.v17.boulderdash.bdobjects;

import javafx.scene.image.Image;

import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

import inf101.v17.boulderdash.maps.BDMap;

/**
 * Implementation of a piece of a wall.
 *
 * @author larsjaffke
 *
 */
public class BDWall extends AbstractBDObject {
	// cannot instanciate image betyr at klassen ikke er importert.

	// Tiles from
	// http://www.indiedb.com/games/chesslike-adventures-in-chess/images/tile-themes-sprite-sheet
	private static final Image image = new Image(BDWall.class.getResourceAsStream("../bdobjects/sprites/SpriteTile.png"));
	ImagePattern img = new ImagePattern(image, 6, 3.7, 8, 8, true);

	public BDWall(BDMap owner) {
		super(owner);

	}

	@Override
	public Paint getColor() {

		return img;

	}

	@Override
	public void step() {
		// DO NOTHING, IT'S A WALL
	}
}
