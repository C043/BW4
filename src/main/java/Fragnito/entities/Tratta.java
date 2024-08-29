package Fragnito.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tratte")


public class Tratta {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String partenza;

    @Column(nullable = false)
    private String capolinea;

    @Column(name = "tempo_previsto", nullable = false)
    private int tempoPrevisto;

    @OneToMany(mappedBy = "tratta")
    private List<Mezzo> mezzi = new ArrayList<>();

    public Tratta() {
    }

    public Tratta(String partenza, String capolinea, int tempoPrevisto) {
        this.partenza = partenza;
        this.capolinea = capolinea;
        this.tempoPrevisto = tempoPrevisto;
    }

    public List<Mezzo> getMezzi() {
        return mezzi;
    }

    public void setMezzi(List<Mezzo> mezzi) {
        this.mezzi = mezzi;
    }

    public UUID getId() {
        return id;
    }

    public String getPartenza() {
        return partenza;
    }

    public void setPartenza(String partenza) {
        this.partenza = partenza;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }

    public int getTempoPrevisto() {
        return tempoPrevisto;
    }

    public void setTempoPrevisto(int tempoPrevisto) {
        this.tempoPrevisto = tempoPrevisto;
    }


    @Override
    public String toString() {
        return "Tratta: " +
                "id = " + id +
                ", partenza = '" + partenza + '\'' +
                ", capolinea = '" + capolinea + '\'' +
                ", tempo previsto = " + tempoPrevisto;
    }
}
