package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.task.ModuleContainsKeywordsPredicate;

/**
 * Finds and lists all tasks in task manager whose module code contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindModuleCommand extends Command {

    public static final String COMMAND_WORD = "findmodule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose module code contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " CS2113T";

    private final ModuleContainsKeywordsPredicate predicate;

    public FindModuleCommand(ModuleContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredTaskList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindModuleCommand // instanceof handles nulls
                && predicate.equals(((FindModuleCommand) other).predicate)); // state check
    }
}
