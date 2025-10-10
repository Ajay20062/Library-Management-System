import java.sql.*;
import java.util.Scanner;

public class LibraryManagementSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/librarydb";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to Library Database Successfully!");

            while (true) {
                System.out.println("\n--- Library Management System ---");
                System.out.println("1. Add Book");
                System.out.println("2. Issue Book");
                System.out.println("3. Return Book");
                System.out.println("4. View Books");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> addBook(conn, sc);
                    case 2 -> issueBook(conn, sc);
                    case 3 -> returnBook(conn, sc);
                    case 4 -> viewBooks(conn);
                    case 5 -> {
                        System.out.println("Exiting...");
                        conn.close();
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice! Try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addBook(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author Name: ");
        String author = sc.nextLine();

        String query = "INSERT INTO books (id, title, author, issued) VALUES (?, ?, ?, false)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ps.setString(2, title);
        ps.setString(3, author);
        ps.executeUpdate();

        System.out.println("✅ Book added successfully!");
    }

    private static void issueBook(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Book ID to issue: ");
        int id = sc.nextInt();
        String query = "UPDATE books SET issued = true WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        int rows = ps.executeUpdate();
        System.out.println(rows > 0 ? "📘 Book issued!" : "❌ Book not found!");
    }

    private static void returnBook(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Book ID to return: ");
        int id = sc.nextInt();
        String query = "UPDATE books SET issued = false WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        int rows = ps.executeUpdate();
        System.out.println(rows > 0 ? "📗 Book returned!" : "❌ Book not found!");
    }

    private static void viewBooks(Connection conn) throws SQLException {
        String query = "SELECT * FROM books";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("\n--- Books List ---");
        while (rs.next()) {
            System.out.printf("ID: %d | Title: %s | Author: %s | Issued: %b\n",
                    rs.getInt("id"), rs.getString("title"),
                    rs.getString("author"), rs.getBoolean("issued"));
        }
    }
}