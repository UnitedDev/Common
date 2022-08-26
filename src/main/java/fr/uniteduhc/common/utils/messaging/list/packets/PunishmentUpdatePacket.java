package fr.uniteduhc.common.utils.messaging.list.packets;

import fr.uniteduhc.common.cache.data.PunishmentData;
import fr.uniteduhc.common.utils.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PunishmentUpdatePacket implements Packet {

    private final PunishmentData punishmentData;
    private final boolean delete;

}
