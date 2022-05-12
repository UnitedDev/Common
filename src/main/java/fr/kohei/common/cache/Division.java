package fr.kohei.common.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public enum Division {
    AUCUNE("&cAucune", 150, profile -> {
        profile.setHosts(profile.getHosts() + 1);
        profile.setCoins(profile.getCoins() + 300);
    }, "&fVous avez obtenu &e300 coins &fet &c1 host&f."),

    BRONZE("&6Bronze", 250, profile -> {
        profile.setHosts(profile.getHosts() + 2);
        profile.setCoins(profile.getCoins() + 500);
    }, "&fVous avez obtenu &e500 coins &fet &c2 host&f."),

    ARGENT("&7Argent", 400, profile -> {
        profile.setHosts(profile.getHosts() + 2);
        profile.setCoins(profile.getCoins() + 700);
    }, "&fVous avez obtenu &e700 coins &fet &c2 host&f."),

    OR("&eOr", 750, profile -> {
        profile.setHosts(profile.getHosts() + 3);
        profile.setCoins(profile.getCoins() + 1000);
    }, "&fVous avez obtenu &e1000 coins &fet &c3 host&f."),

    DIAMANT("&bDiamant", 900, profile -> {
        // TODO RANK FOR 5 DAYS
    }, "&fVous avez obtenu le grade &c<grade> &fpour &c5 jours&f."),

    EMERAUDE("&aEmeraude", 1200, profile -> {
        // TODO RANK FOR 14 DAYS
    }, "&fVous avez obtenu le grade &c<grade> &fpour &c14 jours&f."),

    PLATINE("&8Platine", 1500, profile -> {
        // TODO RANK FOR 30 DAYS
    }, "&fVous avez obtenu le grade &c<grade> &fpour &c30 jours&f."),

    MAITRE("&dMaitre", 10000000, profile -> {}, "");

    private final String display;
    private final int experience;
    private final Consumer<ProfileData> consumer;
    private final String message;
}
