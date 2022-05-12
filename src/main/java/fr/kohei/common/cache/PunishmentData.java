package fr.kohei.common.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PunishmentData {
    private final UUID punishmentId;
    private final UUID punished;
    private final UUID executor;
    private final String reason;
    private final long duration;
    private final List<PunishmentEdit> edits;

    public PunishmentData(UUID punished, UUID executor, String reason, long duration) {
        this.punishmentId = UUID.randomUUID();
        this.punished = punished;
        this.executor = executor;
        this.reason = reason;
        this.duration = duration;
        this.edits = new ArrayList<>();
    }

    @Getter
    @RequiredArgsConstructor
    private static class PunishmentEdit {
        private final long newDuration;
        private final String reason;
        private final UUID executor;
    }
}
