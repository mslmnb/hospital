package com.epam.hospital.web.action;

public abstract class AbstractActionWithController extends AbstractAction {
    private String controllerXmlId;

    public AbstractActionWithController(String uri, String controllerXmlId) {
        super(uri);
        this.controllerXmlId = controllerXmlId;
    }

    public String getControllerXmlId() {
        return controllerXmlId;
    }


}
