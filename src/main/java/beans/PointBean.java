package beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.*;
import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class PointBean {
    private List<Point> points;

    private Point point;

    private String time_string;

    private DataBaseConnection dataBaseConnection;

    public PointBean(){
        points = new ArrayList<>();
        point = new Point();
        point.setX_val(0);
        point.setR_val(2);
        point.setY_val(0);
        time_string = "";
        dataBaseConnection = new DataBaseConnection();
        points = dataBaseConnection.loadRaws();
    }

    public String addRow(){
        /*if (!fillPoint()){
            return "";
        }*/
        fillPoint();
        points.add(point);
        dataBaseConnection.addRow(point);
        return "redirect";
    }

    public void fillPoint(){
        LocalDateTime myDateObj = LocalDateTime.now();
        System.out.println("Before formatting: " + myDateObj);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        point.setCurrent_time(myDateObj.format(myFormatObj));
        point.setId(points.size() + 1);
        point.setResult(inRect() || inCircle() || inTriangle());
        //return setTime();
    }

    public boolean inRect(){
        return point.getX_val() >= 0 && point.getY_val() >= 0 &&
                point.getX_val() <= point.getR_val()/2 && point.getY_val() <= point.getR_val();
    }

    public boolean inCircle(){
        return point.getX_val() >= 0 && point.getY_val() <= 0 &&
                point.getX_val()*point.getX_val() + point.getY_val()*point.getY_val() <= (point.getR_val()/2)*(point.getR_val()/2);
    }

    public boolean inTriangle(){
        return point.getX_val() <= 0 && point.getY_val() <= 0 && point.getX_val() >= -point.getY_val() - point.getR_val();
    }

    public List<Point> getPoints() {
        return points;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    /*public boolean setTime(){
        try {
            OffsetDateTime time = OffsetDateTime.parse(time_string);
            point.setTime(time);
            return true;
        }catch (DateTimeParseException e) {
            FacesContext.getCurrentInstance().addMessage("input-form:time_input", new FacesMessage("Неверный формат", "Неверный формат"));
            return false;
        }
    }*/
    
    public void clear(){
        points.clear();
        dataBaseConnection.clear();
    }

    public String getTime_string() {
        return time_string;
    }

    public void setTime_string(String time_string) {
        this.time_string = time_string;
    }
}
