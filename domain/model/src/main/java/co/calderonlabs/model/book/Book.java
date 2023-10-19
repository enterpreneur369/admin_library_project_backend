package co.calderonlabs.model.book;
import lombok.*;
//import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer id;
    private String title;
    private String author;
    private Integer publicationYear;
    private String genre;
    private Integer availableQuantity;
    private String isbn;
}
