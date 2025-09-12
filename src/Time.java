package src;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {
    private LocalDateTime currentTime;

    public Time() {
        this.currentTime = LocalDateTime.now();
    }

    public String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return currentTime.format(formatter);
    }

    public String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return currentTime.format(formatter);
    }

    public void refreshTime() {
        this.currentTime = LocalDateTime.now();
    }
}
