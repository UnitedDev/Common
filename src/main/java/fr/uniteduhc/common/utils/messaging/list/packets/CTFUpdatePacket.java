package fr.uniteduhc.common.utils.messaging.list.packets;

import fr.uniteduhc.common.cache.server.impl.CTFServer;
import fr.uniteduhc.common.utils.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CTFUpdatePacket implements Packet {

    private final CTFServer ctfServer;

}
