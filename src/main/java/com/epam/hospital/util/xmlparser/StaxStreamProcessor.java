package com.epam.hospital.util.xmlparser;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.InputStream;

public class StaxStreamProcessor implements AutoCloseable {
    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private final XMLEventReader reader;

    public StaxStreamProcessor(InputStream inputStream) throws XMLStreamException {
        this.reader = FACTORY.createXMLEventReader(inputStream);
    }

    @Override
    public void close() throws XMLStreamException {
        if (reader != null) {
            reader.close();
        }
    }

    public XMLEvent getNextEvent() throws XMLStreamException {
        XMLEvent result = null;
        while (reader.hasNext()) {
            result = reader.nextEvent();
            break;
        }
        return result;
    }

    public String getText() throws XMLStreamException {
        return reader.getElementText();
    }

}
