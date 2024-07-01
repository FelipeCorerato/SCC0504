package main.java.model;

import java.util.List;

public class LibraryData {
    private List<Book> books;
    private List<Patron> patrons;
    private List<Loan> loans;

    public LibraryData(List<Book> books, List<Patron> patrons, List<Loan> loans) {
        this.books = books;
        this.patrons = patrons;
        this.loans = loans;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Patron> getPatrons() {
        return patrons;
    }

    public List<Loan> getLoans() {
        return loans;
    }
}