package deus.paperwork.block.cardboard_box_trap;

import com.mojang.nbt.tags.CompoundTag;
import deus.paperwork.Paperwork;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class LogicCardboardBoxTrap extends BlockLogic {
	public LogicCardboardBoxTrap(Block<?> block, Material material) {
		super(block, material);
		block.withEntity(TileEntityCardboardBoxTrap::new);
	}

	@Override
	public ItemStack @Nullable [] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		TileEntityCardboardBoxTrap cardboardBoxTrap = (TileEntityCardboardBoxTrap) tileEntity;

		if (cardboardBoxTrap.getEntity() == null) {
			world.setBlockAndMetadataWithNotify(x, y, z, cardboardBoxTrap.getSavedBlockId(), cardboardBoxTrap.getSavedBlockMeta());

			TileEntity savedTileEntity = cardboardBoxTrap.getTileEntity();
			if (savedTileEntity != null) {
				world.setTileEntity(x, y, z, savedTileEntity);
			}
		} else {
			Entity entity = cardboardBoxTrap.getEntity();
			try {
				Constructor<? extends Entity> constructor = entity.getClass().getDeclaredConstructor(World.class);
				constructor.setAccessible(true);
				Entity newEntity = constructor.newInstance(world);

				CompoundTag compoundTag = new CompoundTag();
				entity.addAdditionalSaveData(compoundTag);
				newEntity.readAdditionalSaveData(compoundTag);

				newEntity.spawnInit();
				newEntity.moveTo(x + 0.5, y, z + 0.5, 0, 0);

				world.entityJoinedWorld(newEntity);

			} catch (ReflectiveOperationException e) {
				Paperwork.LOGGER.error("["+getClass().getSimpleName()+"] Error instancing entity...");
			}
		}
		return new ItemStack[0];
	}


//	@Override
//	public void onBlockLeftClicked(World world, int x, int y, int z, Player player, Side side, double xHit, double yHit) {
//
//		TileEntityCardboardBoxTrap cardboardBoxTrap = (TileEntityCardboardBoxTrap) world.getTileEntity(x,y,z);
//
//		world.setBlockAndMetadataWithNotify(x,y,z,cardboardBoxTrap.getSavedBlockId(),cardboardBoxTrap.getSavedBlockMeta());
//		world.setTileEntity(x,y,z,cardboardBoxTrap.getTileEntity());
//
//		super.onBlockLeftClicked(world, x, y, z, player, side, xHit, yHit);
//	}
}
