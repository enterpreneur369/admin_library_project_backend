package co.calderonlabs.api;

import co.calderonlabs.model.book.Book;
import co.calderonlabs.model.loan.Loan;
import co.calderonlabs.model.user.User;
import co.calderonlabs.usecase.book.BookUseCase;
import co.calderonlabs.usecase.loan.LoanUseCase;
import co.calderonlabs.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private  final BookUseCase bookUseCase;
    private  final UserUseCase userUseCase;
    private  final LoanUseCase loanUseCase;
    public Mono<ServerResponse> getBooks(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue(bookUseCase.getBooks());
    }

    public Mono<ServerResponse> getBookById(ServerRequest serverRequest) {
        int bookId = Integer.parseInt(serverRequest.pathVariable("id"));
        return ServerResponse.ok().bodyValue(bookUseCase.getBookById(bookId));
    }

    public Mono<ServerResponse> createBook(ServerRequest serverRequest) {
        Mono<Book> newBookMono = serverRequest.bodyToMono(Book.class);
        return newBookMono.flatMap(newBook -> {
            newBook.setId(null);
            Book createdBook = bookUseCase.createBook(newBook);
            return ServerResponse.status(HttpStatus.CREATED).bodyValue(createdBook);
        });
    }
    public Mono<ServerResponse> updateBook(ServerRequest serverRequest) {
        Mono<Book> updatedBookMono = serverRequest.bodyToMono(Book.class);

        int bookId = Integer.parseInt(serverRequest.pathVariable("id"));

        return updatedBookMono.flatMap(updatedBook -> {
            Book updatedBookResult = bookUseCase.updateBook(bookId, updatedBook); // Asume que tienes un método updateBook en bookUseCase

            return ServerResponse.ok().bodyValue(updatedBookResult);
        });
    }

    public Mono<ServerResponse> deleteBook(ServerRequest serverRequest) {
        int bookId = Integer.parseInt(serverRequest.pathVariable("id"));

        bookUseCase.deleteBook(bookId);

        return ServerResponse.noContent().build();
    }

    public Mono<ServerResponse> getUsers(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue(userUseCase.getUsers());
    }

    public Mono<ServerResponse> getUserById(ServerRequest serverRequest) {
        int userId = Integer.parseInt(serverRequest.pathVariable("id"));
        return ServerResponse.ok().bodyValue(userUseCase.getUserById(userId));
    }

    public Mono<ServerResponse> getLoans(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue(loanUseCase.getLoans());
    }

    public Mono<ServerResponse> getLoanById(ServerRequest serverRequest) {
        int loanId = Integer.parseInt(serverRequest.pathVariable("id"));
        return ServerResponse.ok().bodyValue(loanUseCase.getLoanById(loanId));
    }
    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        Mono<User> newUserMono = serverRequest.bodyToMono(User.class);

        return newUserMono.flatMap(newUser -> {
            newUser.setId(null);
            User createdUser = userUseCase.createUser(newUser);
            return ServerResponse.status(HttpStatus.CREATED).bodyValue(createdUser);
        });
    }

    public Mono<ServerResponse> createLoan(ServerRequest serverRequest) {
        Mono<Loan> newLoanMono = serverRequest.bodyToMono(Loan.class);

        return newLoanMono.flatMap(newLoan -> {
            newLoan.setId(null);
            Loan createdLoan = loanUseCase.createLoan(newLoan);
            return ServerResponse.status(HttpStatus.CREATED).bodyValue(createdLoan);
        });
    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        Mono<User> updatedUserMono = serverRequest.bodyToMono(User.class);

        int userId = Integer.parseInt(serverRequest.pathVariable("id"));

        return updatedUserMono.flatMap(updatedUser -> {
            User updatedUserResult = userUseCase.updateUser(userId, updatedUser);
            return ServerResponse.ok().bodyValue(updatedUserResult);
        });
    }

    public Mono<ServerResponse> updateLoan(ServerRequest serverRequest) {
        Mono<Loan> updatedLoanMono = serverRequest.bodyToMono(Loan.class);

        int userId = Integer.parseInt(serverRequest.pathVariable("id"));

        return updatedLoanMono.flatMap(updatedLoan -> {
            Loan updatedLoanResult = loanUseCase.updateLoan(userId, updatedLoan);
            return ServerResponse.ok().bodyValue(updatedLoanResult);
        });
    }


    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest) {
        int userId = Integer.parseInt(serverRequest.pathVariable("id"));
        userUseCase.deleteUser(userId);
        return ServerResponse.noContent().build();
    }
}
