package main.java.model;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private String category;
    private boolean isAvailable;

    public Book(String title, String author, String isbn, String category, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return String.format("%s by %s (ISBN: %s)", title, author, isbn);
    }
}