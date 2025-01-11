package dev.ujol.miniproject;

// Node Class for Courier Parcel
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class Parcel {

    String parcelId;
    String sender;
    String receiver;
    String status;
    Parcel next;

    Parcel(String parcelId, String sender, String receiver, String status) {
        this.parcelId = parcelId;
        this.sender = sender;
        this.receiver = receiver;
        if (isValidStatus(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid status. Allowed statuses: Pending, In Transit, Delivered.");
        }
        this.next = null;
    }

    boolean isValidStatus(String status) {
        return status.equals("Pending") || status.equals("In Transit") || status.equals("Delivered");
    }
}

// Linked List Implementation
class ParcelManager {

    private Parcel head;

    public ParcelManager() {
        this.head = null;
    }

    void insert(Parcel parcel) {
        if (head == null) {
            head = parcel;
        } else {
            Parcel current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = parcel;
        }

        System.out.println("Parcel added: " + parcel.parcelId);
    }

    void delete(String parcelId) {
        if (head == null) {
            System.out.println("Parcel list is empty.");
            return;
        }
        if (head.parcelId.equals(parcelId)) {
            head = head.next;
            System.out.println("Parcel removed: " + parcelId);
            return;
        }

        Parcel current = head;
        while (current.next != null && !current.next.parcelId.equals(parcelId)) {
            current = current.next;
        }
        if (current.next == null) {
            System.out.println("Parcel not found: " + parcelId);
        } else {
            current.next = current.next.next;
            System.out.println("Parcel removed: " + parcelId);
        }

        System.out.println("Parcel not found: " + parcelId);
    }

    void updateStatus(String parcelId, String newStatus) {
        Parcel current = head;
        if (!current.isValidStatus(newStatus)) {
            System.out.println("Invalid status. Allowed statuses: Pending, In Transit, Delivered.");
            return;
        }
        while (current != null) {
            if (current.parcelId.equals(parcelId)) {
                current.status = newStatus;
                System.out.println("Parcel status updated: " + parcelId);
                return;
            }
            current = current.next;
        }

        System.out.println("Parcel not found: " + parcelId);
    }

    Parcel search(String parcelId) {
        Parcel current = head;
        while (current != null) {
            if (current.parcelId.equals(parcelId)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    Parcel pop() {
        if (head == null) {
            System.out.println("Parcel list is empty.");
            return null;
        }
        Parcel temp = head;
        head = head.next;
        System.out.println("Last parcel removed: " + temp.parcelId);
        return temp;
    }

    void sortParcelsBy(String criteria) {
        if (head == null || head.next == null) {
            System.out.println("No sorting needed.");
            return;
        }

        ArrayList<Parcel> parcelArray = new ArrayList<>();
        Parcel current = head;
        while (current != null) {
            parcelArray.add(current);
            current = current.next;
        }

        switch (criteria.toLowerCase()) {
            case "sender":
                parcelArray.sort(Comparator.comparing(parcel -> parcel.sender));
                break;
            case "receiver":
                parcelArray.sort(Comparator.comparing(parcel -> parcel.receiver));
                break;
            case "status":
                parcelArray.sort(Comparator.comparing(parcel -> parcel.status));
                break;
            default:
                System.out.println("Invalid sorting criteria. Available options: sender, receiver, status.");
                return;
        }

        System.out.println("Parcels sorted by " + criteria + ":");
        for (Parcel parcel : parcelArray) {
            System.out
                    .println("ID=" + parcel.parcelId + ", Sender=" + parcel.sender + ", Receiver=" + parcel.receiver
                            + ", Status=" + parcel.status);
        }
    }

    void displayAll() {
        Parcel current = head;
        if (current == null) {
            System.out.println("Parcel list is empty.");
            return;
        }

        while (current != null) {
            System.out.println("Parcel ID: " + current.parcelId + ", Sender: " + current.sender + ", Receiver: "
                    + current.receiver + ", Status: " + current.status);
            current = current.next;
        }
    }

}

public class CourierManagementSystem {

    public static void main(String[] args) {
        ParcelManager parcelManager = new ParcelManager();
        Scanner scanner = new Scanner(System.in);

        parcelManager.insert(new Parcel("P001", "Alice", "Bob", "Pending"));
        parcelManager.insert(new Parcel("P002", "Charlie", "David", "Pending"));
        parcelManager.insert(new Parcel("P003", "Eve", "Frank", "Pending"));

        while (true) {
            System.out.println("\nCourier Parcel Management System");
            System.out.println(
                    "1. Add Parcel\n2. Delete Parcel\n3. Update Parcel Status\n4. Search Parcel\n5. Sort Parcels\n6. Pop from Stack\n7. Display Parcels\n8. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Parcel ID: ");
                    String parcelId = scanner.nextLine();
                    System.out.print("Enter Sender: ");
                    String sender = scanner.nextLine();
                    System.out.print("Enter Receiver: ");
                    String receiver = scanner.nextLine();
                    parcelManager.insert(new Parcel(parcelId, sender, receiver, "Pending"));
                    scanner.close();
                    break;
                case 2:
                    System.out.print("Enter Parcel ID to delete: ");
                    parcelId = scanner.nextLine();
                    parcelManager.delete(parcelId);
                    scanner.close();
                    break;
                case 3:
                    System.out.print("Enter Parcel ID to update: ");
                    parcelId = scanner.nextLine();
                    System.out.print("Enter new status: ");
                    String status = scanner.nextLine();
                    parcelManager.updateStatus(parcelId, status);
                    scanner.close();
                    break;
                case 4:
                    System.out.print("Enter Parcel ID to search: ");
                    parcelId = scanner.nextLine();
                    Parcel parcel = parcelManager.search(parcelId);
                    if (parcel != null) {
                        System.out.println("Parcel Found: ID=" + parcel.parcelId + ", Sender=" + parcel.sender
                                + ", Receiver=" + parcel.receiver + ", Status=" + parcel.status);
                    } else {
                        System.out.println("Parcel not found.");
                    }
                    scanner.close();
                    break;
                case 5:
                    System.out.print("Enter sorting criteria (sender, receiver, status): ");
                    String criteria = scanner.nextLine();
                    parcelManager.sortParcelsBy(criteria);
                    scanner.close();
                    break;
                case 6:
                    parcelManager.pop();
                    break;
                case 7:
                    parcelManager.displayAll();
                    break;
                case 8:
                    System.out.println("Exiting system.");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

    }
}
