package com.logthedeveloper.createlogansutils.menu;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, CreateLogansUtils.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<UpgradeableTankMenu>> UPGRADEABLE_TANK_MENU =
            MENUS.register("upgradeable_tank_menu", () -> IMenuTypeExtension.create(UpgradeableTankMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}