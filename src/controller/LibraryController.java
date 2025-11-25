package controller;

import model.Book;
import service.BookService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class LibraryController {
    private final BookService service = new BookService();
    private final Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            printMenu();
            int opt = readInt("Choose an option: ");
            try {
                switch (opt) {
                    case 1 -> addBook();
                    case 2 -> issueBook();
                    case 3 -> returnBook();
                    case 4 -> deleteBook();
                    case 5 -> viewBooks();
                    case 6 -> { System.out.println("Exiting..."); return; }
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== LIBRARY MENU ===");
        System.out.println("1. Add Book");
        System.out.println("2. Issue Book");
        System.out.println("3. Return Book");
        System.out.println("4. Delete Book");
        System.out.println("5. View Books");
        System.out.println("6. Exit");
    }

    private void addBook() throws Exception {
        int id = readInt("Enter ID: ");
        String title = readLine("Enter Title: ");
        String author = readLine("Enter Author: ");
        Book b = new Book(id, title, author, false);
        if (service.addBook(b)) System.out.println("Book added.");
        else System.out.println("Failed to add book.");
    }

    private void issueBook() throws Exception {
        int id = readInt("Enter Book ID to issue: ");
        boolean ok = service.issueBook(id);
        System.out.println(ok ? "Book issued." : "Issue failed (not found or already issued).");
    }

    private void returnBook() throws Exception {
        int id = readInt("Enter Book ID to return: ");
        boolean ok = service.returnBook(id);
        System.out.println(ok ? "Book returned." : "Return failed (not found or not issued).");
    }

    private void deleteBook() throws Exception {
        int id = readInt("Enter Book ID to delete: ");
        boolean ok = service.deleteBook(id);
        System.out.println(ok ? "Book deleted." : "Delete failed (not found).");
    }

    private void viewBooks() throws Exception {
        List<Book> list = service.getAllBooks();
        if (list.isEmpty()) System.out.println("No books.");
        else list.forEach(System.out::println);
    }

    // helpers
    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            sc.next(); // discard bad token
            System.out.print("Please enter a number: ");
        }
        int v = sc.nextInt();
        sc.nextLine(); // consume newline
        return v;
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
}
