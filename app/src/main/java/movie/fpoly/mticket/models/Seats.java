package movie.fpoly.mticket.models;

import java.io.Serializable;

public class Seats implements Serializable {
    private int seat_id;
    private int room_id;
    private String row_seat;
    private int number;
    private boolean seat_status;

    public Seats(int room_id, String row_seat, int number, boolean seat_status) {
        this.room_id = room_id;
        this.row_seat = row_seat;
        this.number = number;
        this.seat_status = seat_status;
    }

    public Seats() {
    }

    public int getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getRow_seat() {
        return row_seat;
    }

    public void setRow_seat(String row_seat) {
        this.row_seat = row_seat;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isSeat_status() {
        return seat_status;
    }

    public void setSeat_status(boolean seat_status) {
        this.seat_status = seat_status;
    }
}
