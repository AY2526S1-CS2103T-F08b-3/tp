package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Session;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label subject;
    @FXML
    private Label level;
    @FXML
    private Label price;
    @FXML
    private Label personId;
    @FXML
    private Label matched;
    @FXML
    private FlowPane rolePane;
    @FXML
    private Label session;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        personId.setText("id: #" + person.getPersonId());
        Label roleChip = new Label(person.getRole()); // "tutor" or "student"
        roleChip.getStyleClass().addAll("tag", "role-" + person.getRole().toLowerCase());
        rolePane.getChildren().setAll(roleChip); // ensure only the role chip is here
        Person mp = person.getMatchedPerson();
        if (mp != null) {
            String mpName = mp.getName().fullName;
            String mpId = "#" + mp.getPersonId();
            matched.setText("Matched: " + mpName + " (" + mpId + ")");
        } else {
            matched.setText("Matched: —");
        }
        Session s = person.getSession();
        if (s != null) {
            session.setText(formatSession(s));
        } else {
            session.setText("—");
        }
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        subject.setText("Subject: " + person.getSubject());
        level.setText("Level: " + person.getLevel());
        price.setText("Price: " + person.getPrice());
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private String formatSession(Session s) {
        // Day: "MONDAY" -> "Monday"
        String day = s.getDay().toString().charAt(0) + s.getDay().toString().substring(1).toLowerCase();

        // Time: LocalTime -> "HH:mm"
        String time = s.getTime().toString(); // already "HH:mm"; or use .format(DateTimeFormatter.ofPattern("HH:mm"))

        // Duration -> "HH:mm"
        java.time.Duration d = s.getDuration();
        long hours = d.toHours();
        long minutes = d.minusHours(hours).toMinutes();
        String dur = String.format("%02d:%02d", hours, minutes);

        // Subject / Price as strings (adjust to your API)
        String subj = s.getSubject().toString();
        String price = s.getPrice().toString();

        // Final string (drop the literal "Session:" here because your FXML already shows "Session:")
        return String.format("%s | %s | %s hrs | %s | $%s", day, time, dur, subj, price);
    }
}
