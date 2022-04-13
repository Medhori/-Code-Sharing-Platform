package platform.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Entity
@Table(name = "code")
public class Code {

    @Id
    private UUID id;
    private String code;
    private String date;
    private long time;
    private int views;
    private boolean isTimeRestriction;
    private boolean isViewsRestriction;

    public Code() {
    }

    public Code(@JsonProperty String code, @JsonProperty long time, @JsonProperty int views) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.time = time;
        this.views = views;
        String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        this.date = localDateTime.format(formatter);
        isViewsRestriction = views > 0;
        isTimeRestriction = time > 0;
    }

    public boolean viewRest() {
        return isViewsRestriction;
    }

    public boolean timeRest() {
        return isTimeRestriction;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
    public String printID() {
        return id.toString();
    }

    public boolean rest() {
        return isTimeRestriction && isViewsRestriction;
    }


}
