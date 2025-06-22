package movie.fpoly.mticket.models;

import java.io.Serializable;

public class Booking implements Serializable {
    private int booking_id;
    private int user_id;
    private int schedule_id;
    private int seat_id;
    private double price;

    public Booking() {
    }

    public Booking(int booking_id, int user_id, int schedule_id, int seat_id, double price) {
        this.booking_id = booking_id;
        this.user_id = user_id;
        this.schedule_id = schedule_id;
        this.seat_id = seat_id;
        this.price = price;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public int getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
