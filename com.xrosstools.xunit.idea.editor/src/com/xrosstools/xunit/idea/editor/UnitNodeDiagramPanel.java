package com.xrosstools.xunit.idea.editor;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileListener;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.intellij.ui.treeStructure.Tree;
import com.xrosstools.xunit.idea.editor.actions.*;
import com.xrosstools.xunit.idea.editor.commands.Command;
import com.xrosstools.xunit.idea.editor.commands.CommandStack;
import com.xrosstools.xunit.idea.editor.commands.DeleteNodeCommand;
import com.xrosstools.xunit.idea.editor.commands.SetPropertyValueCommand;
import com.xrosstools.xunit.idea.editor.figures.Connection;
import com.xrosstools.xunit.idea.editor.figures.Figure;
import com.xrosstools.xunit.idea.editor.figures.UnitNodeContainerFigure;
import com.xrosstools.xunit.idea.editor.figures.UnitNodeDiagramFigure;
import com.xrosstools.xunit.idea.editor.io.UnitNodeDiagramFactory;
import com.xrosstools.xunit.idea.editor.model.*;
import com.xrosstools.xunit.idea.editor.parts.EditContext;
import com.xrosstools.xunit.idea.editor.parts.EditPart;
import com.xrosstools.xunit.idea.editor.parts.UnitNodePartFactory;
import com.xrosstools.xunit.idea.editor.policies.UnitNodeContainerLayoutPolicy;
import com.xrosstools.xunit.idea.editor.policies.UnitNodeLayoutPolicy;
import com.xrosstools.xunit.idea.editor.treeparts.TreeEditPart;
import com.xrosstools.xunit.idea.editor.treeparts.UnitNodeTreePartFactory;
import com.xrosstools.xunit.idea.editor.util.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

//TODO make it factoryinstead of subclass of JPAnel
public class UnitNodeDiagramPanel extends JPanel implements PropertyChangeListener, UnitConstants {
    private Project project;
    private VirtualFile virtualFile;

    private boolean showed;
    private JBSplitter mainPane;
    private JBSplitter diagramPane;
    private Tree treeNavigator;
    private JBTable tableProperties;

    private UnitNodeDiagramFactory factory = new UnitNodeDiagramFactory();
    private UnitNodeDiagram diagram;

    private JScrollPane innerDiagramPane;
    private UnitPanel unitPanel;
    private EditPart root;
    private TreeEditPart treeRoot;

    private Figure lastSelected;
    private Figure lastHover;
    private UnitNode newUnitNode;

    private UnitNodeContainerLayoutPolicy unitNodeContainerLayoutPolicy = new UnitNodeContainerLayoutPolicy();
    private UnitNodeLayoutPolicy nodeLayoutPolicy = new UnitNodeLayoutPolicy();

    private CommandStack commandStack = new CommandStack();
    private boolean inProcessing;

    public UnitNodeDiagramPanel(Project project, VirtualFile virtualFile) throws Exception {
        this.project = project;
        this.virtualFile = virtualFile;
        diagram = factory.getFromDocument(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(virtualFile.getInputStream()));
        diagram.setProject(project);
        diagram.setFilePath(virtualFile);
        createVisual();
        registerListener();
        build();
    }

    private void createVisual() {
        setLayout(new BorderLayout());
        mainPane = new JBSplitter(true, 0.8f);
        mainPane.setDividerWidth(3);
        add(mainPane, BorderLayout.CENTER);

        mainPane.setFirstComponent(createMain());
        mainPane.setSecondComponent(createProperty());
    }

    private JComponent createMain() {
        diagramPane = new JBSplitter(false, 0.8f);
        diagramPane.setDividerWidth(3);

        diagramPane.setFirstComponent(createButtons());
        diagramPane.setSecondComponent(createTree());

        return diagramPane;
    }

