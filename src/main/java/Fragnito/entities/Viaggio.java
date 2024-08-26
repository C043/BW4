package Fragnito.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "viaggi")
public class Viaggio {
    @Id
    @GeneratedValue
    private UUID id;
    // Aggiungere mezzo

    @Column(nullable = false)
    private LocalDate giorno;

    @Column(name = "tempo_effettivo", nullable = false)
    private Integer tempoEffettivo;

    public Viaggio() {
    }

    public Viaggio(LocalDate giorno, Integer tempoEffettivo) {
        this.giorno = giorno;
        this.tempoEffettivo = tempoEffettivo;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getGiorno() {
        return giorno;
    }

    public void setGiorno(LocalDate giorno) {
        this.giorno = giorno;
    }

    public Integer getTempoEffettivo() {
        return tempoEffettivo;
    }

    public void setTempoEffettivo(Integer tempoEffettivo) {
        this.tempoEffettivo = tempoEffettivo;
    }

    @Override
    public String toString() {
        return "Viaggio{" +
                "id=" + id +
                ", giorno=" + giorno +
                ", tempoEffettivo=" + tempoEffettivo +
                '}';
    }
}
