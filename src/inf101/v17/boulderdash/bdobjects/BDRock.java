package inf101.v17.boulderdash.bdobjects;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.maps.BDMap;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class BDRock extends AbstractBDFallingObject {

	public boolean moving = false;

	public BDRock(BDMap owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}

	{

	}

	@Override
	public Paint getColor() {
		return Color.DARKGRAY;
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

		return false;

	}
}
