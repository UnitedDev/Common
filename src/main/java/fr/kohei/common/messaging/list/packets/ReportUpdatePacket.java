package fr.kohei.common.messaging.list.packets;

import fr.kohei.common.cache.Report;
import fr.kohei.common.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReportUpdatePacket implements Packet {

    private final Report report;

}
