package es.iesfranciscodelosrios.petadopt.dataAccess;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class  XMLManager {
    public static <T> boolean writeXML(T t, String fileName){
        boolean result = false;
        try {
            JAXBContext context = JAXBContext.newInstance(t.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File f = new File(fileName);
            marshaller.marshal(t,f);
            result=true;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static <T> T readXML(T t, String fileName){
        T result = t;
        try {
            JAXBContext context = JAXBContext.newInstance(t.getClass());
            Unmarshaller unmarshaller = context.createUnmarshaller();
            result = (T) unmarshaller.unmarshal(new File (fileName));

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

