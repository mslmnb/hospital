package com.epam.hospital.util;

import java.util.Arrays;

/**
 * Prefix type uses in {@code Action} classes.
 * The {@code Action} class processes the client request and return the view with prefix.
 * The prefix defines as view will be transferred to the client.
 */
public enum ViewPrefixType {
    JSON_VIEW_PREFIX("json:"),
    JSP_VIEW_PREFIX("jsp:"),
    REDIRECT_VIEW_PREFIX("redirect:"),
    FORWARD_VIEW_PREFIX("forward:");

    private final String prefix;

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
