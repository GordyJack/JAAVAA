package net.gordyjack.jaavaa.mixin;

import net.gordyjack.jaavaa.item.custom.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(AllayEntity.class)
public class AllayEntityMixin {
	@Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
	private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		// This code is injected into the start of AllayEntity.interactMob()
		if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof AbstractCollectorItem) {
			cir.setReturnValue(ActionResult.PASS);
		}
	}
}