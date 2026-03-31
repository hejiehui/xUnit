package com.xrosstools.xunit.idea.editor.io;


import com.xrosstools.xunit.idea.editor.model.UnitConstants;
import com.xrosstools.xunit.idea.editor.model.UnitNodeDiagram;
import org.w3c.dom.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;

/**
 * <xunit packageId name>
 * 		<description>...</description>
 * 		<imports>
 * 			<import packageId, name>
 * 		</imports>
 * 		<units>
 * 			<unit name type class>
 * 			<validator name type class validLabel invalidLabel>
 * 			<locator name type class>
 * 			<chain name type class>
 * 				any unit
 * 			</chain>
 * 			<bi_branch name type class>
 * 				<validator/>
 * 				<validUnit/>
 * 				<invalidUnit/>
 * 			</bi_branch>
 * 			<branch name type class>
 * 				<locator/>
 * 				<branch_unit key/>
 * 					<unit/>
 * 			</branch>
 * 			<while name type class>
 * 				<validator/>
 * 				<loop_unit/>
 * 			</while>
 * 			<do_while name type class>
 * 				<validator/>
 * 				<loop_unit/>
 * 			</do_while>
 * 		</units>
 * </xunit>
 * 
 */

public class UnitNodeDiagramFactory implements UnitConstants {
	public UnitNodeDiagram getEmptyDiagram(){
		return new UnitNodeDiagram();
	}
	private UnitNodeDiagramReader reader = new UnitNodeDiagramReader();
	private UnitNodeDiagramWriter writer = new UnitNodeDiagramWriter();
	public UnitNodeDiagram getFromDocument(Document doc){
		return reader.getFromDocument(doc);
	}

	public Document writeToDocument(UnitNodeDiagram model){
		return writer.writeToDocument(model);
	}

	public static String format(Document doc) throws Exception {
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	TransformerFactory tFactory = TransformerFactory.newInstance();
    	Transformer transformer = tFactory.newTransformer();
    	DOMSource source = new DOMSource(doc);
    	StreamResult result = new StreamResult(out);
    	transformer.transform(source, result);

    	// To make well formated document
    	SAXReader reader = new SAXReader();
    	org.dom4j.Document document = reader.read(new ByteArrayInputStream(out.toByteArray()));

    	XMLWriter writer = null;
        StringWriter stringWriter = new StringWriter();
        OutputFormat format = new OutputFormat(" ", true);
        writer = new XMLWriter(stringWriter, format);
        writer.write(document);
        writer.flush();
        return stringWriter.toString();
    }
}
