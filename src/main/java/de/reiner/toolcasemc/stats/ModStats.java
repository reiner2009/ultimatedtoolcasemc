package de.reiner.toolcasemc.stats;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

public class ModStats {
    public static final Identifier OPEN_TOOLCASE=makeCustomStat("tool_case_open", StatFormatter.DEFAULT);

    private static Identifier makeCustomStat(final String id, final StatFormatter formatter) {
        Identifier location = Identifier.withDefaultNamespace(id);
        Registry.register(BuiltInRegistries.CUSTOM_STAT, id, location);
        Stats.CUSTOM.get(location, formatter);
        return location;
    }

    public static void init(){}
}
