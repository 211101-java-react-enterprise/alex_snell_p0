package com.revature.app.util.strings;

import com.revature.app.util.types.Labeled;
import java.util.HashMap;
import java.util.Map;

public class Color {


    public enum ANSI implements Labeled {

        RESET("\u001b[0m"),
        RED("\u001b[31m"),
        GREEN("\u001b[32m"),
        YELLOW("\u001b[33m"),
        BLUE("\u001b[34m"),
        MAGENTA("\u001b[35m"),
        CYAN("\u001b[36m"),
        WHITE("\u001b[371m");

        public final String label;

        ANSI(String code) {
            this.label = code;
        }

        private static final Map<String, ANSI> COLOR_CODE = new HashMap<>();

        static {
            for (ANSI a: ANSI.values()) {
                COLOR_CODE.put(a.label, a);
            }
        }

        public static ANSI valueOfLabel(String label) {
            return ANSI.COLOR_CODE.get(label);
        }

        @Override
        public String label() {
            return this.label;
        }

        @Override
        public String toString() {
            return this.label;
        }
    }

    public static String println(ANSI color, String... infixStrings) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(color.label());
        for (String infixString : infixStrings) {
            strBuilder.append(infixString);
        }
        strBuilder.append(ANSI.RESET.label());
        return strBuilder.toString();
    }

    private static String getCode(ANSI color) {
        return color.label();
    }
}
