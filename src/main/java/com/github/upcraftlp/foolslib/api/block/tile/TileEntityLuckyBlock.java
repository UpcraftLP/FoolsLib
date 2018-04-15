package com.github.upcraftlp.foolslib.api.block.tile;

import com.github.upcraftlp.foolslib.api.luck.event.LuckyEventProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class TileEntityLuckyBlock extends TileEntity implements LuckyEventProvider {

    private byte luck;
    private ResourceLocation luckyBlock;

    public TileEntityLuckyBlock() {
        //NO-OP
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.luck = compound.getByte("luck");
        this.luckyBlock = new ResourceLocation(compound.getString("lucky_block"));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setByte("luck", this.luck);
        compound.setString("lucky_block", this.luckyBlock.toString());
    }

    @Override
    public int getLuck() {
        return MathHelper.clamp_int(luck, -100, 100);
    }

    @Override
    public void setLuck(int luck) {
        this.luck = (byte) MathHelper.clamp_int(luck, -100, 100);
        this.markDirty();
    }

    @Override
    public ResourceLocation getLuckyBlock() {
        return luckyBlock;
    }

    @Override
    public void setLuckyBlock(ResourceLocation luckyBlock) {
        this.luckyBlock = luckyBlock;
        this.markDirty();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.setLuck(pkt.getTileEntityType());
    }

    @Override
    public Packet getDescriptionPacket() {
        return new S35PacketUpdateTileEntity(this.getPos(), this.getLuck(), new NBTTagCompound());
    }

    public void activate(@Nullable EntityPlayerMP player) {
        this.worldObj.setBlockToAir(this.pos);
        this.activateEvent(this.getLuckyBlock(), this.worldObj, this.pos, player, this.getLuck());
    }
}
