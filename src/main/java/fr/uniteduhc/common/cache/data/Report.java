package fr.uniteduhc.common.cache.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class Report {

    private final Date date;
    private final UUID reportId;
    private final UUID uuid;
    private final UUID reporter;
    private final String message;
    private boolean resolved;

}
