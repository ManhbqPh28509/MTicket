package movie.fpoly.mticket.models;

import java.io.Serializable;

public class Room implements Serializable {
    private int room_id;
    private int cinema_id;
    private String room_name;

    public Room(int cinema_id, String room_name) {
        this.cinema_id = cinema_id;
        this.room_name = room_name;
    }

    public Room() {
    }

    public int getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(int cinema_id) {
        this.cinema_id = cinema_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }
}
