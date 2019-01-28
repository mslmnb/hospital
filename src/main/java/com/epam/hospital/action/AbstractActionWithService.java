package com.epam.hospital.action;

public abstract class AbstractActionWithService extends AbstractAction {
    private String contextParameterForService;

    public AbstractActionWithService(String uri, String contextParameterForService) {
        super(uri);
        this.contextParameterForService = contextParameterForService;
    }

    public String getContextParameterForService() {
        return contextParameterForService;
    }
}
