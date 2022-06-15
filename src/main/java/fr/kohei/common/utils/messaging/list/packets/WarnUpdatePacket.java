package fr.kohei.common.utils.messaging.list.packets;

import fr.kohei.common.cache.data.Report;
import fr.kohei.common.cache.data.Warn;
import fr.kohei.common.utils.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WarnUpdatePacket implements Packet {

    private final Warn warn;

}
