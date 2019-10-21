package seedu.address.logic.commands.dashboard;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DASHBOARD;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.common.Name;
import seedu.address.model.dashboard.components.Dashboard;
import seedu.address.model.dashboard.components.DashboardName;
import seedu.address.model.dashboard.components.TaskDate;
import seedu.address.model.recipe.components.Calories;
import seedu.address.model.recipe.components.Carbs;
import seedu.address.model.recipe.components.Fats;
import seedu.address.model.recipe.components.Ingredient;
import seedu.address.model.recipe.components.Protein;
import seedu.address.model.recipe.components.Recipe;

/**
 * Edits the details of an existing recipe in Duke Cooks.
 */
public class EditTaskCommand extends EditCommand {

    public static final String VARIANT_WORD = "task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the displayed task list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TASKNAME + "TASKNAME] "
            + "[" + PREFIX_TASKDATE + "TASKDATE] ";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the Duke Cooks.";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param index of the recipe in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditTaskCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Dashboard> lastShownList = model.getFilteredDashboardList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Dashboard taskToEdit = lastShownList.get(index.getZeroBased());
        Dashboard editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        if (!taskToEdit.isSameDashboard(editedTask) && model.hasDashboard(editedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.setDashboard(taskToEdit, editedTask);
        model.updateFilteredDashboardList(PREDICATE_SHOW_ALL_DASHBOARD);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    /**
     * Creates and returns a {@code Dashboard} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Dashboard createEditedTask(Dashboard taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        DashboardName updatedName = editTaskDescriptor.getDashboardName().orElse(taskToEdit.getDashboardName());
        TaskDate updatedTaskDate = editTaskDescriptor.getTaskDate().orElse(taskToEdit.getTaskDate());

        return new Dashboard(updatedName, updatedTaskDate);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTaskCommand)) {
            return false;
        }

        // state check
        EditTaskCommand e = (EditTaskCommand) other;
        return index.equals(e.index)
                && editTaskDescriptor.equals(e.editTaskDescriptor);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private DashboardName name;
        private TaskDate date;

        public EditTaskDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code ingredients} is used internally.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setDashboardName(toCopy.name);
            setTaskDate(toCopy.date);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, date);
        }

        public void setDashboardName(DashboardName name) {
            this.name = name;
        }

        public void setTaskDate(TaskDate date) {
            this.date = date;
        }

        public Optional<DashboardName> getDashboardName() {
            return Optional.ofNullable(name);
        }

        public Optional<TaskDate> getTaskDate() {
            return Optional.ofNullable(date);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskDescriptor e = (EditTaskDescriptor) other;

            return getDashboardName().equals(e.getDashboardName())
                    && getTaskDate().equals(e.getTaskDate());
        }
    }
}
