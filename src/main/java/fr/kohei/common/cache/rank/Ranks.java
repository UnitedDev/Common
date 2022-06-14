package fr.kohei.common.cache.rank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Ranks {

    ADMIN("&4&lADMIN&4", 100000),
    RESP("&c&lRESP&c", 10000),
    DEV("&e&lDEV&e", 9999),
    MODPLUS("&9&lMOD+&9", 41),
    MOD("&9&lMOD&9", 40),
    MODTEST("&3&lMOD&3", 39),
    CM("&b&lCM&b", 27),
    STAFF("&b&lSTAFF&b", 26),
    GDESIGN("&5&lGDESIGN&5", 25),
    PARTNER("&d&lSTAR&d", 21),
    AMI("&d&lAMI&d", 20),
    STREAMER("&5&lSTREAMER&5", 16),
    YTB("&f&lYTB&f", 15),
    MUGIWARA("&c&lMUGIWARA&c", 10),
    HEROS("&a&lHEROS&a", 7),
    VILAIN("&5&lVILAIN&5", 5),
    DEFAULT("&7", 0),

    ;

    private final String prefix;
    private final int power;
}
