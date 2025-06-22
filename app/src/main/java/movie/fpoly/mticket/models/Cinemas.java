package movie.fpoly.mticket.models;

import java.io.Serializable;

public class Cinemas implements Serializable {
    private int cinema_id;
    private String cinema_name;
    private String cinema_address;

    public Cinemas() {
    }

    public Cinemas(String cinema_name, String cinema_address) {
        this.cinema_name = cinema_name;
        this.cinema_address = cinema_address;
    }

    public int getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(int cinema_id) {
        this.cinema_id = cinema_id;
    }

    public String getCinema_name() {
        return cinema_name;
    }

    public void setCinema_name(String cinema_name) {
        this.cinema_name = cinema_name;
    }

    public String getCinema_address() {
        return cinema_address;
    }

    public void setCinema_address(String cinema_address) {
        this.cinema_address = cinema_address;
    }
}
