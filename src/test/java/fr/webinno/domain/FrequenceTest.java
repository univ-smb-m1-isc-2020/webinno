package fr.webinno.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FrequenceTest {
    Frequence frequence = Frequence.SEMAINE;

    @Test
    void values() {
        Frequence[] freqValues = {Frequence.HEURE, Frequence.JOUR, Frequence.SEMAINE, Frequence.MOIS, Frequence.ANNEE};
        assertThat(Frequence.values()).isEqualTo(freqValues);
    }

    @Test
    void valueOf() {
        assertThat(Frequence.valueOf("HEURE")).isEqualTo(Frequence.HEURE);
        assertThat(Frequence.valueOf("JOUR")).isEqualTo(Frequence.JOUR);
        assertThat(Frequence.valueOf("SEMAINE")).isEqualTo(Frequence.SEMAINE);
        assertThat(Frequence.valueOf("MOIS")).isEqualTo(Frequence.MOIS);
        assertThat(Frequence.valueOf("ANNEE")).isEqualTo(Frequence.ANNEE);
    }
}