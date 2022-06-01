package fr.kohei.common.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PunishmentData {
    private final UUID punishmentId;
    private final UUID punished;
    private final UUID executor;
    private final String reason;
    private final Date date;
    private long duration;
    private final List<PunishmentEdit> edits;
    private PunishmentType punishmentType;

    public PunishmentData(PunishmentType punishmentType, UUID punished, UUID executor, String reason, long duration) {
        this.punishmentType = punishmentType;
        this.punishmentId = UUID.randomUUID();
        this.punished = punished;
        this.executor = executor;
        this.reason = reason;
        this.date = new Date();
        this.duration = duration;
        this.edits = new ArrayList<>();
    }

    @Getter
    @RequiredArgsConstructor
    public static class PunishmentEdit {
        private final long oldDuration;
        private final long newDuration;
        private final String reason;
        private final UUID executor;
    }

    public enum PunishmentType {
        BAN,
        MUTE,
        BLACKLIST
    }
}
