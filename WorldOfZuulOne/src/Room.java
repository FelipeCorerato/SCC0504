import java.util.HashMap;
import java.util.Set;

/**
 * The Room class represents a room in the game. A room can have multiple exits
 * that lead to other rooms.
 */
public class Room {
    private String description;
    private HashMap<String, Room> exits; // Store exits of this room.

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     *
     * @param description The room's description.
     */
    public Room(String description) {
        this.description = description;
        exits = new HashMap<String, Room>();
    }

    /**
     * Define an exit from this room.
     *
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * Return the short description of the room (the one that was
     * defined in the constructor).
     *
     * @return The short description of the room.
     */
    public String getShortDescription() {
        return description;
    }

    /**
     * Return a long description of this room, in the form:
     * You are in the kitchen.
     * Exits: north west
     *
     * @return A description of the room, including exits.
     */
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     *
     * @return Details of the room's exits.
     */
    private String getExitString() {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     *
     * @param direction The direction in which to look.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }
}
