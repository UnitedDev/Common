package fr.uniteduhc.common.cache.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Warn {

    private final UUID warnId;
    private final UUID playerId;
    private final String reason;
    private final UUID issuerId;
    private final Date date;

}
