package beans;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

/*create table points (
 id INT,
 x_val FLOAT,
 y_val FLOAT,
 r_val FLOAT,
 time CHAR(50),
 result BOOLEAN,
 date_time TIMESTAMP
)*/

@Entity
@Table(name = "points")
public class Point implements Serializable {
    @Id
    private int id;

    @Column(name = "x_val")
    private double x_val;

    @Column(name = "y_val")
    private double y_val;

    @Column(name = "r_val")
    private double r_val;

    @Column(name = "time")
    private String current_time;

    @Column(name = "result")
    private boolean result;

    @Column(name = "date_time")
    private OffsetDateTime time;

    public Point(double x, double y, double r, String current, boolean res){
        x_val = x;
        y_val = y;
        r_val = r;
        current_time = current;
        result = res;
    }

    public Point(){
        x_val = 0;
        y_val = 0;
        r_val = 2;
        current_time = "";
        result = false;
    }

    public double getR_val() {
        return r_val;
    }

    public double getX_val() {
        return x_val;
    }

    public double getY_val() {
        return y_val;
    }

    public String getCurrent_time() {
        return current_time;
    }

    public boolean isResult() {
        return result;
    }

    public void setR_val(double r_val) {
        this.r_val = r_val;
    }

    public void setX_val(double x_val) {
        this.x_val = x_val;
    }

    public void setCurrent_time(String current_time) {
        this.current_time = current_time;
    }

    public void setY_val(double y_val) {
        this.y_val = y_val;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public void setTime(OffsetDateTime time) {
        this.time = time;
    }
}

