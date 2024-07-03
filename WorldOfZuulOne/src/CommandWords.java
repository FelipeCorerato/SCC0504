/**
 * The CommandWords class holds all valid command words.
 */
public class CommandWords {
    private static final String[] validCommands = {
            "go", "quit", "help", "up", "down"
    };

    public CommandWords() {
        // nothing to do at the moment...
    }

    /**
     * Check if a given string is a valid command.
     *
     * @param aString the string to check
     * @return true if it is a valid command, false otherwise
     */
    public boolean isCommand(String aString) {
        for (String command : validCommands) {
            if (command.equals(aString)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll() {
        for (String command : validCommands) {
            System.out.print(command + " ");
        }
        System.out.println();
    }
}
