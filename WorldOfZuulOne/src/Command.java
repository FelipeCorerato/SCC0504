/**
 * The Command class represents a command issued by the user.
 */
public class Command {
    private String commandWord;
    private String secondWord;

    /**
     * Create a command object. First and second words must be supplied, but
     * either one (or both) can be null.
     *
     * @param firstWord  The first word of the command. Null if the command
     *                   was not recognized.
     * @param secondWord The second word of the command.
     */
    public Command(String firstWord, String secondWord) {
        commandWord = firstWord;
        secondWord = secondWord;
    }

    /**
     * Return the command word (the first word) of this command.
     *
     * @return The command word.
     */
    public String getCommandWord() {
        return commandWord;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     *         second word.
     */
    public String getSecondWord() {
        return secondWord;
    }

    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown() {
        return (commandWord == null);
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord() {
        return (secondWord != null);
    }
}
