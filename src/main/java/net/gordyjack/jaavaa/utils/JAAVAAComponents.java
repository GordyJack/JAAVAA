package net.gordyjack.jaavaa.utils;

import com.mojang.serialization.*;
import net.gordyjack.jaavaa.*;
import net.minecraft.component.*;
import net.minecraft.network.codec.*;
import net.minecraft.registry.*;

import java.util.function.*;

public class JAAVAAComponents {
    public static final DataComponentType<Boolean> MAGNET_STATE = registerDataComponent("magnet_state",
            builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL));
    
    private static <T> DataComponentType<T> registerDataComponent(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, (builderOperator.apply(DataComponentType.builder())).build());
    }
    public static void init() {
        JAAVAA.logInfo("Initializing components");
    }
}
