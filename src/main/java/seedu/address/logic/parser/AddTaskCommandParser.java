//package seedu.address.logic.parser;
//
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//
//import java.util.Set;
//import java.util.stream.Stream;
//
//import seedu.address.logic.commands.AddTaskCommand;
//import seedu.address.logic.parser.exceptions.ParseException;
//import seedu.address.model.tag.Tag;
//import seedu.address.model.task.Priority;
//import seedu.address.model.task.Task;
//import seedu.address.model.task.TaskDate;
//import seedu.address.model.task.TaskModule;
//import seedu.address.model.task.TaskName;
//
///**
// * Parses input arguments and creates a new AddCommand object
// */
//public class AddTaskCommandParser implements Parser<AddTaskCommand> {
//
//    /**
//     * Parses the given {@code String} of arguments in the context of the AddCommand
//     * and returns an AddCommand object for execution.
//     * @throws ParseException if the user input does not conform the expected format
//     */
//    public AddTaskCommand parse(String args) throws ParseException {
//        ArgumentMultimap argMultimap =
//                ArgumentTokenizer.tokenize(args, PREFIX_TASK, PREFIX_MODULE, PREFIX_DATE, PREFIX_PRIORITY, PREFIX_TAG);
//
//        if (!arePrefixesPresent(argMultimap, PREFIX_TASK, PREFIX_MODULE, PREFIX_DATE, PREFIX_PRIORITY)
//                || !argMultimap.getPreamble().isEmpty()) {
//            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
//        }
//
//        TaskName name = ParserUtil.parseTaskName(argMultimap.getValue(PREFIX_TASK).get());
//        TaskModule module = ParserUtil.parseTaskModule(argMultimap.getValue(PREFIX_MODULE).get());
//        TaskDate date = ParserUtil.parseTaskDate(argMultimap.getValue(PREFIX_DATE).get());
//        Priority priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
//        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
//
//        Task task = new Task(name, module, date, priority, tagList);
//
//        return new AddTaskCommand(task);
//    }
//
//    /**
//     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
//     * {@code ArgumentMultimap}.
//     */
//    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
//        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
//    }
//
//}
