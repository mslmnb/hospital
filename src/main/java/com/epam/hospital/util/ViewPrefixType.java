package com.epam.hospital.util;

import java.util.Arrays;

public enum ViewPrefixType {
    JSON_VIEW_PREFIX("json:"),
    JSP_VIEW_PREFIX("jsp:"),
    REDIRECT_VIEW_PREFIX("redirect:"),
    FORWARD_VIEW_PREFIX("forward:");

    String prefix;

    ViewPrefixType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public static ViewPrefixType getValueByPrefix(String prefix) {
        return Arrays.stream(values()).filter(v -> v.getPrefix().equals(prefix))
                               .findFirst()
                               .orElse(null);
    }
}
