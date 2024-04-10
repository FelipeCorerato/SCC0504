import java.util.Scanner;

public class GuessingGame {
    protected int rangeStart; // Properties setted by the user
    protected int rangeEnd;

    protected int lastGuess; // Properties calculated by the game
    protected int guessCounter;
    private int answer;

    public GuessingGame(int rangeStart, int rangeEnd) { // Contructs Game with the range
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public void setup() { // Setups the game calculating properties the setted range
        this.answer = (int) (Math.random() * (this.rangeEnd - this.rangeStart + 1)) + this.rangeStart;
        this.guessCounter = 0;
    }

    public String acceptGuess(int guess) { // Accepts user's guess and returns a status about the guess
        String status = "OK";
        if (guess > rangeEnd || guess < rangeStart) { // Guess out of the game range scenario
            status = "OUT OF BOUNDS";
        }

        if (status.compareTo("OK") == 0) { // The games only accepts guesses if the validations are OK
            this.lastGuess = guess;
        }

        return status;
    }

    public String feedback() { // returns a feedback comparing the last guess and the answer
        int guessDiff = lastGuess - answer;

        if (guessDiff == 0) {
            return "Correct answer!";
        }

        if (guessDiff > 0) { // Decision to return if the answer if higher or lower than the last guess
            return "The answer is lower!";
        } else {
            return "The answer is higher!";
        }
    }

    public int countGuess() { // Increments the number of tries
        return this.guessCounter++;
    }

    public void play(Scanner scanner) { // Game loop
        Boolean shouldContinue = true; // Variable to control the main loop
        while (shouldContinue) {
            if (this.guessCounter > 0) { // Print to confirm that the game is still going after the first guess
                System.out.println("Alright! Let's try again.");
            }

            int guess = Integer.parseInt(scanner.nextLine()); // Reads the guess

            String status = this.acceptGuess(guess); // Gets the status from the guess (validation about the range)
            this.countGuess();
            if (status.compareTo("OK") != 0) { // Ignores the flow if error status
                System.out.println("Your guess is out of the range!");
                continue;
            }

            String feedback = this.feedback(); // Gets and prints the feedback
            System.out.println(feedback);

            if (feedback.compareTo("Correct answer!") == 0) { // Prints the winning scenario and ends the loop
                System.out.println("Congrats for winning!");
                shouldContinue = false;
            } else {
                System.out.println("Do you want to keep playing? [YES | NO]"); // Asks if the game should stop
                String keepGoing = scanner.nextLine();
                shouldContinue = keepGoing.compareTo("NO") != 0;
            }
        }

        System.out.println("You have finished the game in " + this.guessCounter + " guesses."); // Prints the end of the game
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) { // Uses ARM to close scanner as finally clause
            // Reads game range
            System.out.println("Insert the start of the range: ");
            int rangeStart = Integer.parseInt(scanner.nextLine());
            System.out.println("Insert the end of the range: ");
            int rangeEnd = Integer.parseInt(scanner.nextLine());

            // Instantiates and sets the game up to play
            GuessingGame game = new GuessingGame(rangeStart, rangeEnd);
            game.setup();
            System.out.println("The Guessing Game setup is complete! Let's start!");
            System.out.println("Go ahead and try to guess the number between " + rangeStart + " and " + rangeEnd);

            // Play the actually
            game.play(scanner);
        } catch (NumberFormatException e) { // Catch exception if input is not number
            System.out.println("Invalid input! The input must be a integer!");
        }
    }
}
