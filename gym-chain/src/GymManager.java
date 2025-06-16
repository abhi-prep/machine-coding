import model.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.time.*;

class GymManager {

    private static GymManager instance;
    private final Map<Integer, Gym> gyms = new ConcurrentHashMap<>();
    private final Map<Integer, Booking> bookings = new ConcurrentHashMap<>();
    private final Map<String, Set<Integer>> customerBookings = new ConcurrentHashMap<>();

    private final AtomicInteger gymIdGenerator = new AtomicInteger(1);
    private final AtomicInteger classIdGenerator = new AtomicInteger(1);
    private final AtomicInteger bookingIdGenerator = new AtomicInteger(1);

    private final ClassValidatorFactory classValidatorFactory = ClassValidatorFactory.getInstance();

    private GymManager() {}

    public static GymManager getInstance() {
        if(instance == null){
            instance = new GymManager();
        }
        return instance;
    }

    // Admin APIs
    public int addGym(String name, String location, int maxAccommodation) {
        int gymId = gymIdGenerator.getAndIncrement();
        gyms.put(gymId, new Gym(gymId, name, location, maxAccommodation));
        System.out.println("Gym added: " + gymId);
        return gymId;
    }

    public void removeGym(int gymId) {
        Gym gym = gyms.remove(gymId);
        if (gym == null) throw new IllegalArgumentException("Gym not found");

        synchronized (gym) {
            for (GymClass c : gym.getClasses().values()) {
                for (Integer bookingId : c.getBookings()) {
                    bookings.remove(bookingId);
                }
            }
            gym.getClasses().clear();
        }
        System.out.println("Gym removed: " + gymId);
    }

    public int addClass(int gymId, String classType, int maxLimit, String startTime, String endTime) {
        Gym gym = gyms.get(gymId);
        if (gym == null) throw new IllegalArgumentException("Gym not found");

        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);
        int classId = classIdGenerator.getAndIncrement();
        GymClass gymClass = new GymClass(classId, classType, maxLimit, start, end);

        ClassValidator classValidator = classValidatorFactory.getClassValidator(ClassTimeValidator.class.getName());
        if(!classValidator.validate(gym, gymClass)){
            throw new IllegalArgumentException("Invalid class time");
        }

        synchronized (gym) {
            int currentTotal = gym.getTotalClassLimitDuring(start, end);
            if (currentTotal + maxLimit > gym.getMaxAccommodation())
                throw new IllegalArgumentException("Class limit exceeds gym capacity");

            gym.addClass(new GymClass(classId, classType, maxLimit, start, end));
            System.out.println("Class added: " + classId);
            return classId;
        }
    }

    public void removeClass(int gymId, int classId) {
        Gym gym = gyms.get(gymId);
        if (gym == null) throw new IllegalArgumentException("Gym not found");

        synchronized (gym) {
            GymClass cls = gym.getClasses().remove(classId);
            if (cls == null) throw new IllegalArgumentException("Class not found");

            for (Integer bookingId : cls.getBookings()) {
                bookings.remove(bookingId);
            }
        }
        System.out.println("Class removed: " + classId);
    }

    // Customer APIs
    public int bookClass(String customerId, int gymId, int classId) {
        Gym gym = gyms.get(gymId);
        if (gym == null) throw new IllegalArgumentException("Gym not found");

        GymClass cls = gym.getClasses().get(classId);
        if (cls == null) throw new IllegalArgumentException("Class not found");

        synchronized (cls) {
            if (cls.getBookings().size() >= cls.getMaxLimit())
                throw new IllegalStateException("Class is full");

            Set<Integer> bookingsForCustomer = customerBookings.computeIfAbsent(customerId, k -> ConcurrentHashMap.newKeySet());

            synchronized (bookingsForCustomer) {
                for (Integer bId : bookingsForCustomer) {
                    Booking b = bookings.get(bId);
                    if (b.getClassId() == classId)
                        throw new IllegalStateException("Already booked");
                }

                int bookingId = bookingIdGenerator.getAndIncrement();
                Booking booking = new Booking(bookingId, customerId, gymId, classId);
                bookings.put(bookingId, booking);
                cls.addBooking(bookingId);
                bookingsForCustomer.add(bookingId);

                System.out.println("Booking successful: " + bookingId);
                return bookingId;
            }
        }
    }

    public List<Booking> getAllBookings(String customerId) {
        Set<Integer> bookingIds = customerBookings.getOrDefault(customerId, Set.of());
        List<Booking> result = new ArrayList<>();
        for (Integer bId : bookingIds) {
            result.add(bookings.get(bId));
        }
        return result;
    }

    public void cancelBooking(int bookingId) {
        Booking booking = bookings.remove(bookingId);
        if (booking == null) throw new IllegalArgumentException("Booking not found");

        Gym gym = gyms.get(booking.getGymId());
        if (gym == null) return;

        GymClass cls = gym.getClasses().get(booking.getClassId());
        if (cls != null) {
            cls.removeBooking(bookingId);
        }

        Set<Integer> userBookings = customerBookings.get(booking.getCustomerId());
        if (userBookings != null) userBookings.remove(bookingId);

        System.out.println("Booking cancelled: " + bookingId);
    }
}

