package co.calderonlabs.usecase.loan;

import co.calderonlabs.model.book.Book;
import co.calderonlabs.model.loan.Loan;
import co.calderonlabs.model.user.User;
import co.calderonlabs.usecase.book.BookUseCase;
import co.calderonlabs.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class LoanUseCase {

    private final List<Loan> loanList = new ArrayList<>() {{
        add(
                Loan.builder()
                        .id(1)
                        .book(
                                BookUseCase.bookList.get(0)
                        )
                        .user(
                                UserUseCase.userList.get(1)
                        )
                        .loanDate(LocalDate.parse("2023-10-01"))
                        .returnDate(null)
                        .build()
        );
        add(
                Loan.builder()
                        .id(2)
                        .book(
                                BookUseCase.bookList.get(1)
                        )
                        .user(
                                UserUseCase.userList.get(2)
                        )
                        .loanDate(LocalDate.parse("2023-09-25"))
                        .returnDate(LocalDate.parse("2023-10-05"))
                        .build()
        );
    }};

    public List<Loan> getLoans() {
        return this.loanList;
    }
    public Loan getLoanById(Integer id) {
        Optional<Loan> loanOptional = loanList.stream()
                .filter(loan -> loan.getId().equals(id))
                .findFirst();

        if (loanOptional.isPresent()) {
            return loanOptional.get();
        } else {
            throw new IllegalArgumentException("Préstamo no encontrado con ID: " + id);
        }
    }

    public Loan createLoan(Loan newLoan) {
        if (newLoan.getId() == null) {
            Integer lastId = loanList.stream()
                    .map(Loan::getId)
                    .max(Integer::compareTo)
                    .orElse(0);

            newLoan.setId(lastId + 1);
        }

        Book loanBook = newLoan.getBook();

        for (Book book : BookUseCase.bookList) {
            if (book.getId().equals(loanBook.getId())) {
                book.setAvailableQuantity(book.getAvailableQuantity() - 1);
                break;
            }
        }

        this.loanList.add(newLoan);
        return newLoan;
    }

    public Loan updateLoan(Integer id, Loan loanToUpdate) {
        Optional<Loan> existingLoanOptional = loanList.stream()
                .filter(loan -> loan.getId().equals(id))
                .findFirst();

        if (existingLoanOptional.isPresent()) {
            Loan existingLoan = existingLoanOptional.get();

            Book bookToUpdate = existingLoan.getBook();

            for (Book book : BookUseCase.bookList) {
                if (book.getId().equals(bookToUpdate.getId())) {
                    book.setAvailableQuantity(book.getAvailableQuantity() + 1);
                    bookToUpdate = book;
                    break;
                }
            }

            existingLoan.setBook(bookToUpdate);
            existingLoan.setUser(loanToUpdate.getUser());
            existingLoan.setLoanDate(loanToUpdate.getLoanDate());
            existingLoan.setReturnDate(LocalDate.now());

            return existingLoan;
        } else {
            throw new IllegalArgumentException("Préstamo no encontrado con ID: " + id);
        }
    }

    public void deleteLoan(Integer loanId) {
        Iterator<Loan> iterator = loanList.iterator();
        while (iterator.hasNext()) {
            Loan loan = iterator.next();
            if (loan.getId().equals(loanId)) {
                iterator.remove();
                return;
            }
        }
        throw new IllegalArgumentException("Préstamo no encontrado con ID: " + loanId);
    }
}