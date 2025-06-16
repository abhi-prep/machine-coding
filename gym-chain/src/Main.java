import model.Booking;

public class Main {
    public static void main(String[] args) {

        GymManager gymManager = GymManager.getInstance();
        int gymId = gymManager.addGym("Gym1", "Delhi", 100);
        int classId = gymManager.addClass(gymId, "cardio", 20, "06:00", "07:00");

        int bookingId = gymManager.bookClass("customer1", gymId, classId);

        System.out.println("Customer1 Bookings:");
        for (Booking b : gymManager.getAllBookings("customer1")) {
            System.out.println(b);
        }

        gymManager.cancelBooking(bookingId);

        System.out.println("After Cancellation:");
        for (Booking b : gymManager.getAllBookings("customer1")) {
            System.out.println(b);
        }

        gymManager.removeClass(gymId, classId);
        gymManager.removeGym(gymId);
    }
}