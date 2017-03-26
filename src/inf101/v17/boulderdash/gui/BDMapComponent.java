package inf101.v17.boulderdash.gui;

import inf101.v17.boulderdash.maps.BDMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * A container class for a Boulder Dash map inside a paintable component.
 * 
 * @author larsjaffke
 *
 */
public class BDMapComponent extends Canvas {

	// parameters on the sizes of the cells.
	private final double padding = 1;

	// The map to be painted
	private BDMap map;

	public BDMapComponent(BDMap map) {
		this.map = map;
	}

	public void draw() {


		
		GraphicsContext g = getGraphicsContext2D();
		//Removes old picture 
		//g.clearRect(0, 0, getWidth(), getHeight());
		
		g.setFill(Color.BLACK);
		
		//New image fill
		g.fillRect(0, 0, getWidth(), getHeight());
			
		double cellDim = Math.min(getWidth() / map.getWidth() - padding,
				getHeight() / map.getHeight() - padding);

		int width = map.getWidth(), height = map.getHeight();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				g.setFill(map.get(x, height - y - 1).getColor());
				g.fillRect(x * (cellDim + padding) + padding, y * (cellDim + padding) + padding, cellDim,
						cellDim);
			}
		}
	}

}
