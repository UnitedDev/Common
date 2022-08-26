package fr.uniteduhc.common.utils.messaging.list.subscribers;

import fr.uniteduhc.common.CommonProvider;
import fr.uniteduhc.common.utils.messaging.list.packets.ReportUpdatePacket;
import fr.uniteduhc.common.utils.messaging.pigdin.IncomingPacketHandler;
import fr.uniteduhc.common.utils.messaging.pigdin.PacketListener;

public class ReportUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(ReportUpdatePacket packet) {
        CommonProvider.getInstance().getReports().removeIf(r -> r.getReportId().equals(packet.getReport().getReportId()));
        CommonProvider.getInstance().getReports().add(packet.getReport());
    }

}
