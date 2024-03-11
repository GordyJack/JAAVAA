package net.gordyjack.jaavaa.mixin;

import net.gordyjack.jaavaa.JAAVAA;
import net.gordyjack.jaavaa.item.custom.CollectorItem;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AllayEntity.class)
public class AllayEntityMixin {
	@Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
	private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		// This code is injected into the start of AllayEntity.interactMob()
		JAAVAA.logError("Player right-clicked on an AllayEntity with: " + player.getStackInHand(Hand.MAIN_HAND));
		if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof CollectorItem) {
			cir.setReturnValue(ActionResult.PASS);
		}
	}
}