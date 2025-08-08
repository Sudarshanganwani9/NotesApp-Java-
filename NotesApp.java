import java.io.*;
import java.util.Scanner;

public class NotesApp {
    private static final String FILE_NAME = "notes.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n=== NOTES APP ===");
            System.out.println("1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Clear Notes");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            choice = getValidChoice(scanner);

            switch (choice) {
                case 1 -> addNote(scanner);
                case 2 -> viewNotes();
                case 3 -> clearNotes();
                case 4 -> {
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static int getValidChoice(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void addNote(Scanner scanner) {
        System.out.println("Enter your note (type 'END' to finish):");
        StringBuilder note = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equalsIgnoreCase("END")) {
            note.append(line).append(System.lineSeparator());
        }

        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(note.toString());
            fw.write("------------------------\n");
            System.out.println("Note saved successfully!");
        } catch (IOException e) {
            System.err.println("Error writing note: " + e.getMessage());
        }
    }

    private static void viewNotes() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\n--- Saved Notes ---");
            boolean empty = true;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                empty = false;
            }
            if (empty) {
                System.out.println("(No notes found)");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No notes file found yet.");
        } catch (IOException e) {
            System.err.println("Error reading notes: " + e.getMessage());
        }
    }

    private static void clearNotes() {
        try (FileWriter fw = new FileWriter(FILE_NAME)) {
            fw.write("");
            System.out.println("All notes cleared!");
        } catch (IOException e) {
            System.err.println("Error clearing notes: " + e.getMessage());
        }
    }
  }
