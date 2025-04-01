package com.xrosstools.xunit.idea.editor;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.AbstractPanelContentProvider;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.idea.gef.parts.EditPartFactory;
import com.xrosstools.xunit.idea.editor.actions.GenerateFactoryAction;
import com.xrosstools.xunit.idea.editor.actions.GenerateTestAction;
import com.xrosstools.xunit.idea.editor.io.UnitNodeDiagramFactory;
import com.xrosstools.xunit.idea.editor.model.*;
import com.xrosstools.xunit.idea.editor.parts.UnitNodePartFactory;
import com.xrosstools.xunit.idea.editor.treeparts.UnitNodeTreePartFactory;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;

public class XrossUnitPanelContentProvider extends AbstractPanelContentProvider<UnitNodeDiagram> implements UnitConstants {
    private Project project;
    private VirtualFile virtualFile;
    private UnitNodeDiagram  diagram;

    private GenerateFactoryAction generateFactoryAction;
    private GenerateTestAction generateTestAction;
    private UnitContextMenuProvider contextMenuProvider;


    private UnitNodeDiagramFactory factory = new UnitNodeDiagramFactory();

    public XrossUnitPanelContentProvider(Project project, VirtualFile virtualFile) {
        super(virtualFile);
        this.project = project;
        this.virtualFile = virtualFile;

        generateFactoryAction = new GenerateFactoryAction(project, virtualFile);
        generateTestAction = new GenerateTestAction(project);
        contextMenuProvider = new UnitContextMenuProvider(project);
    }

    @Override
    public UnitNodeDiagram getContent() throws Exception {
        diagram = factory.getFromDocument(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(virtualFile.getInputStream()));
        diagram.setFilePath(virtualFile);
        diagram.setProject(project);

        //Make sure we always get the latest file
        generateFactoryAction.setDiagram(diagram);
        generateTestAction.setDiagram(diagram);
        contextMenuProvider.setDiagram(diagram);

        return diagram;
    }

    @Override
    public void saveContent() throws Exception {
        String contentStr = factory.format(factory.writeToDocument(diagram));
        virtualFile.setBinaryContent(contentStr.getBytes(virtualFile.getCharset()));
    }

    @Override
    public ContextMenuProvider getContextMenuProvider() {
        return contextMenuProvider;
    }

    private static Object[][] ENTRIES = new Object[][]{
            {"Processor", XrossUnitIcons.Processor, ProcessorNode.class},
            {"Converter", XrossUnitIcons.Converter, ConverterNode.class},
            {"Validator", XrossUnitIcons.Validator, ValidatorNode.class},
            {"Locator", XrossUnitIcons.Locator, LocatorNode.class},
            {"Dispatcher", XrossUnitIcons.Dispatcher, DispatcherNode.class},
            {"Chain", XrossUnitIcons.Chain, ChainNode.class},
            {"If/else", XrossUnitIcons.Bi_branch, BiBranchNode.class},
            {"Branch", XrossUnitIcons.Branch, BranchNode.class},
            {"Parallel Branch", XrossUnitIcons.Parallel_branch, ParallelBranchNode.class},
            {"While loop", XrossUnitIcons.While_loop, PreValidationLoopNode.class},
            {"Do while loop", XrossUnitIcons.Do_while_loop, PostValidationLoopNode.class},
            {"Start", XrossUnitIcons.Start_point, StartPointNode.class},
            {"End", XrossUnitIcons.End_point, EndPointNode.class},
            {"Decorator", XrossUnitIcons.Decorator, DecoratorNode.class},
            {"Adapter", XrossUnitIcons.Adapter, AdapterNode.class},
    };

    {
  //Add code gen
    }
    @Override
    public void buildPalette(JPanel palette) {
        for(Object[] entry: ENTRIES){
            palette.add(createNodeButton(entry));
        }

        palette.add(createPaletteButton(generateFactoryAction, XrossUnitIcons.GENERATE_HLPER, GENERATE_FACTORY));
        palette.add(createPaletteButton(generateTestAction, XrossUnitIcons.GENERATE_TEST, GENERATE_TEST));
    }

    private JButton createNodeButton(Object[] entry) {
        return createPaletteButton(e -> {
            try {
                createModel(((Class)entry[2]).newInstance());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }, (Icon)entry[1], (String)entry[0]);
    }

    @Override
    public EditPartFactory createEditPartFactory() {
        return new UnitNodePartFactory();
    }

    @Override
    public EditPartFactory createTreePartFactory() {
        return new UnitNodeTreePartFactory();
    }
}
