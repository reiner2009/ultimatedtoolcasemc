package de.reiner.toolcasemc.damageType;

import de.reiner.toolcasemc.UltimatedToolCaseMC;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageType {
    public static final ResourceKey<DamageType> SICKLE_DAMAGE=ResourceKey.create(Registries.DAMAGE_TYPE, UltimatedToolCaseMC.id("sickle"));
    public static final ResourceKey<DamageType> KNIVE_DAMAGE=ResourceKey.create(Registries.DAMAGE_TYPE, UltimatedToolCaseMC.id("knive"));
}
