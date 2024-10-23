package com.brandon3055.draconicevolution.common.network;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;

import com.brandon3055.draconicevolution.common.tileentities.TileParticleGenerator;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class ParticleGenPacket implements IMessage {

    byte id = 0;
    short value = 0;
    int tileX = 0;
    int tileY = 0;
    int tileZ = 0;

    public ParticleGenPacket() {}

    public ParticleGenPacket(byte id, short value, int x, int y, int z) {
        this.id = id;
        this.value = value;
        this.tileX = x;
        this.tileY = y;
        this.tileZ = z;
    }

    @Override
    public void toBytes(ByteBuf bytes) {
        bytes.writeByte(id);
        bytes.writeShort(value);
        bytes.writeInt(tileX);
        bytes.writeInt(tileY);
        bytes.writeInt(tileZ);
    }

    @Override
    public void fromBytes(ByteBuf bytes) {
        this.id = bytes.readByte();
        this.value = bytes.readShort();
        this.tileX = bytes.readInt();
        this.tileY = bytes.readInt();
        this.tileZ = bytes.readInt();
    }

    public static class Handler implements IMessageHandler<ParticleGenPacket, IMessage> {

        @Override
        public IMessage onMessage(ParticleGenPacket message, MessageContext ctx) {
            TileEntity tile = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.tileX, message.tileY, message.tileZ);
            if (!(tile instanceof TileParticleGenerator)) {
                return null;
            }
            TileParticleGenerator particleGenerator = (TileParticleGenerator)tile;
            switch (message.id) {
            case 0: particleGenerator.red = message.value; break;
            case 1: particleGenerator.green = message.value; break;
            case 2: particleGenerator.blue = message.value; break;
            case 3: particleGenerator.motionX = (float)message.value / 1000F; break;
            case 4: particleGenerator.motionY = (float)message.value / 1000F; break;
            case 5: particleGenerator.motionZ = (float)message.value / 1000F; break;
            case 6: particleGenerator.scale = (float)message.value / 100F; break;
            case 7: particleGenerator.life = message.value; break;
            case 10: particleGenerator.randomRed = message.value; break;
            case 11: particleGenerator.randomGreen = message.value; break;
            case 12: particleGenerator.randomBlue = message.value; break;
            case 13: particleGenerator.randomMotionX = (float)message.value / 1000F; break;
            case 14: particleGenerator.randomMotionY = (float)message.value / 1000F; break;
            case 15: particleGenerator.randomMotionZ = (float)message.value / 1000F; break;
            case 16: particleGenerator.randomScale = (float)message.value / 100F; break;
            case 17: particleGenerator.randomLife = message.value; break;
            case 20: particleGenerator.spawnX = (float)message.value / 10F; break;
            case 21: particleGenerator.spawnY = (float)message.value / 10F; break;
            case 22: particleGenerator.spawnZ = (float)message.value / 10F; break;
            case 23: particleGenerator.spawnRate = message.value; break;
            case 24: particleGenerator.fade = message.value; break;
            case 25: particleGenerator.gravity = (float)message.value / 1000F; break;
            case 30: particleGenerator.randomSpawnX = (float)message.value / 10F; break;
            case 31: particleGenerator.randomSpawnY = (float)message.value / 10F; break;
            case 32: particleGenerator.randomSpawnZ = (float)message.value / 10F; break;
            case 40: particleGenerator.beamRed = message.value; break;
            case 41: particleGenerator.beamGreen = message.value; break;
            case 42: particleGenerator.beamBlue = message.value; break;
            case 43: particleGenerator.beamPitch = (float)message.value / 10F; break;
            case 44: particleGenerator.beamYaw = (float)message.value / 10F; break;
            case 45: particleGenerator.beamRotation = (float)message.value / 100F; break;
            case 46: particleGenerator.beamScale = (float)message.value / 100F; break;
            case 47: particleGenerator.beamLength = (float)message.value / 100F; break;
            case 100:
            case 101:
            case 102:
            case 103: particleGenerator.page = message.value; break;
            case 110: particleGenerator.canParticleCollide = message.value == 1; break;
            case 111: particleGenerator.selectedParticle = message.value; break;
            case 112: particleGenerator.isParticlesEnabled = message.value == 1; break;
            case 120: particleGenerator.isBeamEnabled = message.value == 1; break;
            case 121: particleGenerator.shouldRenderCore = message.value == 1; break;
            }

            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            if (message.id == 127) {
                if (player.capabilities.isCreativeMode || player.inventory.hasItem(Items.paper)) {
                    giveNote(message, player);
                } else {
                    player.addChatComponentMessage(
                            new ChatComponentText("You need paper in your inventory to do that"));
                }
            }
            player.worldObj.markBlockForUpdate(message.tileX, message.tileY, message.tileZ);
            return null;
        }

        private void giveNote(ParticleGenPacket message, EntityPlayerMP player) {
            if (!player.capabilities.isCreativeMode) {
                player.inventory.consumeInventoryItem(Items.paper);
            }
            ItemStack stack = new ItemStack(Items.paper);
            stack.setTagCompound(new NBTTagCompound());
            TileEntity tile = player.worldObj.getTileEntity(message.tileX, message.tileY, message.tileZ);
            if (tile instanceof TileParticleGenerator) {
                ((TileParticleGenerator)tile).getBlockNBT(stack.getTagCompound());
                stack.setStackDisplayName("Saved Particle Gen Settings");
                player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, stack));
            }
        }
    }
}
