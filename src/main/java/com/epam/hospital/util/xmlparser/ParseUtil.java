package com.epam.hospital.util.xmlparser;

import com.epam.hospital.repository.ConnectionPool;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ParseUtil {
    public static final String GET_INSTANCE_METHOD = "getInstance";
    private static final String REPOSITORY = "repository";
    private static final String CONTROLLER = "controller";
    private static final String SERVICE = "service";
    private static final String ID = "id";
    private static final String CLASS_NAME = "class-name";

    /**
     * Return map of controllers. Controller puts in map as element of Object.class
     *
     * @param  xmlFileName the name of xml-file with descriptions of repositories, services and controllers
     * @param  connectionPool the database connection pool
     *
     * @return map of controllers or null if the exception was thrown
     */
    public static Map<String, Object> getControllers(String xmlFileName, ConnectionPool connectionPool) {
        Map<String, Map<String, ParseResult>> parseResults = new HashMap<>();
        parseResults.put(REPOSITORY, new HashMap<>());
        parseResults.put(SERVICE, new HashMap<>());
        parseResults.put(CONTROLLER, new HashMap<>());

        InputStream inputStream = new ParseUtil().getClass()
                .getClassLoader()
                .getResourceAsStream(xmlFileName);

        Map<String, Object> resultMap = null;
        try (StaxStreamProcessor processor = new StaxStreamProcessor(inputStream)) {
            XMLEvent event = processor.getNextEvent();
            String key = null;
            String attributeName = "";
            String className = "";
            while (event != null) {
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    switch (startElement.getName().getLocalPart()) {
                        case REPOSITORY:
                            key = startElement.getAttributeByName(new QName(ID)).getValue();
                            attributeName = "";
                            break;
                        case SERVICE:
                            key = startElement.getAttributeByName(new QName(ID)).getValue();
                            attributeName = startElement.getAttributeByName(new QName(REPOSITORY)).getValue();
                            break;
                        case CONTROLLER:
                            key = startElement.getAttributeByName(new QName(ID)).getValue();
                            attributeName = startElement.getAttributeByName(new QName(SERVICE)).getValue();
                            break;
                        case CLASS_NAME:
                            className = processor.getNextEvent().asCharacters().getData();
                            break;
                        default:
                            break;
                    }
                }
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    switch (endElement.getName().getLocalPart()) {
                        case REPOSITORY:
                            parseResults.get(REPOSITORY).put(key, new ParseResult(className, attributeName));
                            break;
                        case SERVICE:
                            parseResults.get(SERVICE).put(key, new ParseResult(className, attributeName));
                            break;
                        case CONTROLLER:
                            parseResults.get(CONTROLLER).put(key, new ParseResult(className, attributeName));
                            break;
                        default:
                            break;
                    }
                }
                event = processor.getNextEvent();
            }
            resultMap = getControllers(parseResults, connectionPool);
        } catch (XMLStreamException e) {
            // logger.error(e.getClass().getName() + " was thrown at parsing of xml-file:" + xmlFileName)
            resultMap = null;
        }
        return resultMap;
    }

    /**
     * Return map of controllers. Controller puts in map as element of Object.class
     *
     * @param  parseResults the map of ParseResult objects
     * @param  connectionPool the database connection pool
     *
     * @return map of controllers or null if the exception was thrown
     */
    private static Map<String, Object> getControllers(Map<String, Map<String, ParseResult>> parseResults,
                                                      ConnectionPool connectionPool) {
        Map<String, Object> repositories = new HashMap<>();
        Map<String, Object> resultMap = null;
        for (Map.Entry<String, ParseResult> pair : parseResults.get(REPOSITORY).entrySet()) {
            String className = pair.getValue().getClassName();
            try {
                Class repositoryClass = Class.forName(className);
                Object repository = repositoryClass.getConstructor(ConnectionPool.class).newInstance(connectionPool);
                repositories.put(pair.getKey(), repository);
            } catch (IllegalAccessException | InvocationTargetException |
                    NoSuchMethodException | ClassNotFoundException | InstantiationException e) {
                // logger.error(e.getClass().getName() + " was thrown at initialization of an object of the class:" + className)
                repositories = null;
                break;
            }
        }
        if (repositories != null) {
            Map<String, Object> services = getObjects(parseResults.get(SERVICE), repositories);
            if (services != null) {
                resultMap = getObjects(parseResults.get(CONTROLLER), services);
            }
        }
        return resultMap;
    }

    /**
     * Returns map of object
     *
     * @param  parseResults the map of ParseResult objects
     * @param  constructorParameters the parameters for class constructor
     *
     * @return map of object or null if the exception was thrown
     */
    private static Map<String, Object> getObjects(Map<String, ParseResult> parseResults,
                                                  Map<String, Object> constructorParameters) {
        Map<String, Object> resultMap = new HashMap<>();
        for (Map.Entry<String, ParseResult> pair : parseResults.entrySet()) {
            String className = pair.getValue().getClassName();
            String constructorParameterName = pair.getValue().getConstructorParameter();
            Object constructorParameter;
            if (constructorParameters.containsKey(constructorParameterName)) {
                constructorParameter = constructorParameters.get(constructorParameterName);
            } else {
                // logger.error("Error of initialization constructor parameter for the class:" + className)
                resultMap = null;
                break;
            }
            try {
                Class objectClass = Class.forName(className);
                Method method = objectClass.getDeclaredMethod(GET_INSTANCE_METHOD, Object.class);
                method.setAccessible(true);
                Object object = method.invoke(null, constructorParameter);
                resultMap.put(pair.getKey(), object);
            } catch (IllegalAccessException | InvocationTargetException |
                    NoSuchMethodException | ClassNotFoundException e) {
                // logger.error(e.getClass().getName() + " was thrown at initialization of an object of the class:" + className)
                resultMap = null;
                break;
            }
        }
        return resultMap;
    }
}
