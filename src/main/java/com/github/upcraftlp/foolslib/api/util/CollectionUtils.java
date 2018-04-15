package com.github.upcraftlp.foolslib.api.util;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class CollectionUtils {

    private static final Random RANDOM = new Random();

    @Nullable
    public static <T> T getRandomElementFromList(List<T> list) {
        if(list.size() == 0) return null;
        return list.get(RANDOM.nextInt(list.size()));
    }
}
