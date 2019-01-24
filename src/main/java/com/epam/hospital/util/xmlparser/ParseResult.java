package com.epam.hospital.util.xmlparser;

public class ParseResult {
    private String className;
    private String constructorParameter;

    public ParseResult(String className, String constructorParameter) {
        this.className = className;
        this.constructorParameter = constructorParameter;
    }

    public String getClassName() {
        return className;
    }

    public String getConstructorParameter() {
        return constructorParameter;
    }
}
