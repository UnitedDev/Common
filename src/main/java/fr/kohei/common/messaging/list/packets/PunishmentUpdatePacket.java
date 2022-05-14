package fr.kohei.common.messaging.list.packets;

import fr.kohei.common.cache.PunishmentData;
import fr.kohei.common.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PunishmentUpdatePacket implements Packet {

    private final PunishmentData punishmentData;
    private final boolean delete;

}
