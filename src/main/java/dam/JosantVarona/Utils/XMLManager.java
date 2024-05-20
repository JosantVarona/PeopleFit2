package dam.JosantVarona.Utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLManager {
    /**
     * Writes an object to an XML file.
     *
     * @param <T> The type of the object to be written.
     * @param c The object to be written to the XML file.
     * @param filename The name of the file where the object will be written.
     * @return true if the object was successfully written to the file, false otherwise.
     */
    public static <T> boolean writeXML(T c, String filename){
        boolean result= false;
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(c.getClass());
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            m.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");
            m.marshal(c,new File(filename));
            result=true;
        }catch (JAXBException e){
            e.printStackTrace();
        }
        return result;
    }
    /**
     * Reads an object from an XML file.
     *
     * @param <T> The type of the object to be read.
     * @param c The object to be used as a template for the type.
     * @param filename The name of the file from which the object will be read.
     * @return The object read from the XML file, or the provided template object if an error occurs.
     */
    public static<T> T readXML(T c,String filename){
        T result = c;
        JAXBContext context;

        try{
            context = JAXBContext.newInstance(c.getClass());
            Unmarshaller um = context.createUnmarshaller();
            result = (T) um.unmarshal(new File(filename));
        }catch (JAXBException e){
            e.printStackTrace();
        }
        return result;
    }

}
