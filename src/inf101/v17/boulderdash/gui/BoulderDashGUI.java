package inf101.v17.boulderdash.gui;

import inf101.v17.boulderdash.bdobjects.BDDiamond;
import inf101.v17.boulderdash.bdobjects.BDDoor;
import inf101.v17.boulderdash.bdobjects.BDWall;
import inf101.v17.boulderdash.maps.BDMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The main class of the BoulderDash program. Sets up a simple gui of a given
 * map and handles the timing.
 *
 */
public class BoulderDashGUI extends Application implements EventHandler<KeyEvent> {
	/**
	 * Determines how many milliseconds pass between two steps of the program.
	 */
	private static final int SPEED = 120;
	private static BDMap theMap;
	public static final double NOMINAL_WIDTH = 1900;
	public static final double NOMINAL_HEIGHT = 1000;
	private Stage stage;

	/**
	 * Runs the program on a given map.
	 *
	 * @param map00
	 */
	public static void run(BDMap map) {
		theMap = map;
		launch();

	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		double spacing = 10;
		newGame();

		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		Group root = new Group();
		double width = Math.min(primaryScreenBounds.getWidth() - 40, map.getWidth() * 200);
		double height = Math.min(primaryScreenBounds.getHeight() - 100, map.getWidth() * 200);
		Scene scene = new Scene(root, width, height, Color.BLACK);
		stage.setScene(scene);

		message = new Text(10, 0, "");
		message.setFont(new Font(26));
		message.setFill(Color.WHEAT);
		message.setText("Diamonds: " + map.getPlayer().numberOfDiamonds());

		mapComponent = new BDMapComponent(map);
		mapComponent.widthProperty().bind(scene.widthProperty());
		mapComponent.heightProperty().bind(scene.heightProperty());
		mapComponent.heightProperty().bind(Bindings
				.subtract(Bindings.subtract(scene.heightProperty(), message.getLayoutBounds().getHeight()), spacing));
		// mapComponent.setScaleY(-1.0);

		timer = new AnimationTimer() {

			private long lastUpdateTime;

			@Override
			public void handle(long now) {
				if (now - lastUpdateTime > SPEED * 1_000_000) {
					lastUpdateTime = now;
					step();
				}
			}

		};

		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.getChildren().add(message);
		vbox.getChildren().add(mapComponent);
		root.getChildren().add(vbox);

		stage.addEventHandler(KeyEvent.KEY_PRESSED, this);
		timer.start();
		stage.show();
	}

	private BDMap map;
	private BDMapComponent mapComponent;

	private Text message;

	private AnimationTimer timer;

	public BoulderDashGUI() {
		this.map = theMap;
	}

	/**
	 * A method that will count the number of diamonds available on the map.
	 * When the number == 0, a door will open. Extra functionality will be to
	 * implement loading of a new level when the player enters through the door.
	 * 
	 */
	public void newGame() {
		int nrDia = 0;
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				if (map.getGrid().get(x, y) instanceof BDDiamond) {
					nrDia++;
				}
			}
		}
		if (map.getWidth() >= 39) {
			if (nrDia == 0) {
				// System.exit(0);
				map.set(39, 3, new BDDoor(map));
			}

			else if (nrDia != 0) {
				map.set(39, 3, new BDWall(map));
			}
		}
	}

	protected void step() {
		if (map.getPlayer().isAlive()) {
			map.step();
			message.setText(("Diamonds: " + map.getPlayer().numberOfDiamonds())
					+ ("   Sand: " + map.getPlayer().numberOfSand()));

			if (map.getPlayer().getPosition() == null) {
				message.setText("Congratulations");

			}
		}

		else {
			message.setText("Player is dead.");
			// System.exit(0);

		}
		newGame();
		mapComponent.draw();
	}

	@Override
	public void handle(KeyEvent event) {
		KeyCode code = event.getCode();
		if (code == KeyCode.ESCAPE || code == KeyCode.Q) {
			System.exit(0);
		} else if (code == KeyCode.F) {
			stage.setFullScreen(!stage.isFullScreen());
		}
		// else if(code == KeyCode.SPACE){
		// map.getPlayer().fireWeapon();
		// }

		// Update late with functionality for relaunching map.
		// else if (code == KeyCode.L) {
		// Platform.exit();
		// String[] args = {};
		// Main.main(args);
		// }
		//
		else {

			map.getPlayer().keyPressed(code);
		}
	}

}