    private JComponent createButtons() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel toolbar = new JPanel();
        GridLayout layout = new GridLayout(0, 1, 10,0);
        toolbar.setLayout(layout);
        toolbar.add(createResetButton());
        toolbar.add(createButton("Processor", XrossUnitIcons.Processor, ProcessorNode.class));
        toolbar.add(createButton("Converter", XrossUnitIcons.Converter, ConverterNode.class));
        toolbar.add(createButton("Validator", XrossUnitIcons.Validator, ValidatorNode.class));
        toolbar.add(createButton("Locator", XrossUnitIcons.Locator, LocatorNode.class));
        toolbar.add(createButton("Dispatcher", XrossUnitIcons.Dispatcher, DispatcherNode.class));
        toolbar.add(createButton("Chain", XrossUnitIcons.Chain, ChainNode.class));
        toolbar.add(createButton("If/else", XrossUnitIcons.Bi_branch, BiBranchNode.class));
        toolbar.add(createButton("Branch", XrossUnitIcons.Branch, BranchNode.class));
        toolbar.add(createButton("Parallel Branch", XrossUnitIcons.Parallel_branch, ParallelBranchNode.class));
        toolbar.add(createButton("While loop", XrossUnitIcons.While_loop, PreValidationLoopNode.class));
        toolbar.add(createButton("Do while loop", XrossUnitIcons.Do_while_loop, PostValidationLoopNode.class));
        toolbar.add(createButton("Start", XrossUnitIcons.Start_point, StartPointNode.class));
        toolbar.add(createButton("End", XrossUnitIcons.End_point, EndPointNode.class));
        toolbar.add(createButton("Decorator", XrossUnitIcons.Decorator, DecoratorNode.class));
        toolbar.add(createButton("Adapter", XrossUnitIcons.Adapter, AdapterNode.class));

        //Add code gen
        toolbar.add(createPaletteButton(new GenerateHelperAction(project, virtualFile, diagram), XrossUnitIcons.GENERATE_HLPER, GENERATE_HELPER));
        toolbar.add(createPaletteButton(new GenerateTestAction(diagram), XrossUnitIcons.GENERATE_TEST, GENERATE_TEST));

        mainPanel.add(toolbar, BorderLayout.WEST);

        unitPanel = new UnitPanel();
        innerDiagramPane = new JBScrollPane(unitPanel);
        innerDiagramPane.setLayout(new ScrollPaneLayout());
        innerDiagramPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        innerDiagramPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        innerDiagramPane.getVerticalScrollBar().setUnitIncrement(50);

        mainPanel.add(innerDiagramPane, BorderLayout.CENTER);
        mainPanel.add(createToolbar(),  BorderLayout.NORTH);

