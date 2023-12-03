package bookstore;

import bookstore.model.Book;
import bookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootBookStoreApplication {
    private final BookService bookService;

    @Autowired
    public SpringBootBookStoreApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book = new Book();
                book.setTitle("Know no fear");
                book.setAuthor("Dan Abnett");
                book.setIsbn("99781849701358");
                book.setPrice(BigDecimal.valueOf(200));
                bookService.save(book);
            }
        };
    }
}
