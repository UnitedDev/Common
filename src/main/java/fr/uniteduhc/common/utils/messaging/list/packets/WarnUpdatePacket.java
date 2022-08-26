package fr.uniteduhc.common.utils.messaging.list.packets;

import fr.uniteduhc.common.cache.data.Warn;
import fr.uniteduhc.common.utils.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WarnUpdatePacket implements Packet {

    private final Warn warn;

}
