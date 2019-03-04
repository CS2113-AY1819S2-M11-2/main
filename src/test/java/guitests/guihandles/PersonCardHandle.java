package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Task;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class PersonCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String MODULE_FIELD_ID = "#module";
    private static final String DATE_FIELD_ID = "#date";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label priorityLabel;
    private final Label moduleLabel;
    private final Label dateLabel;
    private final List<Label> tagLabels;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        priorityLabel = getChildNode(PRIORITY_FIELD_ID);
        moduleLabel = getChildNode(MODULE_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getPriority() {
        return priorityLabel.getText();
    }

    public String getModule() {
        return moduleLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code task}.
     */
    public boolean equals(Task task) {
        return getName().equals(task.getName().fullName)
                && getPriority().equals(task.getPriority().value)
                && getModule().equals(task.getModule().value)
                && getDate().equals(task.getDate().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(task.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
