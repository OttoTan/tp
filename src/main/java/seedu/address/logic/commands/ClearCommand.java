package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.ui.ConfirmationWindow;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_CONFIRMATION = "Are you sure you want to clear ALL contacts from MediContact?\n"
            + "This action is IRREVERSIBLE.";
    public static final String MESSAGE_CLEAR_CANCELLED = "Clear action cancelled.";
    private Boolean isConfirmed;

    public ClearCommand() {
    }

    /**
     * This constructor should only be used for testing purposes to skip confirmation window.
     * @param isConfirmed skips confirmation window and provides the result for confirm clearance.
     */
    public ClearCommand(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // Show confirmation dialog
        if (isConfirmed != null) {
            if (isConfirmed) {
                model.setAddressBook(new AddressBook());
                return new CommandResult(String.format(MESSAGE_SUCCESS));
            } else {
                return new CommandResult(String.format(MESSAGE_CLEAR_CANCELLED));
            }
        }
        if (confirmClear()) {
            model.setAddressBook(new AddressBook());
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else {
            return new CommandResult(String.format(MESSAGE_CLEAR_CANCELLED));
        }
    }

    /**
     * Shows a confirmation dialog to the user and returns whether the clearance was confirmed.
     *
     * @return true if the clearance is confirmed; false otherwise.
     */
    private boolean confirmClear() {
        ConfirmationWindow confirmationWindow = ConfirmationWindow.getInstance();
        return confirmationWindow.showAlertDialogAndWait(
                "Confirm Clear",
                String.format(MESSAGE_CONFIRMATION)
        );
    }
}
