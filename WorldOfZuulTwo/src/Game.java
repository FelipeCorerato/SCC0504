// Game.java
public class Game {
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;

    public Game() {
        createRooms();
        parser = new Parser();
    }

    private void createRooms() {
        // Creating rooms and setting items
        Room outside, theater, pub, lab, office;

        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");

        // Setting items
        outside.setItem("a rock");
        theater.setItem("a projector");
        pub.setItem("a beer");
        lab.setItem("a computer");
        office.setItem("a desk");

        // Initialize room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside; // start game outside
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    private void printLocationInfo() {
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case LOOK:
                look();
                break;
            case GO:
                goRoom(command);
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
            case BACK:
                back();
                break;
            default:
                System.out.println("I don't know what you mean...");
                break;
        }
        return wantToQuit;
    }

    private void look() {
        printLocationInfo();
    }

    private void back() {
        if (previousRoom != null) {
            currentRoom = previousRoom;
            printLocationInfo();
        } else {
            System.out.println("You can't go back!");
        }
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            previousRoom = currentRoom;
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }
}
