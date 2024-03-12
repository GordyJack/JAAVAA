package net.gordyjack.jaavaa.mixin;

import net.gordyjack.jaavaa.mixinterfaces.ItemEntityMixinInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin
        extends Entity
        implements Ownable,
        ItemEntityMixinInterface {
    @Unique
    private Vec3d oldPos = Vec3d.ZERO;

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
}
