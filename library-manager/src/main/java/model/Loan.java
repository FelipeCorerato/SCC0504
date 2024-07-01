package main.java.model;

import java.time.LocalDate;

public class Loan {
    private Book book;
    private Patron patron;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private boolean isReturned;

    public Loan(Book book, Patron patron, LocalDate loanDate, LocalDate dueDate) {
        this.book = book;
        this.patron = patron;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.isReturned = false;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean isReturned) {
        this.isReturned = isReturned;
    }

    @Override
    public String toString() {
        return "Loan [Book=" + book + ", Patron=" + patron + ", Loan Date=" + loanDate + ", Due Date=" + dueDate + ", Returned=" + isReturned + "]";
    }
}