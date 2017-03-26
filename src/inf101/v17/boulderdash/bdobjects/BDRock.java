package inf101.v17.boulderdash.bdobjects;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.maps.BDMap;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

public class BDRock extends AbstractBDFallingObject {

	// https://www.google.no/search?q=stone+pixel+art&source=lnms&tbm=isch&sa=X&ved=0ahUKEwjrhcaN0OfSAhUxSJoKHdZYCPAQ_AUICCgB&biw=1517&bih=654#tbm=isch&q=boulder+vector&*&imgrc=pN206JuPNSS_nM:
	// Setter til private static final. Hvis ikke vil nytt bilde måtte hentes
	// inn for hver eneste refresh for hvert object.
	private static final Image image = new Image(BDRock.class.getResourceAsStream("../bdobjects/sprites/boulder.png"));
	private ImagePattern img = new ImagePattern(image);

	private AudioClip noCanGo = new AudioClip(getClass().getResource("../bdobjects/soundEffects/LowFQThump.wav").toString());;

	public BDRock(BDMap owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Paint getColor() {
		return img;
	}

	/**
	 * The push method will return the value true if a push was successfully
	 * executed.
	 */

	public boolean push(Direction dir) {
		// lager en variabel pos som henter objektet sin posisjon på mappet
		Position pos = owner.getPosition(this);
		// IBDObject rock = owner.get(pos);

		if (dir == Direction.EAST || dir == Direction.WEST) {
			Position nextpos = pos.moveDirection(dir);
			if (owner.canGo(nextpos) && owner.get(nextpos) instanceof BDEmpty) {
				try {
					prepareMove(nextpos);
					step();
					return true;
				} catch (IllegalMoveException e) {
					e.printStackTrace();
				}
			}

		}
		noCanGo.setVolume(0.7);
		noCanGo.play();
		return false;

	}
}
