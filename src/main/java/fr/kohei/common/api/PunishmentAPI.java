package fr.kohei.common.api;

import fr.kohei.common.cache.PunishmentData;

import java.util.List;
import java.util.UUID;

public interface PunishmentAPI {
    List<PunishmentData> getPunishments();

    List<PunishmentData> getPunishments(UUID uuid);

    void updatePunishment(PunishmentData punishmentData);

    void deletePunishment(PunishmentData punishmentData);
}
