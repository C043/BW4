package Fragnito.enumClass;

import java.util.List;
import java.util.Random;

public enum TipoDistributore {
    DISTRIBUTORE_AUTOMATICO, RIVENDITORE_AUTORIZZATO;

    private static final List<TipoDistributore> values = List.of(values());

    public static TipoDistributore randomTipoDistributore() {
        Random random = new Random();
        return values.get(random.nextInt(values.size()));
    }
}
