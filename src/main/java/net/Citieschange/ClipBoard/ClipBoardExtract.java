package net.Citieschange.ClipBoard;

import com.ldtteam.structurize.api.ItemStackUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import com.simibubi.create.content.equipment.clipboard.ClipboardEntry;

import java.util.ArrayList;
import java.util.List;


public class ClipBoardExtract {
    public int count;
    public List<MaterialEntry> materials = new ArrayList<>();
    public static ResourceLocation PICKUPID;
    public ItemStack itemStacks;
    public static List<List<ClipboardEntry>> icon;

    public boolean isCreateClipboard(ItemStack stack) {
        PICKUPID = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (ItemStackUtils.isEmpty(stack)) {
            return false;
        } else return isnotClipboard();
    }

    public static Boolean isnotClipboard() {
        if (PICKUPID.toString().equals("create:clipboard")) {
            return true;
        }
        return false;
    }

    public void nbtget(ItemStack stack) {
        icon = ClipboardEntry.readAll(stack);
    }
    public List<MaterialEntry> nbtget(List<List<ClipboardEntry>> icon) {
        for (List<ClipboardEntry> page : icon) {
            List<ClipboardEntry> itemStacks12 = icon.get(0);
            for (ClipboardEntry pagfe : itemStacks12) {
                materials.add(new MaterialEntry(pagfe.icon,pagfe.itemAmount));
                itemStacks = pagfe.icon;
                count = pagfe.itemAmount;
            }
        }
        System.out.println(materials);
        return materials;
    }
    public class MaterialEntry{
        public ItemStack itemStack;
        public final int count;
        public MaterialEntry(ItemStack itemStack, int count){
            this.itemStack = itemStack;
            this.count = count;
        }
        @Override
        public String toString() {
            return "MaterialEntry{" +
                    "itemStack='" + itemStack + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
//}