package movie.fpoly.mticket.models;

import java.io.Serializable;

public class Schedule implements Serializable {
    private int schedule_id;
    private int movie_id;
    private int room_id;
    private String schedule_date;

    public Schedule(int movie_id, int room_id, String schedule_date) {
        this.movie_id = movie_id;
        this.room_id = room_id;
        this.schedule_date = schedule_date;
    }

    public Schedule() {
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }
}
