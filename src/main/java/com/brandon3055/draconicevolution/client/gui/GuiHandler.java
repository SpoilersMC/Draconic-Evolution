package com.brandon3055.draconicevolution.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.brandon3055.draconicevolution.DraconicEvolution;
import com.brandon3055.draconicevolution.client.gui.componentguis.GUIManual;
import com.brandon3055.draconicevolution.client.gui.componentguis.GUIReactor;
import com.brandon3055.draconicevolution.client.gui.componentguis.GUIToolConfig;
import com.brandon3055.draconicevolution.common.container.ContainerAdvTool;
import com.brandon3055.draconicevolution.common.container.ContainerDissEnchanter;
import com.brandon3055.draconicevolution.common.container.ContainerDraconiumChest;
import com.brandon3055.draconicevolution.common.container.ContainerEnergyInfuser;
import com.brandon3055.draconicevolution.common.container.ContainerGenerator;
import com.brandon3055.draconicevolution.common.container.ContainerGrinder;
import com.brandon3055.draconicevolution.common.container.ContainerPlayerDetector;
import com.brandon3055.draconicevolution.common.container.ContainerReactor;
import com.brandon3055.draconicevolution.common.container.ContainerSunDial;
import com.brandon3055.draconicevolution.common.container.ContainerUpgradeModifier;
import com.brandon3055.draconicevolution.common.container.ContainerWeatherController;
import com.brandon3055.draconicevolution.common.inventory.InventoryTool;
import com.brandon3055.draconicevolution.common.tileentities.TileDissEnchanter;
import com.brandon3055.draconicevolution.common.tileentities.TileDraconiumChest;
import com.brandon3055.draconicevolution.common.tileentities.TileEnergyInfuser;
import com.brandon3055.draconicevolution.common.tileentities.TileGenerator;
import com.brandon3055.draconicevolution.common.tileentities.TileGrinder;
import com.brandon3055.draconicevolution.common.tileentities.TileParticleGenerator;
import com.brandon3055.draconicevolution.common.tileentities.TilePlayerDetectorAdvanced;
import com.brandon3055.draconicevolution.common.tileentities.TileSunDial;
import com.brandon3055.draconicevolution.common.tileentities.TileUpgradeModifier;
import com.brandon3055.draconicevolution.common.tileentities.TileWeatherController;
import com.brandon3055.draconicevolution.common.tileentities.gates.TileGate;
import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.reactor.TileReactorCore;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler {

    public static final int GUIID_WEATHER_CONTROLLER = 0;
    public static final int GUIID_SUN_DIAL = 1;
    public static final int GUIID_GRINDER = 2;
    public static final int GUIID_TELEPORTER = 3;
    public static final int GUIID_PARTICLEGEN = 5;
    public static final int GUIID_PLAYERDETECTOR = 6;
    public static final int GUIID_ENERGY_INFUSER = 7;
    public static final int GUIID_GENERATOR = 8;
    public static final int GUIID_MANUAL = 9;
    public static final int GUIID_DISSENCHANTER = 10;
    public static final int GUIID_DRACONIC_CHEST = 11;
    public static final int GUIID_TOOL_CONFIG = 12;
    public static final int GUIID_FLOW_GATE = 13;
    public static final int GUIID_REACTOR = 14;
    public static final int GUIID_UPGRADE_MODIFIER = 15;
    public static final int GUIID_CONTAINER_TEMPLATE = 100;

    public GuiHandler() {
        NetworkRegistry.INSTANCE.registerGuiHandler(DraconicEvolution.instance, this);
    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if( tile == null){
            if(id == GUIID_TOOL_CONFIG){
                return new ContainerAdvTool(player.inventory, new InventoryTool(player, null));
            }else if(id == GUIID_TELEPORTER){
                return new GUITeleporter(player);
            }
          return null;
        }
        switch (id) {
        case GUIID_WEATHER_CONTROLLER:
            return tile instanceof TileWeatherController ? new ContainerWeatherController(player.inventory, (TileWeatherController)tile) : null;
        case GUIID_SUN_DIAL:
            return tile instanceof TileSunDial ? new ContainerSunDial(player.inventory, (TileSunDial)tile) : null;
        case GUIID_TELEPORTER:
            return new GUITeleporter(player);
        case GUIID_GRINDER:
            return tile instanceof TileGrinder ? new ContainerGrinder(player.inventory, (TileGrinder)tile) : null;
        case GUIID_PLAYERDETECTOR:
            return tile instanceof TilePlayerDetectorAdvanced ? new ContainerPlayerDetector(player.inventory, (TilePlayerDetectorAdvanced)tile) : null;
        case GUIID_ENERGY_INFUSER:
            return tile instanceof TileEnergyInfuser ? new ContainerEnergyInfuser(player.inventory, (TileEnergyInfuser)tile) : null;
        case GUIID_GENERATOR:
            return tile instanceof TileGenerator ? new ContainerGenerator(player.inventory, (TileGenerator)tile) : null;
        case GUIID_DISSENCHANTER:
            return tile instanceof TileDissEnchanter ? new ContainerDissEnchanter(player.inventory, (TileDissEnchanter)tile) : null;
        case GUIID_DRACONIC_CHEST:
            return tile instanceof TileDraconiumChest ? new ContainerDraconiumChest(player.inventory, (TileDraconiumChest)tile) : null;
        case GUIID_REACTOR:
            return tile instanceof TileReactorCore ? new ContainerReactor(player, (TileReactorCore)tile) : null;
        case GUIID_UPGRADE_MODIFIER:
            return tile instanceof TileUpgradeModifier ? new ContainerUpgradeModifier(player.inventory, (TileUpgradeModifier)tile) : null;
        case GUIID_TOOL_CONFIG:
            return new ContainerAdvTool(player.inventory, new InventoryTool(player, null));
        default: break;
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile == null){
            switch(id){
                case GUIID_TELEPORTER:
                    return new GUITeleporter(player);
                case GUIID_MANUAL:
                    return new GUIManual();
                case GUIID_TOOL_CONFIG:
                    return new GUIToolConfig(player, new ContainerAdvTool(player.inventory, new InventoryTool(player, null)));
                default : return null;
            }
        }
        switch (id) {
        case GUIID_WEATHER_CONTROLLER:
            return tile instanceof TileWeatherController ? new GUIWeatherController(player.inventory, (TileWeatherController)tile) : null;
        case GUIID_SUN_DIAL:
            return tile instanceof TileSunDial ? new GUISunDial(player.inventory, (TileSunDial)tile) : null;
        case GUIID_TELEPORTER:
            return new GUITeleporter(player);
        case GUIID_GRINDER:
            return tile instanceof TileGrinder ? new GUIGrinder(player.inventory, (TileGrinder)tile) : null;
        case GUIID_PARTICLEGEN:
            return tile instanceof TileParticleGenerator ? new GUIParticleGenerator((TileParticleGenerator)tile) : null;
        case GUIID_PLAYERDETECTOR:
            return tile instanceof TilePlayerDetectorAdvanced ? new GUIPlayerDetector(player.inventory, (TilePlayerDetectorAdvanced)tile) : null;
        case GUIID_ENERGY_INFUSER:
            return tile instanceof TileEnergyInfuser ? new GUIEnergyInfuser(player.inventory, (TileEnergyInfuser)tile) : null;
        case GUIID_GENERATOR:
            return tile instanceof TileGenerator ? new GUIGenerator(player.inventory, (TileGenerator)tile) : null;
        case GUIID_MANUAL:
            return new GUIManual();
        case GUIID_DISSENCHANTER:
            return tile instanceof TileDissEnchanter ? new GUIDissEnchanter(player.inventory, (TileDissEnchanter)tile) : null;
        case GUIID_DRACONIC_CHEST:
            return tile instanceof TileDraconiumChest ? new GUIDraconiumChest(player.inventory, (TileDraconiumChest)tile) : null;
        case GUIID_REACTOR:
            return tile instanceof TileReactorCore ? new GUIReactor((TileReactorCore)tile, new ContainerReactor(player, (TileReactorCore)tile)) : null;
        case GUIID_TOOL_CONFIG:
            return new GUIToolConfig(player, new ContainerAdvTool(player.inventory, new InventoryTool(player, null)));
        case GUIID_FLOW_GATE:
            return tile instanceof TileGate ? new GUIFlowGate((TileGate)tile) : null;
        case GUIID_UPGRADE_MODIFIER:
            return tile instanceof TileUpgradeModifier ? 
                    new GUIUpgradeModifier(player.inventory, (TileUpgradeModifier)tile, new ContainerUpgradeModifier(player.inventory, (TileUpgradeModifier)tile)) : null;
        default: break;
        }
        return null;
    }
}