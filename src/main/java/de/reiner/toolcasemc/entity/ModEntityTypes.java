package de.reiner.toolcasemc.entity;

import de.reiner.toolcasemc.UltimatedToolCaseMC;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;


public class ModEntityTypes {
    public static void init(){}

    private static ResourceKey<EntityType<?>> createID(final String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(UltimatedToolCaseMC.MOD_ID, name));
    }

    private static <T extends Entity> EntityType<T> register(ResourceKey<EntityType<?>> key, EntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static final EntityType<ThrownSickle> SICKLE = register(
            createID("sickle"),
            EntityType.Builder.<ThrownSickle>of(
                            ThrownSickle::new,
                            MobCategory.MISC
                    )
                    .noLootTable()
                    .sized(1.0F, 1.0F)
                    .eyeHeight(0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
    );
    public static final EntityType<ThrownKnive> KNIVE = register(
            createID("knive"),
            EntityType.Builder.<ThrownKnive>of(
                            ThrownKnive::new,
                            MobCategory.MISC
                    )
                    .noLootTable()
                    .sized(0.3F, 0.3F)
                    .eyeHeight(0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
    );
}
