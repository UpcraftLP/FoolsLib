package com.github.upcraftlp.foolslib.api.entity;

import com.github.upcraftlp.foolslib.api.luck.event.LuckyEventProvider;
import com.github.upcraftlp.foolslib.api.luck.event.bow.BowEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

public class EntityLuckyArrow extends EntityArrow implements LuckyEventProvider {

    protected ResourceLocation luckyBlock;
    public final List<BlockPos> posList = new LinkedList<>();
    private BlockPos storedPos = BlockPos.ORIGIN;
    private int luck = 0;

    @SuppressWarnings("unused")
    public EntityLuckyArrow(World worldIn) {
        super(worldIn);
    }

    public EntityLuckyArrow(World worldIn, EntityLivingBase shooter, float velocity) {
        super(worldIn, shooter, velocity);
    }

    @Override
    public ResourceLocation getLuckyBlock() {
        return luckyBlock;
    }

    @Override
    public void setLuck(int luck) {
        this.luck = luck;
    }

    @Override
    public int getLuck() {
        return this.luck;
    }

    @Override
    public void setLuckyBlock(ResourceLocation luckyBlock) {
        this.luckyBlock = luckyBlock;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(this.worldObj.isRemote) return;
        if(!this.inGround) { //ATs are broken AF, remember to run setupDecompWorkspace on all modules!
            BlockPos pos = this.getPosition();
            if(!pos.equals(this.storedPos)) {
                this.posList.add(pos);
                this.storedPos = pos;
            }
        }
        else {
            if(this.rand.nextInt(7) == 0) {
                BowEvent.drawLine(this.rand, this.worldObj, this.posList);
            }
            this.setDead();
        }
    }

    public void activate(EntityPlayerMP player) {
        this.activateEvent(luckyBlock, this.worldObj, this.getPosition(), player, luck);
    }
}
