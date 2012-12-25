package com.xross.tools.xunit.editor.io;

import org.w3c.dom.Document;

import com.xross.tools.xunit.editor.model.UnitConstants;
import com.xross.tools.xunit.editor.model.UnitNodeDiagram;

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

public class UnitNodeDiagramFactory implements UnitConstants{
	public UnitNodeDiagram getEmptyDiagram(){
		UnitNodeDiagram pkg = new UnitNodeDiagram();
		pkg.getConfigure().addCategory(DEFAULT_CATEGORY);
		return pkg;
	}
	private UnitNodeDiagramReader reader = new UnitNodeDiagramReader();
	private UnitNodeDiagramWriter writer = new UnitNodeDiagramWriter();
	public UnitNodeDiagram getFromDocument(Document doc){
		return reader.getFromDocument(doc);
	}
	
	public Document writeToDocument(UnitNodeDiagram model){
		return writer.writeToDocument(model);
	}
}
