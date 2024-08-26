package Fragnito.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "utenti")
public class Utente {

    @Id
    @GeneratedValue
    private UUID id;

    private String nome;
    private String cognome;
    private LocalDate dataNascita;

    @Embedded
    @OneToOne(mappedBy = "tessera")
    private Tessera tessera;

    public Utente() {
    }

    public Utente(UUID id, String nome, String cognome, LocalDate dataNascita) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
    }

    public UUID getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }
}
