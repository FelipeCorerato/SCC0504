
// Room.java
import java.util.HashMap;

public class Room {
    private String description;
    private HashMap<String, Room> exits;
    private String item;

    public Room(String description) {
        this.description = description;
        exits = new HashMap<String, Room>();
        item = "";
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString() + "\n" + getItemString();
    }

    private String getExitString() {
        String returnString = "Exits:";
        for (String exit : exits.keySet()) {
            returnString += " " + exit;
        }
        return returnString;
    }

    private String getItemString() {
        return item.isEmpty() ? "No items here." : "You see here: " + item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }
}
