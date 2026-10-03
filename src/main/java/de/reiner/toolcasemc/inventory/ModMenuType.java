package de.reiner.toolcasemc.inventory;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ModMenuType {

    public static final MenuType<ToolCaseMenu> TOOLCASE = register("tool_case", ToolCaseMenu::new);

    private static <T extends AbstractContainerMenu> MenuType<T> register(final String name, final MenuType.MenuSupplier<T> constructor) {
        return Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath("ultimatedtoolcasemc", name), new MenuType<>(constructor, FeatureFlags.VANILLA_SET));
    }

    public static void init(){}
}