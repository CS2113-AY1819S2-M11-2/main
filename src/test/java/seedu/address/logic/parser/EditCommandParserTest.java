package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_GRADED;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_UNGRADED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GRADED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNGRADED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.person.Date;
import seedu.address.model.person.Module;
import seedu.address.model.person.Name;
import seedu.address.model.person.Priority;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_PROJECT, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_PROJECT, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_PROJECT, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_MODULE_DESC, Module.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_PRIORITY_DESC, Priority.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_MODULE_DESC + DATE_DESC_PROJECT, Module.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + MODULE_DESC_TUTORIAL + INVALID_MODULE_DESC, Module.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Task} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_GRADED + TAG_DESC_UNGRADED + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_GRADED + TAG_EMPTY + TAG_DESC_UNGRADED, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_GRADED + TAG_DESC_UNGRADED, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_DATE_DESC + VALID_PRIORITY_PROJECT + VALID_MODULE_PROJECT,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + MODULE_DESC_TUTORIAL + TAG_DESC_UNGRADED
                + DATE_DESC_PROJECT + PRIORITY_DESC_PROJECT + NAME_DESC_PROJECT + TAG_DESC_GRADED;

        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_PROJECT)
                .withPhone(VALID_MODULE_TUTORIAL).withEmail(VALID_DATE_PROJECT).withAddress(VALID_PRIORITY_PROJECT)
                .withTags(VALID_TAG_GRADED, VALID_TAG_UNGRADED).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + MODULE_DESC_TUTORIAL + DATE_DESC_PROJECT;

        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withPhone(VALID_MODULE_TUTORIAL)
                .withEmail(VALID_DATE_PROJECT).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + NAME_DESC_PROJECT;
        EditCommand.EditTaskDescriptor descriptor;
        descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_PROJECT).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + MODULE_DESC_PROJECT;
        descriptor = new EditTaskDescriptorBuilder().withPhone(VALID_MODULE_PROJECT).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + DATE_DESC_PROJECT;
        descriptor = new EditTaskDescriptorBuilder().withEmail(VALID_DATE_PROJECT).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + PRIORITY_DESC_PROJECT;
        descriptor = new EditTaskDescriptorBuilder().withAddress(VALID_PRIORITY_PROJECT).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_GRADED;
        descriptor = new EditTaskDescriptorBuilder().withTags(VALID_TAG_UNGRADED).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + MODULE_DESC_PROJECT + PRIORITY_DESC_PROJECT + DATE_DESC_PROJECT
                + TAG_DESC_GRADED + MODULE_DESC_PROJECT + PRIORITY_DESC_PROJECT + DATE_DESC_PROJECT + TAG_DESC_GRADED
                + MODULE_DESC_TUTORIAL + PRIORITY_DESC_TUTORIAL + DATE_DESC_TUTORIAL + TAG_DESC_UNGRADED;

        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withPhone(VALID_MODULE_TUTORIAL)
                .withEmail(VALID_DATE_TUTORIAL).withAddress(VALID_PRIORITY_TUTORIAL)
                .withTags(VALID_TAG_UNGRADED, VALID_TAG_GRADED)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + INVALID_MODULE_DESC + MODULE_DESC_TUTORIAL;
        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withPhone(VALID_MODULE_TUTORIAL).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DATE_DESC_TUTORIAL + INVALID_MODULE_DESC + PRIORITY_DESC_TUTORIAL
                + MODULE_DESC_TUTORIAL;
        descriptor = new EditTaskDescriptorBuilder().withPhone(VALID_MODULE_TUTORIAL).withEmail(VALID_DATE_TUTORIAL)
                .withAddress(VALID_PRIORITY_TUTORIAL).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
