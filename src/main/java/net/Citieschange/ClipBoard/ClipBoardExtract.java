package net.Citieschange.ClipBoard;

import com.ldtteam.structurize.api.util.ItemStackUtils;
import com.simibubi.create.content.equipment.clipboard.ClipboardEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ClipBoardExtract {
    public static boolean check;
    public List<MaterialEntry> materials = new ArrayList<>();
    public static ResourceLocation PICKUPID;
    public static List<List<ClipboardEntry>> icon;
    public boolean isCreateClipboard(ItemStack stack) {
        PICKUPID= ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (ItemStackUtils.isEmpty(stack))
        {
            return false;
        } else return PICKUPID.toString().equals("create:clipboard");
    }
public void nbtget(ItemStack stack){
        icon=ClipboardEntry.readAll(stack);
}
    public List<MaterialEntry> ExtractClipboard(List<List<ClipboardEntry>> icon) {
        for (List<ClipboardEntry> page : icon) {
            for (ClipboardEntry entry : page) {
                materials.add(new MaterialEntry(entry.icon, entry.itemAmount));
            }
        }
        return materials;
    }
    public class MaterialEntry{
        public ItemStack itemStack;
        public int count;
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
