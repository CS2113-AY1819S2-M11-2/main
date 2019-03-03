//package seedu.address.ui;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.Label;
//import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Region;
//import seedu.address.model.person.Person;
//import seedu.address.model.task.Task;
//
///**
// * An UI component that displays information of a {@code Person}.
// */
//public class TaskCard extends UiPart<Region> {
//
//    private static final String FXML = "TaskListCard.fxml";
//
//    /**
//     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
//     * As a consequence, UI elements' variable names cannot be set to such keywords
//     * or an exception will be thrown by JavaFX during runtime.
//     *
//     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
//     */
//
//    public final Task task;
//
//    //@FXML
//    //private HBox cardPane;
//    @FXML
//    private Label id;
//    @FXML
//    private Label name;
//    @FXML
//    private Label module;
//    @FXML
//    private Label date;
//    @FXML
//    private Label priority;
//    @FXML
//    private FlowPane tags;
//
//    public TaskCard(Task task, int displayedIndex) {
//        super(FXML);
//        this.task = task;
//        id.setText(displayedIndex + ". ");
//        name.setText(task.getTaskName().fullName);
//        module.setText(task.getTaskModule().value);
//        date.setText(task.getTaskDate().value);
//        priority.setText(task.getPriority().value);
//        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        // short circuit if same object
//        if (other == this) {
//            return true;
//        }
//
//        // instanceof handles nulls
//        if (!(other instanceof TaskCard)) {
//            return false;
//        }
//
//        // state check
//        TaskCard card = (TaskCard) other;
//        return id.getText().equals(card.id.getText())
//                && task.equals(card.task);
//    }
//}
