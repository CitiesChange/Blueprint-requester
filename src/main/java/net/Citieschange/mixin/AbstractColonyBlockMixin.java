package net.Citieschange.mixin;

import com.minecolonies.api.blocks.AbstractColonyBlock;
import com.minecolonies.api.colony.IColonyManager;
import com.minecolonies.api.colony.buildings.views.IBuildingView;
import com.minecolonies.core.colony.buildings.workerbuildings.PostBox;
import net.Citieschange.ClipBoard.ClipBoardExtract;
import net.Citieschange.ClipBoard.channel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractColonyBlock.class)
public class AbstractColonyBlockMixin {
    @Inject(method = "useItemOn", at = @At("HEAD"),remap = false)
    public void use(ItemStack stack, BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray, CallbackInfoReturnable<ItemInteractionResult> cir) {
        @Nullable final IBuildingView buildings = IColonyManager.getInstance().getBuildingView(worldIn.dimension(), pos);
        ItemStack heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        ClipBoardExtract extract = new ClipBoardExtract();
        if (buildings!=null&&!(buildings instanceof PostBox.View)) {
            return;
        }
            if (extract.isCreateClipboard(heldItem)) {
                extract.nbtget(heldItem);
                channel.setArt(1);

            } else {
                channel.setArt(0);
            }
    }
}
