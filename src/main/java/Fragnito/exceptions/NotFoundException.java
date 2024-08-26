package Fragnito.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    private String message;

    public NotFoundException(UUID id) {
        super("Elemento con id " + id + " non trovato.");
    }
}
