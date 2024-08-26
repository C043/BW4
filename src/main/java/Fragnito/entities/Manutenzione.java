package Fragnito.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "manutenzioni")
public class Manutenzione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "data_inizio")
    private LocalDate dataInizio;
    @Column(name = "data_fine")
    private LocalDate dataFine;
    private String motivo;


    //molti veicoli possono essere in manutenzione
    @ManyToOne
    @JoinColumn(name = "veicolo_id")
    private Mezzo mezzo;

    public Manutenzione(LocalDate dataFine, String motivo, Mezzo mezzo) {
        this.dataInizio = LocalDate.now();
        this.dataFine = dataFine;
        this.motivo = motivo;
        this.mezzo = mezzo;
    }


    //GETTER E SETTER


    public UUID getId() {
        return id;
    }


    public LocalDate getStartDate() {
        return dataInizio;
    }

    public void setStartDate(LocalDate dataInizio) {
        this.dataInizio = Manutenzione.this.dataInizio;
    }

    public LocalDate getEndDate() {
        return dataFine;
    }

    public void setEndDate(LocalDate dataFine) {
        this.dataFine = Manutenzione.this.dataFine;
    }

    public String getDescription() {
        return motivo;
    }

    public void setDescription(String motivo) {
        this.motivo = motivo;
    }

    public Mezzo getVeicolo() {
        return mezzo;
    }

    public void setVeicolo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "id=" + id +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", motivo='" + motivo + '\'' +
                ", veicolo=" + mezzo.getId() +
                '}';
    }

}
