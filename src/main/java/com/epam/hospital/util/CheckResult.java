package com.epam.hospital.util;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CheckResult {
    private Set<String> details = new HashSet<>();

    public CheckResult() {
    }

    public void addErrorMessage(String msg) {
        details.add(msg);
    }

    public boolean foundErrors() {
        return !details.isEmpty();
    }

    public String getJsonString() {
        return "{ " +
                "\"details\": [" +
                details.stream().map(s->"\"" + s + "\"").collect(Collectors.joining(",")) +
                "]}";
    }
}
