package fr.kohei.common.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Report {

    private final UUID reportId;
    private final UUID uuid;
    private final UUID reporter;
    private final String message;
    private final boolean resolved;

}
