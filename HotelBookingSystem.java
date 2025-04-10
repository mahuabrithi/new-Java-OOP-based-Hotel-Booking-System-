import java.util.*;

// Room class
class Room {
    private int number;
    private String type;
    private double price;
    private boolean available = true;

    public Room(int number, String type, double price) {
        this.number = number;
        this.type = type;
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String toString() {
        return "Room " + number + " [" + type + "] - $" + price + " - " + (available ? "Available" : "Booked");
    }
}

// Review class
class Review {
    private int rating;
    private String comment;

    public Review(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public String toString() {
        return "[Rating: " + rating + "/5, Comment: " + comment + "]";
    }
}

// Booking class
class Booking {
    private String guestName;
    private Room room;
    private boolean checkedIn = false;
    private boolean checkedOut = false;
    private Review review;

    public Booking(String guestName, Room room) {
        this.guestName = guestName;
        this.room = room;
    }

    public String getGuestName() {
        return guestName;
    }

    public Room getRoom() {
        return room;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public void generateInvoice() {
        System.out.println("--- Invoice ---");
        System.out.println("Guest: " + guestName);
        System.out.println("Room Number: " + room.getNumber());
        System.out.println("Room Type: " + room.getType());
        System.out.println("Amount Due: $" + room.getPrice());
        System.out.println("----------------");
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public String toString() {
        return "Booking [Guest=" + guestName + ", Room=" + room.getNumber() +
                ", Checked-In=" + checkedIn + ", Checked-Out=" + checkedOut +
                (review != null ? ", Review=" + review : "") + "]";
    }
}

// Hotel class
class Hotel {
    private String name;
    private List<Room> rooms = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public Hotel(String name) {
        this.name = name;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void searchAndBookRoom(Scanner scanner) {
        System.out.print("Enter room type (Single/Double/Suite): ");
        String type = scanner.next();
        for (Room room : rooms) {
            if (room.getType().equalsIgnoreCase(type) && room.isAvailable()) {
                System.out.print("Enter your name: ");
                String guestName = scanner.next();
                room.setAvailable(false);
                Booking booking = new Booking(guestName, room);
                bookings.add(booking);
                System.out.println("Room booked successfully! Room Number: " + room.getNumber());
                return;
            }
        }
        System.out.println("No available rooms of type: " + type);
    }

    public void checkIn(Scanner scanner) {
        System.out.print("Enter your name to check in: ");
        String name = scanner.next();
        for (Booking booking : bookings) {
            if (booking.getGuestName().equalsIgnoreCase(name) && !booking.isCheckedIn()) {
                booking.setCheckedIn(true);
                System.out.println("Check-in successful for " + name);
                return;
            }
        }
        System.out.println("Booking not found or already checked in.");
    }

    public void checkOut(Scanner scanner) {
        System.out.print("Enter your name to check out: ");
        String name = scanner.next();
        for (Booking booking : bookings) {
            if (booking.getGuestName().equalsIgnoreCase(name) && booking.isCheckedIn()) {
                booking.setCheckedOut(true);
                booking.getRoom().setAvailable(true);
                System.out.println("Check-out successful. Generating invoice...");
                booking.generateInvoice();
                return;
            }
        }
        System.out.println("No check-in record found.");
    }

    public void leaveReview(Scanner scanner) {
        System.out.print("Enter your name to leave a review: ");
        String name = scanner.next();
        for (Booking booking : bookings) {
            if (booking.getGuestName().equalsIgnoreCase(name)) {
                System.out.print("Enter rating (1 to 5): ");
                int rating = scanner.nextInt();
                System.out.print("Enter review comment: ");
                scanner.nextLine(); // consume leftover newline
                String comment = scanner.nextLine();
                booking.setReview(new Review(rating, comment));
                System.out.println("Thanks for your feedback!");
                return;
            }
        }
        System.out.println("No booking found under that name.");
    }

    public void viewBookings() {
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }
}

// Main class
public class HotelBookingSystem {
    public static void main(String[] args) {
        Hotel hotel = new Hotel("Sunrise Inn");
        Scanner scanner = new Scanner(System.in);
        int choice;

        // Add sample rooms
        hotel.addRoom(new Room(101, "Single", 50.0));
        hotel.addRoom(new Room(102, "Double", 80.0));
        hotel.addRoom(new Room(201, "Suite", 150.0));

        do {
            System.out.println("\n--- Hotel Booking System ---");
            System.out.println("1. Search and Book Room");
            System.out.println("2. Check-in");
            System.out.println("3. Check-out");
            System.out.println("4. Leave Review");
            System.out.println("5. View All Bookings");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    hotel.searchAndBookRoom(scanner);
                    break;
                case 2:
                    hotel.checkIn(scanner);
                    break;
                case 3:
                    hotel.checkOut(scanner);
                    break;
                case 4:
                    hotel.leaveReview(scanner);
                    break;
                case 5:
                    hotel.viewBookings();
                    break;
                case 6:
                    System.out.println("Thank you for using the Hotel Booking System!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 6);

        scanner.close();
    }
}
