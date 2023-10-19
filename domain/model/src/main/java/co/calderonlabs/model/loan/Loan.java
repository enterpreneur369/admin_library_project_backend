package co.calderonlabs.model.loan;
import co.calderonlabs.model.book.Book;
import co.calderonlabs.model.user.User;
import lombok.*;
//import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    private Integer id;
    private Book book;
    private User user;
    private LocalDate loanDate;
    private LocalDate returnDate;
}
