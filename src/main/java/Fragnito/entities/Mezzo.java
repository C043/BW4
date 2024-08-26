package Fragnito.entities;


import Fragnito.enumClass.StatoMezzo;
import Fragnito.enumClass.TipoMezzo;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "mezzi")
public class Mezzo {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "tipo_mezzo")
    @Enumerated(EnumType.STRING)
    private TipoMezzo tipoMezzo;

    @Column(nullable = false)
    private int capacita;

    @Column(name = "stato_mezzo", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatoMezzo statoMezzo;

    //un veicolo può essere stato in manutenzione più volte
    @OneToMany(mappedBy = "mezzo")
    private List<Manutenzione> manutenzioni = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "tratta_id")
    private Tratta tratta;

    public Mezzo() {
    }

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

    public TipoMezzo getTipoMezzo() {
        return tipoMezzo;
    }

    public void setTipoMezzo(TipoMezzo tipoMezzo) {
        this.tipoMezzo = tipoMezzo;
    }

    public StatoMezzo getStatoMezzo() {
        return statoMezzo;
    }

    public void setStatoMezzo(StatoMezzo statoMezzo) {
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
        return "Mezzo {" +
                "id=" + id +
                ", tipoMezzo=" + tipoMezzo +
                ", capacita=" + capacita +
                ", statoMezzo=" + statoMezzo +
                ", manutenzioni=" + manutenzioni +
                '}';
    }
}
