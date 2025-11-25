package service;

import dao.BookDAO;
import dao.impl.BookDAOImpl;
import model.Book;

import java.util.List;
import java.util.Optional;

public class BookService {
    private final BookDAO bookDAO = new BookDAOImpl();

    public boolean addBook(Book book) throws Exception {
        // business validation example: id > 0 & title non-empty
        if (book.getId() <= 0) throw new IllegalArgumentException("ID must be > 0");
        if (book.getTitle() == null || book.getTitle().trim().isEmpty())
            throw new IllegalArgumentException("Title required");
        // ensure not duplicate
        Optional<Book> exists = bookDAO.findById(book.getId());
        if (exists.isPresent()) throw new IllegalArgumentException("Book with ID exists");
        return bookDAO.add(book);
    }

    public Optional<Book> findBookById(int id) throws Exception {
        return bookDAO.findById(id);
    }

    public List<Book> getAllBooks() throws Exception {
        return bookDAO.findAll();
    }

    public boolean issueBook(int id) throws Exception {
        Optional<Book> ob = bookDAO.findById(id);
        if (ob.isEmpty()) return false;
        Book b = ob.get();
        if (b.isIssued()) return false;
        b.setIssued(true);
        return bookDAO.update(b);
    }

    public boolean returnBook(int id) throws Exception {
        Optional<Book> ob = bookDAO.findById(id);
        if (ob.isEmpty()) return false;
        Book b = ob.get();
        if (!b.isIssued()) return false;
        b.setIssued(false);
        return bookDAO.update(b);
    }

    public boolean deleteBook(int id) throws Exception {
        return bookDAO.delete(id);
    }
}
