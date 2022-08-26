package fr.uniteduhc.common.api;

import fr.uniteduhc.common.cache.data.Warn;

import java.util.List;
import java.util.Queue;
import java.util.UUID;

public interface WarnAPI {
    Queue<Warn> getWarns();

    List<Warn> getWarns(UUID uuid);

    void addWarn(Warn warn);

    void removeWarn(Warn warn);
}
