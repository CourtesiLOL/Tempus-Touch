package org.courtesilol;

public enum City {
    BARCELONA(41.3851, 2.1734),
    BADALONA(41.4501, 2.2470),
    MONTBAIX(41.4771, 2.4803),
    TARRASA(41.5645, 1.9783),
    SABADELL(41.5331, 2.1211),
    MARTORELL(41.5505, 1.8778),
    VILANOVA_I_LA_GELTRU(41.2246, 1.7284),
    GAVA(41.2982, 2.0153),
    SITGES(41.2362, 1.8350),
    MANRESA(41.7221, 1.8175),
    VILADECANS(41.3280, 2.0239),
    GRANOLLERS(41.6083, 2.2896),
    CORNELLA_DE_LLOBREGAT(41.3591, 2.0884),
    EL_PRAT_DE_LLOBREGAT(41.3460, 2.0804),
    CUNIT(41.2076, 1.7189),
    RIPOLLET(41.4543, 2.1473),
    MOIA(41.7373, 2.0450),
    PARETS_DEL_VALLES(41.6054, 2.2346),
    BERGA(42.0255, 1.8614),
    CASTELLBISBAL(41.4860, 1.8510);

    private final double latitud;
    private final double longitud;

    City(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}