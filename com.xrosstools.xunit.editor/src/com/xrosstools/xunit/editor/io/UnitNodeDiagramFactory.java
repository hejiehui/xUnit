package com.xrosstools.xunit.editor.io;

import org.w3c.dom.Document;

import com.xrosstools.xunit.editor.model.UnitConstants;
import com.xrosstools.xunit.editor.model.UnitNodeDiagram;

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
 *          <parallel_branch name type class>
 *              <dispatcher/>
 *              <branch_unit key task_type/>
 *                  <unit/>
 *          </parallel_branch>
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
}