        return mainPanel;
    }

    private JComponent createTree() {
        treeNavigator = new Tree();
        treeNavigator.setExpandsSelectedPaths(true);

        JScrollPane treePane = new JBScrollPane(treeNavigator);
        treePane.setLayout(new ScrollPaneLayout());
        treePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        treePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        treePane.getVerticalScrollBar().setUnitIncrement(50);

        return treePane;
    }

    private JComponent createProperty() {
        tableProperties = new JBTable();
        PropertyTableModel model = new PropertyTableModel(diagram, this);
        setModel(model);

        JScrollPane scrollPane = new JBScrollPane(tableProperties);
        scrollPane.setLayout(new ScrollPaneLayout());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(50);

        return scrollPane;
    }

    private JComponent createToolbar() {
        ActionManager actionManager = ActionManager.getInstance();
        DefaultActionGroup actionGroup = new DefaultActionGroup();

        actionGroup.add(new AnAction("Undo", "Undo", XrossUnitIcons.Do_while_loop) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
                undo();
            }

            @Override
            public void update(AnActionEvent e) {
                super.update(e);
                Presentation presentation = e.getPresentation();
                presentation.setEnabled(commandStack.canUndo());
                if(presentation.isEnabled())
                    presentation.setText("Undo " + commandStack.getUndoCommandLabel());
            }
        });

        actionGroup.add(new AnAction("Redo", "Redo", XrossUnitIcons.While_loop) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
                redo();
            }

            @Override
            public void update(AnActionEvent e) {
                super.update(e);
                Presentation presentation = e.getPresentation();
                presentation.setEnabled(commandStack.canRedo());
                if(presentation.isEnabled())
                    presentation.setText("Redo " + commandStack.getRedoCommandLabel());
            }
        });

        ActionToolbar toolbar = actionManager.createActionToolbar("XrossToolsToolbar", actionGroup, true);
        return toolbar.getComponent();
    }

    private void save() {
        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {
                try {
                    String contentStr = XmlHelper.format(factory.writeToDocument(diagram));
                    virtualFile.setBinaryContent(contentStr.getBytes(virtualFile.getCharset()));
                } catch (Throwable e) {
                    throw new IllegalStateException("Can not save document " + virtualFile.getName(), e);
                }
            }
        });
    }

    private void build() {
        EditContext context = new EditContext(this);

        UnitNodePartFactory f = new UnitNodePartFactory(context);
        root = f.createEditPart(null, diagram);
        root.build();

        UnitNodeTreePartFactory treePartFactory = new UnitNodeTreePartFactory(context);
        treeRoot = treePartFactory.createEditPart(null, diagram);
        treeNavigator.setModel(new DefaultTreeModel(treeRoot.build(), false));
        treeNavigator.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                if (inProcessing)
                    return;

                selectedNode();
            }
        });
        treeNavigator.setCellRenderer(new DefaultTreeCellRenderer(){
            public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                          boolean sel, boolean expanded, boolean leaf, int row,
                                                          boolean hasFocus){
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                TreeEditPart treePart = (TreeEditPart)((DefaultMutableTreeNode)value).getUserObject();
                setText(treePart.getText());
                setIcon(treePart.getImage());
                return this;
            }
        });

        updateVisual();
    }

    public void rebuild() {
        build();
    }

    public void contentsChanged() {
        if(inProcessing)
            return;

        try {
            virtualFile.refresh(false, true);
            diagram = factory.getFromDocument(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(virtualFile.getInputStream()));
            diagram.setProject(project);
            diagram.setFilePath(virtualFile);
            rebuild();
            selectUnit(diagram);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Figure selectFigureAt(int x, int y) {
        Figure rootFigure = root.getFigure();
        Figure selected = rootFigure.selectFigureAt(x, y);
        return selected == null ? rootFigure : selected;
    }

    public void selectUnit(String name) {
        if (name == null)
            return;

        UnitNode referencedNode = null;
        for (UnitNode node : diagram.getUnits()) {
            if (node.getName().equals(name)) {
                referencedNode = node;
                break;
            }
        }

        if (referencedNode == null)
            return;

        selectUnit(referencedNode);
    }

    private void selectUnit(Object selectedNode) {
        Figure selected = root.getContext().findFigure(selectedNode);

        if(selected == null) {
            selectedFigure(root.getFigure());
            return;
        }

        selectedFigure(selected);

        TreeEditPart treePart= treeRoot.findEditPart(selectedNode);
        DefaultMutableTreeNode treeNode = treePart == null ? null : treePart.getTreeNode();
        if (treeNode != null)
            treeNavigator.setSelectionPath(new TreePath(treeNode.getPath()));

        if(selectedNode instanceof UnitNodeDiagram)
            return;

        adjust(innerDiagramPane.getVerticalScrollBar(), lastSelected.getY(), lastSelected.getHeight());
        adjust(innerDiagramPane.getHorizontalScrollBar(), lastSelected.getX(), lastSelected.getWidth());

    }

    private void updateHover(MouseEvent e) {
        Figure f = root.getFigure().findFigureAt(e.getX(), e.getY());
        f = f == null ? root.getFigure() : f;

        if(lastHover != null && lastHover != f)
            lastHover.setInsertionPoint(null);

        if(f instanceof UnitNodeDiagramFigure || f instanceof UnitNodeContainerFigure)
            f.setInsertionPoint(e.getPoint());

        unitPanel.repaint();
        lastHover = f;
    }

    private void clearHover() {
        if(lastHover != null) {
            lastHover.setInsertionPoint(null);
            unitPanel.repaint();
            lastHover = null;
        }
    }

    private void registerListener() {
        unitPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(lastSelected != null && !(lastSelected instanceof UnitNodeDiagramFigure))
                    updateHover(e);
                else
                    clearHover();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Figure f = selectFigureAt(e.getX(), e.getY());
                if(f == null || f == root.getFigure())
                    unitPanel.setToolTipText(null);
                else
                    unitPanel.setToolTipText(f.getToolTipText());


                if(newUnitNode != null)
                    updateHover(e);
                else
                    clearHover();
            }
        });

        unitPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Figure f = selectFigureAt(e.getX(), e.getY());
                    Object obj = f.getPart().getModel();
                    if(obj == null || !(obj instanceof UnitNode))
                        return;

                    OpenClassAction.openClassOrReference(project, ((UnitNode)obj));
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(newUnitNode != null)
                    return;

                Figure f = selectFigureAt(e.getX(), e.getY());
                selectedFigure(f);

                if(f != null && !(f instanceof Connection)) {
                    DefaultMutableTreeNode treeNode = treeRoot.findEditPart(f.getPart().getModel()).getTreeNode();
                    if (treeNode != null)
                        treeNavigator.setSelectionPath(new TreePath(treeNode.getPath()));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(lastHover != null)
                    lastHover.setInsertionPoint(null);

                // Create new node
                if (newUnitNode != null) {
                    updateHover(e);
                    if (lastHover != null) {
                        if (lastHover instanceof UnitNodeDiagramFigure || lastHover instanceof UnitNodeContainerFigure)
                            update(unitNodeContainerLayoutPolicy.getCreateCommand(lastHover, newUnitNode));
                        else
                            update(nodeLayoutPolicy.getCreateCommand(lastHover.getPart(), newUnitNode));
                    }
                    return;
                }

                // Drag and drop
                // Drag to itself or child is not allowed
                if (lastSelected != null && lastHover != null && lastSelected != lastHover && !lastSelected.containsPoint(e.getX(), e.getY())) {
                    updateHover(e);
                    if (lastHover instanceof UnitNodeDiagramFigure || lastHover instanceof UnitNodeContainerFigure) {
                        // In same parent
                        if(lastHover.getPart() == lastSelected.getPart().getParent())
                            update(unitNodeContainerLayoutPolicy.createMoveChildCommand(lastHover, lastSelected.getPart()));
                        else
                            update(unitNodeContainerLayoutPolicy.createAddCommand(lastHover, lastSelected.getPart()));
                    } else
                        update(nodeLayoutPolicy.getAddCommand(lastHover.getPart(), lastSelected.getPart()));
                    return;
                }

                if (isPopupTrigger(e))
                    showContexMenu(e.getX(), e.getY());
            }
        });

        unitPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(lastSelected == null)
                    return;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_DELETE :
                        if (lastSelected.getPart().getModel() instanceof UnitNode)
                            update(new DeleteNodeCommand(lastSelected.getPart().getParent().getModel(), (UnitNode)lastSelected.getPart().getModel()));
                }
            }
        });
    }

    private void setModel(PropertyTableModel model){
        tableProperties.setModel(model);
        tableProperties.setDefaultRenderer(Object.class, new SimpleTableRenderer(model));
        tableProperties.getColumnModel().getColumn(1).setCellEditor(new SimpleTableCellEditor(model));
    }

    private void selectedFigure(Figure selected) {
        if(lastSelected != null && lastSelected != selected)
            lastSelected.setSelected(false);

        if(selected != null) {
            lastSelected = selected;
            lastSelected.setSelected(true);
        }

        refresh();
    }

    private void selectedNode() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)treeNavigator.getLastSelectedPathComponent();
        if(node == null)
            return;

        TreeEditPart treePart = (TreeEditPart)node.getUserObject();
        selectUnit(treePart.getModel());
    }

    private void adjust(JScrollBar scrollBar, int start, int length ) {
        if (scrollBar.getValue() > start || scrollBar.getValue() + scrollBar.getVisibleAmount() < start + length)
            scrollBar.setValue(start - 100);
    }

    private boolean isPopupTrigger(MouseEvent evt) {
        return evt.isPopupTrigger() || evt.getButton() == MouseEvent.BUTTON3;
    }

    private void showContexMenu(int x, int y) {
        UnitContextMenuProvider builder = new UnitContextMenuProvider(this);
        builder.buildContextMenu(project, diagram, lastSelected.getPart()).show(unitPanel, x, y);
    }

    private void reset() {
        inProcessing = false;

        if(lastSelected != null) {
            lastSelected.setSelected(false);
            lastSelected = null;
        }

        newUnitNode = null;
        refresh();
    }

    public void refresh() {
        updateVisual();
        unitPanel.grabFocus();
    }

    private JButton createResetButton() {
        JButton btn = new JButton("Select", AllIcons.Actions.Back);
        btn.setContentAreaFilled(false);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        return btn;
    }

    private JButton createButton(String name, Icon icon, final Class unitNodeClass) {
        JButton btn = new JButton(name, icon);
        btn.setContentAreaFilled(false);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    newUnitNode = (UnitNode) unitNodeClass.newInstance();
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        });
        return btn;
    }
    public JButton createPaletteButton(ActionListener action, Icon icon, String tooltip) {
        JButton btn = new JButton(tooltip, icon);
        btn.setContentAreaFilled(false);
        btn.addActionListener(action);
        return btn;
    }

    private void update(Command command) {
        if(command == null)
            return;

        Object model = newUnitNode;
        if(model == null)
            model = lastSelected == null ? null : lastSelected.getPart().getModel();

        inProcessing = true;
        commandStack.execute(command, model);

        if(newUnitNode != null)
            newUnitNode = null;

        postExecute(model);
    }

    private void postExecute(Object model) {
        rebuild();
        save();
        selectUnit(model);
        inProcessing = false;
        refresh();
    }

    private void undo() {
        inProcessing = true;
        commandStack.undo();
        postExecute(commandStack.getCurModel());
    }

    private void redo() {
        inProcessing = true;
        commandStack.redo();
        postExecute(commandStack.getCurModel());
    }

    private void updateVisual() {
        if(inProcessing)
            return;

        if(lastSelected!=null) {
          PropertyTableModel model = new PropertyTableModel((IPropertySource) lastSelected.getPart().getModel(), this);
          setModel(model);
        }


        int height = unitPanel.getPreferredSize().height;
        int width = unitPanel.getPreferredSize().width;
        innerDiagramPane.getVerticalScrollBar().setMaximum(height);
        innerDiagramPane.getHorizontalScrollBar().setMaximum(width);

        if(lastSelected != null) {
            Figure selected = lastSelected;
            if(lastSelected instanceof Connection)
                selected = lastSelected.getPart().findFigure(((UnitNodeConnection)lastSelected.getPart().getModel()).getSource());

            if(selected != root.getFigure()) {
                adjust(innerDiagramPane.getVerticalScrollBar(), selected.getY(), selected.getHeight());
                adjust(innerDiagramPane.getHorizontalScrollBar(), selected.getX(), selected.getWidth());
            }
        }

        repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getSource() instanceof WorkbenchPartAction) {
            update((Command) evt.getNewValue());
        }else if (evt.getSource() instanceof IPropertySource) {
            update(new SetPropertyValueCommand(evt));
        }
    }

    private class UnitPanel extends JPanel {
        @Override
        protected void paintChildren(Graphics g) {
            root.getFigure().paint(g);
        }

        @Override
        public Dimension getPreferredSize() {
            if(root == null)
                return new Dimension(500,800);

            Dimension size = root.getFigure().getPreferredSize();
            root.getFigure().layout();
            size.height+=100;
            return size;
        }
    }
}
