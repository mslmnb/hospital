package com.epam.hospital.util;

import com.epam.hospital.model.HavingJsonView;
import org.json.JSONObject;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The class represents a object which keeps error messages as result of check
 */
public class CheckResult implements HavingJsonView{
    private static final String DETAILS_PARAMETER = "details";

    /**
     * set for keeping error messages
     */
    private final Set<String> details = new LinkedHashSet<>();

    public CheckResult() {
    }

    public CheckResult(String message) {
        this.addErrorMessage(message);
    }

    public Set<String> getDetails() {
        return details;
    }

    /**
     * Adds the error message from specific string
     * @param msg the error message which need to be added
     */
    public void addErrorMessage(String msg) {
        details.add(msg);
    }

    /**
     * Adds all error messages from specific {@code CheckResult} object
     * @param checkResult {@code CheckResult} object which error messages to be added
     */
    public void addErrorMessage(CheckResult checkResult) {
        checkResult.getDetails().stream().forEach(d->addErrorMessage(d));
    }

    /**
     * Checks that errors were found
     * @return {@code true} if errors were found else
     *         {@code false}
     */
    public boolean foundErrors() {
        return !details.isEmpty();
    }

    @Override
    public String getJsonString() {
        return new JSONObject().put(DETAILS_PARAMETER, details).toString();
    }

}
