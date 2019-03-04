package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Date;
import seedu.address.model.person.Name;
import seedu.address.model.person.Task;
import seedu.address.model.person.Module;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditCommand.EditTaskDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditCommand.EditTaskDescriptor();
    }

    public EditPersonDescriptorBuilder(EditCommand.EditTaskDescriptor descriptor) {
        this.descriptor = new EditCommand.EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details
     */
    public EditPersonDescriptorBuilder(Task task) {
        descriptor = new EditCommand.EditTaskDescriptor();
        descriptor.setName(task.getName());
        descriptor.setModule(task.getModule());
        descriptor.setDate(task.getDate());
        descriptor.setPriority(task.getPriority());
        descriptor.setTags(task.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Module} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setModule(new Module(phone));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setDate(new Date(email));
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setPriority(new Priority(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditTaskDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}
