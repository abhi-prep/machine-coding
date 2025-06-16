package model;

public class Booking {
    private final int id;
    private final String customerId;
    private final int gymId;
    private final int classId;

    public Booking(int id, String customerId, int gymId, int classId) {
        this.id = id;
        this.customerId = customerId;
        this.gymId = gymId;
        this.classId = classId;
    }

    public int getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getGymId() {
        return gymId;
    }

    public int getClassId() {
        return classId;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", customerId='" + customerId + '\'' +
                ", gymId=" + gymId +
                ", classId=" + classId +
                '}';
    }
}
