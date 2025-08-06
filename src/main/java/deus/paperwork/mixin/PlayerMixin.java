package deus.paperwork.mixin;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.IVehicle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Player.class)
public class PlayerMixin {


	@Inject(method = "hurt", at = @At("HEAD"), remap = false, cancellable = true)
	public void test(Entity attacker, int damage, DamageType type, CallbackInfoReturnable<Boolean> cir) {
		IVehicle vehicle = ((Entity) (Object) this).vehicle;
		if (vehicle != null) {
			if (type == DamageType.FALL) {
				cir.setReturnValue(false);
			}
		}

	}
}
