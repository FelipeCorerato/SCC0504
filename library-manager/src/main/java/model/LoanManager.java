package main.java.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanManager {
    private List<Loan> loans;

    public LoanManager() {
        loans = new ArrayList<>();
    }

    public void performLoan(Book book, Patron patron) {
        if (book.isAvailable()) {
            Loan loan = new Loan(book, patron, LocalDate.now(), LocalDate.now().plusWeeks(2));
            loans.add(loan);
            book.setAvailable(false);
        }
    }

    public void returnLoan(Loan loan) {
        loan.setReturned(true);
        loan.getBook().setAvailable(true);
    }

    public List<Loan> getOverdueLoans() {
        LocalDate today = LocalDate.now();
        return loans.stream()
                .filter(loan -> !loan.isReturned() && loan.getDueDate().isBefore(today))
                .collect(Collectors.toList());
    }

    public List<Loan> getLoans() {
        return loans;
    }

    // Método para carregar empréstimos de um arquivo
    public void loadLoans(String fileName) {
        // Implementação da carga de dados
    }

    // Método para salvar empréstimos em um arquivo
    public void saveLoans(String fileName) {
        // Implementação da gravação de dados
    }
}