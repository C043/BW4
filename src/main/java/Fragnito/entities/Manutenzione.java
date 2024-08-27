package Fragnito.entities;

import Fragnito.enumClass.StatoMezzo;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "manutenzioni")
public class Manutenzione {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatoMezzo stato;

    @Column(name = "data_inizio", nullable = false)
    private LocalDate dataInizio;

    @Column(name = "data_fine")
    private LocalDate dataFine;

    private String motivo;


    //molti mezzi possono essere in manutenzione
    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzo mezzo;

    public Manutenzione() {
    }

    public Manutenzione(StatoMezzo stato, String motivo, Mezzo mezzo) {
        this.stato = stato;
        this.dataInizio = LocalDate.now();
        this.motivo = motivo;
        this.mezzo = mezzo;
    }

    //GETTER E SETTER


    public StatoMezzo getStato() {
        return stato;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
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
