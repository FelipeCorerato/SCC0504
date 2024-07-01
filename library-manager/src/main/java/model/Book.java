package main.java.model;

/**
 * Represents a book in the library system.
 *
 * <p>
 * This class stores details about a book, including its title, author, ISBN, category, and availability status.
 * </p>
 *
 * @see LibraryManager
 */
public class Book {
    private String title;
    private String author;
    private String isbn;
    private String category;
    private boolean isAvailable;

    /**
     * Constructs a Book object with the specified title, author, ISBN, category, and availability status.
     *
     * @param title the title of the book
     * @param author the author of the book
     * @param isbn the ISBN of the book
     * @param category the category of the book
     * @param isAvailable the availability status of the book
     */
    public Book(String title, String author, String isbn, String category, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    /**
     * Returns the title of the book.
     *
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title the new title of the book
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the author of the book.
     *
     * @return the author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     *
     * @param author the new author of the book
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns the ISBN of the book.
     *
     * @return the ISBN of the book
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the book.
     *
     * @param isbn the new ISBN of the book
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Returns the category of the book.
     *
     * @return the category of the book
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the book.
     *
     * @param category the new category of the book
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Returns the availability status of the book.
     *
     * @return true if the book is available, false otherwise
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Sets the availability status of the book.
     *
     * @param isAvailable the new availability status of the book
     */
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    /**
     * Returns a string representation of the book.
     *
     * @return a string representation of the book
     */
    @Override
    public String toString() {
        return String.format("%s by %s (ISBN: %s)", title, author, isbn);
    }
}