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
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_GRADED;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_UNGRADED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GRADED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNGRADED;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalTasks.PROJECT;
import static seedu.address.testutil.TypicalTasks.TUTORIAL;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Date;
import seedu.address.model.person.Module;
import seedu.address.model.person.Name;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Task;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TaskBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder(TUTORIAL).withTags(VALID_TAG_UNGRADED).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_TUTORIAL + MODULE_DESC_TUTORIAL + DATE_DESC_TUTORIAL
                + PRIORITY_DESC_TUTORIAL + TAG_DESC_GRADED, new AddCommand(expectedTask));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_PROJECT + NAME_DESC_TUTORIAL + MODULE_DESC_TUTORIAL + DATE_DESC_TUTORIAL
                + PRIORITY_DESC_TUTORIAL + TAG_DESC_GRADED, new AddCommand(expectedTask));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_TUTORIAL + MODULE_DESC_PROJECT + MODULE_DESC_TUTORIAL + DATE_DESC_TUTORIAL
                + PRIORITY_DESC_TUTORIAL + TAG_DESC_GRADED, new AddCommand(expectedTask));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_TUTORIAL + MODULE_DESC_TUTORIAL + DATE_DESC_PROJECT + DATE_DESC_TUTORIAL
                + PRIORITY_DESC_TUTORIAL + TAG_DESC_GRADED, new AddCommand(expectedTask));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_TUTORIAL + MODULE_DESC_TUTORIAL + DATE_DESC_TUTORIAL + PRIORITY_DESC_PROJECT
                + PRIORITY_DESC_TUTORIAL + TAG_DESC_GRADED, new AddCommand(expectedTask));

        // multiple tags - all accepted
        Task expectedTaskMultipleTags = new TaskBuilder(TUTORIAL).withTags(VALID_TAG_UNGRADED, VALID_TAG_GRADED)
                .build();
        assertParseSuccess(parser, NAME_DESC_TUTORIAL + MODULE_DESC_TUTORIAL + DATE_DESC_TUTORIAL + PRIORITY_DESC_TUTORIAL
                + TAG_DESC_UNGRADED + TAG_DESC_GRADED, new AddCommand(expectedTaskMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Task expectedTask = new TaskBuilder(PROJECT).withTags().build();
        assertParseSuccess(parser, NAME_DESC_PROJECT + MODULE_DESC_PROJECT + DATE_DESC_PROJECT + PRIORITY_DESC_PROJECT,
                new AddCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_TUTORIAL + MODULE_DESC_TUTORIAL + DATE_DESC_TUTORIAL + PRIORITY_DESC_TUTORIAL,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_TUTORIAL + VALID_MODULE_TUTORIAL + DATE_DESC_TUTORIAL + PRIORITY_DESC_TUTORIAL,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_TUTORIAL + MODULE_DESC_TUTORIAL + VALID_DATE_TUTORIAL + PRIORITY_DESC_TUTORIAL,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_TUTORIAL + MODULE_DESC_TUTORIAL + DATE_DESC_TUTORIAL + VALID_PRIORITY_TUTORIAL,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_TUTORIAL + VALID_MODULE_TUTORIAL + VALID_DATE_TUTORIAL + VALID_PRIORITY_TUTORIAL,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + MODULE_DESC_TUTORIAL + DATE_DESC_TUTORIAL + PRIORITY_DESC_TUTORIAL
                + TAG_DESC_UNGRADED + TAG_DESC_GRADED, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_TUTORIAL + INVALID_MODULE_DESC + DATE_DESC_TUTORIAL + PRIORITY_DESC_TUTORIAL
                + TAG_DESC_UNGRADED + TAG_DESC_GRADED, Module.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_TUTORIAL + MODULE_DESC_TUTORIAL + INVALID_DATE_DESC + PRIORITY_DESC_TUTORIAL
                + TAG_DESC_UNGRADED + TAG_DESC_GRADED, Date.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_TUTORIAL + MODULE_DESC_TUTORIAL + DATE_DESC_TUTORIAL + INVALID_PRIORITY_DESC
                + TAG_DESC_UNGRADED + TAG_DESC_GRADED, Priority.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_TUTORIAL + MODULE_DESC_TUTORIAL + DATE_DESC_TUTORIAL + PRIORITY_DESC_TUTORIAL
                + INVALID_TAG_DESC + VALID_TAG_UNGRADED, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + MODULE_DESC_TUTORIAL + DATE_DESC_TUTORIAL + INVALID_PRIORITY_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_TUTORIAL + MODULE_DESC_TUTORIAL + DATE_DESC_TUTORIAL
                        + PRIORITY_DESC_TUTORIAL + TAG_DESC_UNGRADED + TAG_DESC_GRADED,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
