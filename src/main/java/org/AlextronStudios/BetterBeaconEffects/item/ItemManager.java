package org.AlextronStudios.BetterBeaconEffects.item;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.AlextronStudios.BetterBeaconEffects.utils.IInitializable;
import org.AlextronStudios.BetterBeaconEffects.utils.IManager;

import java.util.ArrayList;
import java.util.List;

public class ItemManager implements IInitializable, IManager<BetterItem> {

    private final List<BetterItem> list;
    private static ItemManager instance;

    public ItemManager() {
        instance = this;
        this.list = new ArrayList<>();
    }

    @Override
    public void initialize() {

    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {

    }

    @Override
    public List<BetterItem> getList() {
        return list;
    }

    @Override
    public void register(BetterItem value) {
        list.add(value);
    }

    public static ItemManager getInstance() {
        return instance;
    }
}
