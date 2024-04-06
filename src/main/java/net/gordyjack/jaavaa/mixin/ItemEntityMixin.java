package net.gordyjack.jaavaa.mixin;

import net.gordyjack.jaavaa.mixinterfaces.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin
        extends Entity
        implements Ownable,
        ItemEntityMixinInterface {
    @Unique
    private Vec3d oldPos = Vec3d.ZERO;
    @Unique
    private boolean delayEnded = false;

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Override
    public Vec3d jaavaa$getOldPos() {
        return oldPos;
    }
    @Override
    public void jaavaa$setOldPos(Vec3d pos) {
        oldPos = pos;
    }
    @Override
    public boolean jaavaa$delayEnded() {
        return delayEnded;
    }
    @Override
    public void jaavaa$setDelayEnded(boolean ended) {
        delayEnded = ended;
    }
}
