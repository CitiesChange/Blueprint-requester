package net.Citieschange.ClipBoard;

import com.ldtteam.structurize.api.util.ItemStackUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ClipBoardExtract {
    public int count;
    public static CompoundTag nbt;
    public List<MaterialEntry> materials = new ArrayList<>();
    public static ResourceLocation PICKUPID;
    public String itemStack;
    public boolean isCreateClipboard(ItemStack stack) {
        PICKUPID= ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (ItemStackUtils.isEmpty(stack))
        {
            return false;
        }
        else return isnotClipboard();
    }
    public static Boolean isnotClipboard() {
        if (PICKUPID.toString().equals("create:clipboard")) {
            return true;
        }
        return false;
    }
public String nbtget(ItemStack stack){
        nbt=stack.getTag();
    if (nbt != null) {
        return nbt.toString();
    }
    return stack.toString();
}
    public void ExtractClipboard(CompoundTag nbt) {
        if (nbt!=null&&nbt.contains("Pages")) {
            ListTag pages = nbt.getList("Pages", Tag.TAG_COMPOUND);
            for (int i = 0; i < pages.size(); i++) {
                CompoundTag page = pages.getCompound(i);
                if (page.contains("Entries", Tag.TAG_LIST)) {
                    ListTag entries = page.getList("Entries", Tag.TAG_COMPOUND);
                    for (int j = 0; j < entries.size(); j++) {
                        CompoundTag entry = entries.getCompound(j);
                        if (entry.contains("Icon", Tag.TAG_COMPOUND)) {
                            CompoundTag icon = entry.getCompound("Icon");
                            itemStack = icon.getString("id");
                            count = entry.getInt("ItemAmount");
                            materials.add(new MaterialEntry(itemStack, count));
                        }
                    }
                }
            }
        }
    }
    public class MaterialEntry{
        public final String itemStack;
        public final int count;
        public MaterialEntry(String itemStack, int count){
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
