package main.java.model;

import java.util.List;

/**
 * Represents the data of a library including books, patrons, and loans.
 *
 * <p>
 * This class is used to encapsulate the library data in a single object, which
 * can be easily serialized and deserialized for persistence.
 * </p>
 *
 * @see Book
 * @see Patron
 * @see Loan
 */
public class LibraryData {
    private List<Book> books;
    private List<Patron> patrons;
    private List<Loan> loans;

    /**
     * Constructs a LibraryData object with the specified lists of books, patrons, and loans.
     *
     * @param books the list of books in the library
     * @param patrons the list of patrons in the library
     * @param loans the list of loans in the library
     */
    public LibraryData(List<Book> books, List<Patron> patrons, List<Loan> loans) {
        this.books = books;
        this.patrons = patrons;
        this.loans = loans;
    }

    /**
     * Returns the list of books in the library.
     *
     * @return the list of books
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Returns the list of patrons in the library.
     *
     * @return the list of patrons
     */
    public List<Patron> getPatrons() {
        return patrons;
    }

    /**
     * Returns the list of loans in the library.
     *
     * @return the list of loans
     */
    public List<Loan> getLoans() {
        return loans;
    }
}