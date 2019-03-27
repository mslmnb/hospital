package com.epam.hospital.util;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CheckResult {
    private Set<String> details = new LinkedHashSet<>();

    public CheckResult() {
    }

    public CheckResult(String message) {
        this.addErrorMessage(message);
    }

    public Set<String> getDetails() {
        return details;
    }

    public void addErrorMessage(String msg) {
        details.add(msg);
    }

    public void addErrorMessage(CheckResult checkResult) {
        checkResult.getDetails().stream().forEach(d->addErrorMessage(d));
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
