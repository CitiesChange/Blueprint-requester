package net.Citieschange.mixin;

import com.minecolonies.core.Network;
import com.minecolonies.core.client.gui.WindowPostBoxMain;
import com.minecolonies.core.colony.buildings.workerbuildings.PostBox;
import com.minecolonies.core.network.messages.server.colony.building.postbox.PostBoxRequestMessage;
import net.Citieschange.ClipBoard.ClipBoardExtract;
import net.Citieschange.ClipBoard.channel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.Citieschange.ClipBoard.ClipBoardExtract.nbt;

@Mixin(WindowPostBoxMain.class)
public class WindowPostBoxMainMixin {
    @Final
    @Shadow
    private PostBox.View postBoxView;
    @Shadow
    private boolean deliverAvailable;
    @Unique
    private int cheakart= channel.art;

    @Inject(method ="onOpened", at = @At("HEAD"),remap = false)
    public void onOpened(CallbackInfo ci) {
        if(cheakart==1) {
            deliverymanconfig$Clickwithbook();
        }
    }
    @Unique
    private void deliverymanconfig$Clickwithbook() {
        ClipBoardExtract network = new ClipBoardExtract();
        network.ExtractClipboard(nbt);
        int questcount = 0;
        for (ClipBoardExtract.MaterialEntry materialEntry : network.materials) {
            ResourceLocation itemRL = new ResourceLocation(materialEntry.itemStack);
            Item itemObj = ForgeRegistries.ITEMS.getValue(itemRL);
            ItemStack item = (itemObj != null) ? new ItemStack(itemObj) : ItemStack.EMPTY;
            int qty = materialEntry.count;
            if (qty > 0) {
                Network.getNetwork().sendToServer(new PostBoxRequestMessage(postBoxView, item.copy(), qty, deliverAvailable));
                questcount += 1;
            }
        }
        int finalQuestcount = questcount;
        if (questcount>0) {
            net.minecraft.client.Minecraft.getInstance().execute(() -> {
                net.minecraft.client.player.LocalPlayer player = net.minecraft.client.Minecraft.getInstance().player;
                player.displayClientMessage(net.minecraft.network.chat.Component.literal("§a已从剪贴板创建" + finalQuestcount + "个物品请求"), false);
            });
        }
    }
}