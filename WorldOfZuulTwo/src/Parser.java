
// Parser.java
import java.util.Scanner;

public class Parser {
    private CommandWords commandWords;
    private Scanner reader;

    public Parser() {
        commandWords = new CommandWords();
        reader = new Scanner(System.in);
    }

    public Command getCommand() {
        String inputLine;
        String word1 = null;
        String word2 = null;

        System.out.print("> ");

        inputLine = reader.nextLine();

        Scanner tokenizer = new Scanner(inputLine);
        if (tokenizer.hasNext()) {
            word1 = tokenizer.next();
            if (tokenizer.hasNext()) {
                word2 = tokenizer.next();
            }
        }

        return new Command(commandWords.getCommandWord(word1), word2);
    }

    public void showCommands() {
        commandWords.showAll();
    }
}
