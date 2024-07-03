// CommandWord.java
public enum CommandWord {
    GO("go"), QUIT("quit"), HELP("help"), LOOK("look"), BACK("back"), UNKNOWN("?");

    private String commandString;

    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    public String toString() {
        return commandString;
    }
}
