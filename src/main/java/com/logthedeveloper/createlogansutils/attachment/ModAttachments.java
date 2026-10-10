package com.logthedeveloper.createlogansutils.attachment;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, CreateLogansUtils.MODID);

    public static final Supplier<AttachmentType<Integer>> REDSTONE_EXPOSURE =
            ATTACHMENT_TYPES.register("redstone_exposure",
                    () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    public static void register(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }
}