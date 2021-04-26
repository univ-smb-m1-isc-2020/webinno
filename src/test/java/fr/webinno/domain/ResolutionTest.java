package fr.webinno.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ResolutionTest {

    Resolution resolution = new Resolution(0L,"Test des classes");

    @Test
    void getIdResolution() {
        assertThat(resolution.getIdResolution()).isEqualTo(0);
    }

    @Test
    void getAction() {
        assertThat(resolution.getAction()).isEqualTo("Test des classes");
    }

    @Test
    void setAction() {
        assertThat(resolution.getAction()).isEqualTo("Test des classes");
        resolution.setAction("Test");
        assertThat(resolution.getAction()).isEqualTo("Test");
    }

    @Test
    void getUserResolutions() {
        assertThat(resolution.getUserResolutions()).isEqualTo(new ArrayList<UserResolution>());

        User user = new User("Jean", "Jacques", "jean.jacques@gmail.com", "jeanjacques");
        UserResolution ur = new UserResolution(Frequence.SEMAINE, 2, user, resolution);
        List<UserResolution> userResolutionList = new ArrayList<UserResolution>();
        userResolutionList.add(ur);
        resolution.getUserResolutions().add(ur);

        assertThat(resolution.getUserResolutions()).isEqualTo(userResolutionList);
    }

    @Test
    void testToString() {
        assertThat(resolution.toString()).isEqualTo(resolution.getAction());
    }

    @Test
    void setIdResolution() {
        assertThat(resolution.getIdResolution()).isEqualTo(0);
        resolution.setIdResolution(1000L);
        assertThat(resolution.getIdResolution()).isEqualTo(1000);
    }
}