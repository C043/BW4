package Fragnito.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Nessun elemento con " + id + " è stato trovato.");
    }
}
