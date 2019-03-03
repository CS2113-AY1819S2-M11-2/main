//package seedu.address.logic.parser;
//
//import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Optional;
//import java.util.Set;
//
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.EditCommand;
//import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
//import seedu.address.logic.commands.EditTaskCommand;
//import seedu.address.logic.parser.exceptions.ParseException;
//import seedu.address.model.tag.Tag;
//
///**
// * Parses input arguments and creates a new EditCommand object
// */
//public class EditTaskCommandParser implements Parser<EditTaskCommand> {
//
//    /**
//     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
//     * and returns an EditTaskCommand object for execution.
//     * @throws ParseException if the user input does not conform the expected format
//     */
//    public EditTaskCommand parse(String args) throws ParseException {
//        requireNonNull(args);
//        ArgumentMultimap argMultimap =
//                ArgumentTokenizer.tokenize(args, PREFIX_TASK, PREFIX_MODULE, PREFIX_DATE, PREFIX_PRIORITY, PREFIX_TAG);
//
//        Index index;
//
//        try {
//            index = ParserUtil.parseIndex(argMultimap.getPreamble());
//        } catch (ParseException pe) {
//            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE), pe);
//        }
//
//        EditTaskCommand.EditTaskDescriptor editTaskDescriptor = new EditTaskCommand.EditTaskDescriptor();
//        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
//            editTaskDescriptor.setTaskName(ParserUtil.parseTaskName(argMultimap.getValue(PREFIX_TASK).get()));
//        }
//        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
//            editTaskDescriptor.setTaskModule(ParserUtil.parseTaskModule(argMultimap.getValue(PREFIX_MODULE).get()));
//        }
//        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
//            editTaskDescriptor.setTaskDate(ParserUtil.parseTaskDate(argMultimap.getValue(PREFIX_DATE).get()));
//        }
//        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
//            editTaskDescriptor.setPriority(ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get()));
//        }
//        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editTaskDescriptor::setTags);
//
//        if (!editTaskDescriptor.isAnyFieldEdited()) {
//            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED);
//        }
//
//        return new EditTaskCommand(index, editTaskDescriptor);
//    }
//
//    /**
//     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
//     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
//     * {@code Set<Tag>} containing zero tags.
//     */
//    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
//        assert tags != null;
//
//        if (tags.isEmpty()) {
//            return Optional.empty();
//        }
//        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
//        return Optional.of(ParserUtil.parseTags(tagSet));
//    }
//
//}
