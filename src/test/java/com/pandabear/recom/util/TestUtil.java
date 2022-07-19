package com.pandabear.recom.util;

import java.util.Random;

public class TestUtil {

    private TestUtil() {}

    public static String randomString(int limit) {
        return new Random().ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >=97))
                .limit(limit)
                .collect(
                        StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append
                ).toString();
    }
}
