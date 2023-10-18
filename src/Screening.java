import java.io.*;
import java.util.*;

class Screening implements Serializable {
    private String title;
    private String day;
    private String time;
    private int ageRestrictions;
    private Map<Character, Map<Integer, Boolean>> seats;

    public Screening(String title, String day, String time, int ageRestrictions, Map<Character, Map<Integer, Boolean>> seats) {
        this.title = title;
        this.day = day;
        this.time = time;
        this.ageRestrictions = ageRestrictions;
        this.seats = seats;
    }

    public String getTitle() {
        return title;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public int getAgeRestrictions() {
        return ageRestrictions;
    }

    public Map<Character, Map<Integer, Boolean>> getSeats() {
        return seats;
    }

    public void setSeats(Map<Character, Map<Integer, Boolean>> seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Screening{" +
                "title='" + title + '\'' +
                ", day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", ageRestrictions=" + ageRestrictions +
                '}';
    }
}