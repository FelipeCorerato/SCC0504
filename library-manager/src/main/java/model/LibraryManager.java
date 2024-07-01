package main.java.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.util.LocalDateAdapter;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryManager {
    private List<Book> books;
    private List<Patron> patrons;
    private List<Loan> loans;
    private Gson gson;

    public LibraryManager() {
        books = new ArrayList<>();
        patrons = new ArrayList<>();
        loans = new ArrayList<>();
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }

    public void addBook(Book book) {
        books.add(book);
        saveData();
    }

    public void editBook(Book oldBook, Book newBook) {
        int index = books.indexOf(oldBook);
        if (index != -1) {
            books.set(index, newBook);
            saveData();
        }
    }

    public void deleteBook(Book book) {
        books.remove(book);
        saveData();
    }

    public List<Book> searchBooks(String keyword) {
        return books.stream()
                .filter(book -> book.getTitle().contains(keyword) || book.getAuthor().contains(keyword) || book.getIsbn().contains(keyword) || book.getCategory().contains(keyword))
                .collect(Collectors.toList());
    }

    public void addPatron(Patron patron) {
        patrons.add(patron);
        saveData();
    }

    public void editPatron(Patron oldPatron, Patron newPatron) {
        int index = patrons.indexOf(oldPatron);
        if (index != -1) {
            patrons.set(index, newPatron);
            saveData();
        }
    }

    public void deletePatron(Patron patron) {
        patrons.remove(patron);
        saveData();
    }

    public List<Patron> searchPatrons(String keyword) {
        return patrons.stream()
                .filter(patron -> patron.getName().contains(keyword) || patron.getContactInfo().contains(keyword))
                .collect(Collectors.toList());
    }

    public void performLoan(Book book, Patron patron) {
        if (book.isAvailable()) {
            Loan loan = new Loan(book, patron, LocalDate.now(), LocalDate.now().plusWeeks(2));
            loans.add(loan);
            book.setAvailable(false);
            saveData();
        }
    }

    public void returnLoan(Loan loan) {
        loan.setReturned(true);
        loan.getBook().setAvailable(true);
        saveData();
    }

    public List<Loan> getOverdueLoans() {
        LocalDate today = LocalDate.now();
        return loans.stream()
                .filter(loan -> !loan.isReturned() && loan.getDueDate().isBefore(today))
                .collect(Collectors.toList());
    }

    public void saveData() {
        try (Writer writer = new FileWriter("src/main/resources/library_data.json")) {
            LibraryData data = new LibraryData(books, patrons, loans);
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    // Método para obter todos os livros
    public List<Book> getBooks() {
        return books;
    }

    // Método para obter todos os patronos
    public List<Patron> getPatrons() {
        return patrons;
    }

    // Método para obter todos os empréstimos
    public List<Loan> getLoans() {
        return loans;
    }
}