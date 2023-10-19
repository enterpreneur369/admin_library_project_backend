package co.calderonlabs.usecase.book;

import co.calderonlabs.model.book.Book;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Iterator;


@RequiredArgsConstructor
public class BookUseCase {
    public static final List<Book> bookList = new ArrayList<Book>() {{
        add(
                Book.builder().id(1).title("Cien anios de soledad").author("Gabriel Garcia Marquez").publicationYear(1967)
                        .genre("Realismo magico")
                        .availableQuantity(5)
                        .isbn("1234567890")
                        .build()
        );
        add(
                Book.builder().id(2).title("El senior de los anillos").author("J.R.R. Tolkien").publicationYear(1954)
                        .genre("Fantasia")
                        .availableQuantity(7)
                        .isbn("0987654321")
                        .build()
        );
        add(
                Book.builder().id(3).title("1984").author("George Orwell").publicationYear(1949)
                        .genre("Distopía")
                        .availableQuantity(3)
                        .isbn("1122334455")
                        .build()
        );
    }};

    public List<Book> getBooks() {
        return this.bookList;
    }

    public Book getBookById(Integer id) {
        Optional<Book> bookOptional = bookList.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();

        if (bookOptional.isPresent()) {
            return bookOptional.get();
        } else {
            throw new IllegalArgumentException("Libro no encontrado con ID: " + id);
        }
    }


    public Book createBook(Book newBook) {
        if (newBook.getId() == null) {
            Integer lastId = bookList.stream()
                    .map(Book::getId)
                    .max(Integer::compareTo)
                    .orElse(0);

            newBook.setId(lastId + 1);
        }

        this.bookList.add(newBook);
        return newBook;
    }

    public Book updateBook(Integer id, Book bookToUpdate) {
        Optional<Book> existingBookOptional = bookList.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();

        if (existingBookOptional.isPresent()) {
            Book existingBook = existingBookOptional.get();

            existingBook.setTitle(bookToUpdate.getTitle());
            existingBook.setAuthor(bookToUpdate.getAuthor());
            existingBook.setPublicationYear(bookToUpdate.getPublicationYear());
            existingBook.setGenre(bookToUpdate.getGenre());
            existingBook.setAvailableQuantity(bookToUpdate.getAvailableQuantity());
            existingBook.setIsbn(bookToUpdate.getIsbn());

            return existingBook;
        } else {
            throw new IllegalArgumentException("Libro no encontrado con ID: " + id);
        }
    }

    public void deleteBook(Integer bookId) {
        Iterator<Book> iterator = bookList.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getId().equals(bookId)) {
                iterator.remove();
                return;
            }
        }
        throw new IllegalArgumentException("Libro no encontrado con ID: " + bookId);
    }
}
