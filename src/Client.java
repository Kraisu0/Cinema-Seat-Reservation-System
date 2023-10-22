import java.io.*;
import java.util.*;

class Client implements Serializable {
    private String surname;
    private String name;
    private String email;
    private String phoneNumber;
    private Screening screening;
    private List<String> seats;

    public Client(String surname, String name, String email, String phoneNumber, Screening screening, List<String> seats) {
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.screening = screening;
        this.seats = seats;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Screening getScreening() {
        return screening;
    }

    public List<String> getSeats() {
        return seats;
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return surname + ", " + name + ", " + email + ", " + phoneNumber + ", " + screening + ", " + seats;
    }

}