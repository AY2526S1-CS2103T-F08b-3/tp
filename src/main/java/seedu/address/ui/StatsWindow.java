package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a stats page
 */
public class StatsWindow extends UiPart<Stage> {

    public static final String STATS_MESSAGE = "ConnectEd Statistics";

    private static final Logger logger = LogsCenter.getLogger(StatsWindow.class);
    private static final String FXML = "StatsWindow.fxml"; // changed from HelpWindow.fxml

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new StatsWindow.
     *
     * @param root Stage to use as the root of the StatsWindow.
     */
    public StatsWindow(Stage root) {
        super(FXML, root);
        // injected fields are available after FXMLLoader finished loading in UiPart
        helpMessage.setText(STATS_MESSAGE);
    }

    /**
     * Creates a new StatsWindow.
     */
    public StatsWindow() {
        this(new Stage());
    }

    /**
     * Shows the stats window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing stats page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the stats window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the stats window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the stats window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Sets the stats text to be shown in the window.
     * Call this before show() to update the displayed text.
     */
    public void setStatsText(String text) {
        // defensive: ensure non-null
        if (text == null) {
            helpMessage.setText(STATS_MESSAGE);
        } else {
            helpMessage.setText(text);
        }
    }
}
