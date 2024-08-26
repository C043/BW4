package Fragnito.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "distributori")
public class Distributore {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDistributore tipo;

    @Enumerated(EnumType.STRING)
    private StatoDistributore stato;

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
        return "Distributore{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", stato=" + stato +
                '}';
    }
}
