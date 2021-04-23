package fr.webinno.form;

import java.util.Date;

public class TimeLine {
    public Date date;
    public int level; //niveau de réussite

    public TimeLine(Date date, int level){
        this.date = date;
        this.level = level;
    }


    public Date getDate() {
        return date;
    }

    public String toString(){
        return date + " ==> Level = " + level;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
