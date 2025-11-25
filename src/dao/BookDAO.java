package dao;

import model.Book;
import java.util.List;
import java.util.Optional;

public interface BookDAO {
    boolean add(Book book) throws Exception;
    Optional<Book> findById(int id) throws Exception;
    List<Book> findAll() throws Exception;
    boolean update(Book book) throws Exception;
    boolean delete(int id) throws Exception;
}
