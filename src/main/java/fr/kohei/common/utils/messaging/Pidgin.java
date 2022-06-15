package fr.kohei.common.utils.messaging;

import com.google.gson.Gson;
import fr.kohei.common.CommonProvider;
import fr.kohei.common.utils.messaging.list.packets.*;
import fr.kohei.common.utils.messaging.list.subscribers.*;
import fr.kohei.common.utils.messaging.pigdin.IncomingPacketHandler;
import fr.kohei.common.utils.messaging.pigdin.Packet;
import fr.kohei.common.utils.messaging.pigdin.PacketListener;
import lombok.Getter;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Getter
public class Pidgin {

    private final RedissonClient client;
    private final RTopic topic;

    private final Gson gson = new Gson();
    private final HashMap<Class<? extends Packet>, PacketListener> adapters;
    private final HashMap<Class<? extends Packet>, String> types;
    private final HashMap<String, Class<? extends Packet>> cTypes;

    public Pidgin(String topic) {
        this.client = Redisson.create();
        this.topic = this.client.getTopic(topic);

        this.adapters = new HashMap<>();
        this.types = new HashMap<>();
        this.cTypes = new HashMap<>();
        this.topic.addListener(String.class, new MessagingListener());

        this.registerAdapter(ProfileUpdatePacket.class, new ProfileUpdateSubscriber());
        this.registerAdapter(PunishmentUpdatePacket.class, new PunishmentUpdateSubscriber());
        this.registerAdapter(RankUpdatePacket.class, new RankUpdateSubscriber());
        this.registerAdapter(LobbyUpdatePacket.class, new LobbyUpdateSubscriber());
        this.registerAdapter(UHCUpdatePacket.class, new UHCUpdateSubscriber());
        this.registerAdapter(CTFUpdatePacket.class, new CTFUpdateSubscriber());
    }

    public void registerAdapter(Class<? extends Packet> clazz, PacketListener listener) {
        this.adapters.put(clazz, listener);
        String uuid = clazz.getSimpleName();
        this.types.put(clazz, uuid);
        this.cTypes.put(uuid, clazz);
    }

    public void sendPacket(Packet packet) {
        CompletableFuture.runAsync(() ->
                this.topic.publish(types.get(packet.getClass()) + ";" + gson.toJson(packet))
        );
    }

    private class MessagingListener implements MessageListener<String> {
        @Override
        public void onMessage(CharSequence charSequence, String s) {
            CompletableFuture.runAsync(() -> {
                try {
                    String id = s.split(";")[0];
                    Packet packet = gson.fromJson(s.split(";")[1], cTypes.get(id));

                    Class<? extends Packet> clazz = null;
                    for (Map.Entry<Class<? extends Packet>, String> entry : types.entrySet()) {
                        Class<? extends Packet> aClass = entry.getKey();
                        String s1 = entry.getValue();
                        if (s1.equalsIgnoreCase(id)) clazz = aClass;
                    }

                    PacketListener listener = adapters.get(clazz);

                    for (Method m : listener.getClass().getDeclaredMethods()) {
                        if (m.getDeclaredAnnotation(IncomingPacketHandler.class) != null) {
                            try {
                                m.invoke(listener, packet);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
            });
        }
    }
}