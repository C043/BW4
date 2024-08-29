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

    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzo mezzo;

    @Column(nullable = false)
    private LocalDate giorno;

    @Column(name = "tempo_effettivo", nullable = false)
    private Integer tempoEffettivo;

    public Viaggio() {
    }

    public Viaggio(Mezzo mezzo, LocalDate giorno, Integer tempoEffettivo) {
        this.mezzo = mezzo;
        this.giorno = giorno;
        this.tempoEffettivo = tempoEffettivo;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
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
        return "Viaggio: " +
                "id =" + id +
                ", mezzo =" + mezzo.getId() +
                ", giorno =" + giorno +
                ", tempo effettivo =" + tempoEffettivo;
    }
}
