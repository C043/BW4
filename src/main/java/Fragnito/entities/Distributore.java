package Fragnito.entities;

import Fragnito.enumClass.StatoDistributore;
import Fragnito.enumClass.TipoDistributore;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "distributori")
public class Distributore {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDistributore tipo;

    @Enumerated(EnumType.STRING)
    private StatoDistributore stato;

    public Distributore() {
    }

    public Distributore(String nome, TipoDistributore tipo, StatoDistributore stato) {
        this.nome = nome;
        this.tipo = tipo;
        this.stato = stato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public TipoDistributore getTipo() {
        return tipo;
    }

    public void setTipo(TipoDistributore tipo) {
        this.tipo = tipo;
    }

    public StatoDistributore getStato() {
        return stato;
    }

    public void setStato(StatoDistributore stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        return "Distributore: " +
                "id =" + id +
                ", nome ='" + nome + '\'' +
                ", tipo =" + tipo +
                ", stato =" + stato;
    }
}
