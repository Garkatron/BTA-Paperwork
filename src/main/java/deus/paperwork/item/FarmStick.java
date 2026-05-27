package deus.paperwork.item;

import deus.brainless.Brainless;
import deus.paperwork.Paperwork;
import deus.paperwork.entities.employee.MobEmployee;
import deus.paperwork.entities.employee.farmer.MobEmployeeFarmer;
import deus.paperwork.util.PoscArea;
import deus.utils.annotations.RegisterItemModel;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@RegisterItemModel(model = ItemModelStandard.class)
public class FarmStick extends Item {

    private TilePosc pointA = null;
    private TilePosc pointB = null;

    public FarmStick(@NotNull String translationKey, @NotNull String namespaceId, int id) {
        super(translationKey, namespaceId, id);
    }

    @Override
    public boolean onUseOnBlock(@NotNull ItemStack selfStack, @NotNull World world,
                                @Nullable Player player, @NotNull TilePosc blockPos,
                                @NotNull Side side, double xHit, double yHit) {
        if (world.isClientSide || player == null) return false;

        TilePosc pos = new TilePos(blockPos.x(), blockPos.y(), blockPos.z());

        if (player.isSneaking()) {
            pointB = pos;
            Paperwork.LOGGER.info("[FarmStick] Point B set: {}", pos);
        } else {
            pointA = pos;
            pointB = null; // reset B on new A
            Paperwork.LOGGER.info("[FarmStick] Point A set: {}", pos);
        }

        if (pointA != null && pointB != null) {
            Paperwork.LOGGER.info("[FarmStick] Area ready: {} -> {}", pointA, pointB);
        }

        return true;
    }

    @Override
    public boolean useOnEntity(@NotNull ItemStack selfStack, @NotNull Player player, @NotNull Mob mob) {
        if (!(mob instanceof MobEmployeeFarmer farmer)) return false;

        if (pointA == null || pointB == null) {
            Paperwork.LOGGER.info("[FarmStick] Area incomplete — set both points first (click A, shift+click B)");
            return false;
        }

        farmer.farm_area = Optional.of(new PoscArea.Area2D(pointA, pointB));
        Paperwork.LOGGER.info("[FarmStick] Farm area assigned to farmer: {} -> {}", pointA, pointB);

        pointA = null;
        pointB = null;
        return true;
    }
}
