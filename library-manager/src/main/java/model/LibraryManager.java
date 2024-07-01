package main.java.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.util.LocalDateAdapter;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages the operations of a library, including managing books, patrons, and loans.
 * Provides methods to add, edit, delete, and search for books and patrons,
 * as well as performing and returning loans.
 *
 * <p>
 * This class also handles the persistence of library data using JSON serialization.
 * </p>
 *
 * @see Book
 * @see Patron
 * @see Loan
 * @see LibraryData
 */
public class LibraryManager {
    private List<Book> books;
    private List<Patron> patrons;
    private List<Loan> loans;
    private Gson gson;

    /**
     * Constructs a LibraryManager object and initializes the lists of books, patrons, and loans.
     * Also sets up the Gson instance for JSON serialization and deserialization.
     */
    public LibraryManager() {
        books = new ArrayList<>();
        patrons = new ArrayList<>();
        loans = new ArrayList<>();
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }

    /**
     * Adds a book to the library and saves the data.
     *
     * @param book the book to be added
     */
    public void addBook(Book book) {
        books.add(book);
        saveData();
    }

    /**
     * Edits the details of an existing book and saves the data.
     *
     * @param oldBook the existing book to be edited
     * @param newBook the new book details
     */
    public void editBook(Book oldBook, Book newBook) {
        int index = books.indexOf(oldBook);
        if (index != -1) {
            books.set(index, newBook);
            saveData();
        }
    }

    /**
     * Deletes a book from the library and saves the data.
     *
     * @param book the book to be deleted
     */
    public void deleteBook(Book book) {
        books.remove(book);
        saveData();
    }

    /**
     * Searches for books in the library based on a keyword.
     * The search is performed on the title, author, ISBN, and category of the books.
     *
     * @param keyword the keyword to search for
     * @return a list of books that match the keyword
     */
    public List<Book> searchBooks(String keyword) {
        return books.stream()
                .filter(book -> book.getTitle().contains(keyword) || book.getAuthor().contains(keyword) || book.getIsbn().contains(keyword) || book.getCategory().contains(keyword))
                .collect(Collectors.toList());
    }

    /**
     * Adds a patron to the library and saves the data.
     *
     * @param patron the patron to be added
     */
    public void addPatron(Patron patron) {
        patrons.add(patron);
        saveData();
    }

    /**
     * Edits the details of an existing patron and saves the data.
     *
     * @param oldPatron the existing patron to be edited
     * @param newPatron the new patron details
     */
    public void editPatron(Patron oldPatron, Patron newPatron) {
        int index = patrons.indexOf(oldPatron);
        if (index != -1) {
            patrons.set(index, newPatron);
            saveData();
        }
    }

    /**
     * Deletes a patron from the library and saves the data.
     *
     * @param patron the patron to be deleted
     */
    public void deletePatron(Patron patron) {
        patrons.remove(patron);
        saveData();
    }

    /**
     * Searches for patrons in the library based on a keyword.
     * The search is performed on the name and contact information of the patrons.
     *
     * @param keyword the keyword to search for
     * @return a list of patrons that match the keyword
     */
    public List<Patron> searchPatrons(String keyword) {
        return patrons.stream()
                .filter(patron -> patron.getName().contains(keyword) || patron.getContactInfo().contains(keyword))
                .collect(Collectors.toList());
    }

    /**
     * Performs a loan by associating a book with a patron and setting the loan and due dates.
     * The availability status of the book is updated and the data is saved.
     *
     * @param book the book to be loaned
     * @param patron the patron borrowing the book
     */
    public void performLoan(Book book, Patron patron) {
        if (book.isAvailable()) {
            Loan loan = new Loan(book, patron, LocalDate.now(), LocalDate.now().plusWeeks(2));
            loans.add(loan);
            book.setAvailable(false);
            saveData();
        }
    }

    /**
     * Returns a loan by updating the loan status and the availability status of the book.
     * The data is then saved.
     *
     * @param loan the loan to be returned
     */
    public void returnLoan(Loan loan) {
        loan.setReturned(true);
        loan.getBook().setAvailable(true);
        saveData();
    }

    /**
     * Retrieves a list of loans that are overdue.
     *
     * @return a list of overdue loans
     */
    public List<Loan> getOverdueLoans() {
        LocalDate today = LocalDate.now();
        return loans.stream()
                .filter(loan -> !loan.isReturned() && loan.getDueDate().isBefore(today))
                .collect(Collectors.toList());
    }

    /**
     * Saves the library data (books, patrons, loans) to a JSON file.
     */
    public void saveData() {
        try (Writer writer = new FileWriter("src/main/resources/library_data.json")) {
            LibraryData data = new LibraryData(books, patrons, loans);
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the library data (books, patrons, loans) from a JSON file.
     */
    public void loadData() {
        try (Reader reader = new FileReader("src/main/resources/library_data.json")) {
            LibraryData data = gson.fromJson(reader, LibraryData.class);
            if (data != null) {
                books = data.getBooks();
                patrons = data.getPatrons();
                loans = data.getLoans();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the list of all books in the library.
     *
     * @return the list of books
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Returns the list of all patrons in the library.
     *
     * @return the list of patrons
     */
    public List<Patron> getPatrons() {
        return patrons;
    }

    /**
     * Returns the list of all loans in the library.
     *
     * @return the list of loans
     */
    public List<Loan> getLoans() {
        return loans;
    }
}