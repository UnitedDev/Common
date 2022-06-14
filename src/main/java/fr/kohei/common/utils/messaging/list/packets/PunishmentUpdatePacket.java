package fr.kohei.common.utils.messaging.list.packets;

import fr.kohei.common.cache.data.PunishmentData;
import fr.kohei.common.utils.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PunishmentUpdatePacket implements Packet {

    private final PunishmentData punishmentData;
    private final boolean delete;

}
