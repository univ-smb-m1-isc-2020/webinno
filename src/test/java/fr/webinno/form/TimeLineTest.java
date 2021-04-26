package fr.webinno.form;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TimeLineTest {

    TimeLine timeLine = new TimeLine(new Date(),1);

    @Test
    void getDate() {
        Date d = new Date();
        assertThat(timeLine.getDate()).isEqualToIgnoringHours(d);
    }

    @Test
    void testToString() {
        String str = timeLine.getDate() + " ==> Level = " + timeLine.getLevel();
        assertThat(timeLine.toString()).isEqualTo(str);
    }

    @Test
    void setDate() {
        Date d = new Date();

        timeLine.setDate(d);
        assertThat(timeLine.getDate()).isEqualToIgnoringHours(d);

        timeLine.setDate(new Date(20/03/2020));
        assertThat(timeLine.getDate()).isEqualToIgnoringHours(new Date(20/03/2020));
    }

    @Test
    void getLevel() {
        assertThat(timeLine.getLevel()).isEqualTo(1);
    }

    @Test
    void setLevel() {
        timeLine.setLevel(1);
        assertThat(timeLine.getLevel()).isEqualTo(1);

        timeLine.setLevel(0);
        assertThat(timeLine.getLevel()).isEqualTo(0);
    }
}