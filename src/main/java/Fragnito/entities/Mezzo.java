package Fragnito.entities;


import Fragnito.enumClass.StatoMezzo;
import Fragnito.enumClass.TipoMezzo;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "veicoli")
public class Mezzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "tipo_veicolo")
    private int capacita;
    @Enumerated(EnumType.STRING)
    private TipoMezzo tipoMezzo;
    @Column(name = "stato_veicolo")
    @Enumerated(EnumType.STRING)
    private StatoMezzo statoMezzo;

    //un veicolo può essere stato in manutenzione più volte
    @OneToMany(mappedBy = "veicolo")
    private List<Manutenzione> manutenzioni;

    public Mezzo(TipoMezzo tipoMezzo, int capacita, StatoMezzo statoMezzo) {
        this.tipoMezzo = tipoMezzo;
        this.capacita = capacita;
        this.statoMezzo = statoMezzo;
    }

    //GETTER E SETTER

    public UUID getId() {
        return id;
    }


    public int getCapacita() {
        return capacita;
    }

    public void setCapacita(int capacita) {
        this.capacita = capacita;
    }

    public TipoMezzo getTipoVeicolo() {
        return tipoMezzo;
    }

    public void setTipoVeicolo(TipoMezzo tipoMezzo) {
        this.tipoMezzo = tipoMezzo;
    }

    public StatoMezzo getStatoVeicolo() {
        return statoMezzo;
    }

    public void setStatoVeicolo(StatoMezzo statoMezzo) {
        this.statoMezzo = statoMezzo;
    }

    public List<Manutenzione> getManutenzioni() {
        return manutenzioni;
    }

    public void setManutenzioni(List<Manutenzione> manutenzioni) {
        this.manutenzioni = manutenzioni;
    }

    @Override
    public String toString() {
        return "Veicolo{" +
                "id=" + id +
                ", tipoVeicolo=" + tipoMezzo +
                ", capacita=" + capacita +
                ", statoVeicolo=" + statoMezzo +
                ", manutenzioni=" + manutenzioni +
                '}';
    }
}
