package Fragnito.enumClass;

import java.util.List;
import java.util.Random;

public enum TipoMezzo {
    TRAM, BUS;

    private static final List<TipoMezzo> values = List.of(values());

    public static TipoMezzo randomTipoMezzo() {
        Random random = new Random();
        return values.get(random.nextInt(values.size()));
    }
}
