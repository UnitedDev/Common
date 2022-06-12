package fr.kohei.common.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Ranks {

    ADMIN("&4&lADMIN", 1000),
    RESP("&c&lRESP", 100),
    DEV("&e&lDEV", 90),
    MODPLUS("&9&lMOD+", 41),
    MOD("&9&lMOD", 40),
    MODTEST("&3&lMOD", 39),
    CM("&b&lCM", 27),
    STAFF("&b&lSTAFF", 26),
    GDESIGN("&5&lGDESIGN", 25),
    PARTNER("&d&lSTAR", 21),
    AMI("&d&lAMI", 20),
    STREAMER("&5&lSTREAMER", 16),
    YTB("&f&lYTB", 15),
    MUGIWARA("&c&lMUGIWARA", 10),
    HEROS("&a&lHEROS", 7),
    VILAIN("&5&lVILAIN", 5),
    DEFAULT("&7", 0),

    ;

    private final String prefix;
    private final int power;
}
