package Fragnito.entities;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.UUID;

@Entity
@Table(name = "tratte")


public class Tratta {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "partenza")
    private String partenza;

    @Column(name = "capolinea")
    private String capolinea;
    @Column(name = "tempo_previsto")
    private Time tempoPrevisto;

    //onetomany dei viaggi


    public Tratta() {
    }

    public Tratta(String partenza, String capolinea, Time tempoPrevisto) {
        this.partenza = partenza;
        this.capolinea = capolinea;
        this.tempoPrevisto = tempoPrevisto;
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

    public Time getTempoPrevisto() {
        return tempoPrevisto;
    }

    public void setTempoPrevisto(Time tempoPrevisto) {
        this.tempoPrevisto = tempoPrevisto;
    }


    @Override
    public String toString() {
        return "Tratta{" +
                "id=" + id +
                ", partenza='" + partenza + '\'' +
                ", capolinea='" + capolinea + '\'' +
                ", tempoPrevisto=" + tempoPrevisto +
                '}';
    }
}
