package org.catdou.param.generate.core.xml;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.catdou.validate.log.ValidatorLog;
import org.catdou.validate.log.ValidatorLogFactory;
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * @author James
 */
public class XmlTransformer {
    private static final ValidatorLog LOG = ValidatorLogFactory.getLogger(XmlTransformer.class);

    private static final TransformerFactory transformerFactory = TransformerFactory.newInstance();

    public static void transFormDocument(Document document, String filePath) {
        try {
            Transformer tf = transformerFactory.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty(OutputKeys.STANDALONE, "yes");
            tf.transform(new DOMSource(document), new StreamResult(new File(filePath)));
        } catch (TransformerException e) {
            LOG.error("transformer document exception " + ExceptionUtils.getStackTrace(e));
        }
    }
}
