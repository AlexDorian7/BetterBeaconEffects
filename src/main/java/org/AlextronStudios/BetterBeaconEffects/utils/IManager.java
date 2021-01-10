package org.AlextronStudios.BetterBeaconEffects.utils;

import java.util.List;

public interface IManager<T> {
    List<T> getList();
    void register(T value);
}
