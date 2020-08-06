package com.xrosstools.xunit.idea.editor;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.intellij.ui.treeStructure.Tree;
import com.xrosstools.xunit.idea.editor.actions.OpenClassAction;
import com.xrosstools.xunit.idea.editor.commands.DeleteNodeCommand;
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
        toolbar.add(createButton("Processor", Activator.PROCESSOR, ProcessorNode.class));
        toolbar.add(createButton("Converter", Activator.CONVERTER, ConverterNode.class));
        toolbar.add(createButton("Validator", Activator.VALIDATOR, ValidatorNode.class));
        toolbar.add(createButton("Locator", Activator.LOCATOR, LocatorNode.class));
        toolbar.add(createButton("Dispatcher", Activator.DISPATCHER, DispatcherNode.class));
        toolbar.add(createButton("Chain", Activator.CHAIN, ChainNode.class));
        toolbar.add(createButton("If/else", Activator.BI_BRANCH, BiBranchNode.class));
        toolbar.add(createButton("Branch", Activator.BRANCH, BranchNode.class));
        toolbar.add(createButton("Parallel Branch", Activator.PARALLEL_BRANCH, ParallelBranchNode.class));
        toolbar.add(createButton("While loop", Activator.WHILE, PreValidationLoopNode.class));
        toolbar.add(createButton("Do while loop", Activator.DO_WHILE, PostValidationLoopNode.class));
        toolbar.add(createButton("Start", Activator.START_POINT, StartPointNode.class));
        toolbar.add(createButton("End", Activator.END_POINT, EndPointNode.class));
        toolbar.add(createButton("Decorator", Activator.DECORATOR, DecoratorNode.class));
        toolbar.add(createButton("Adapter", Activator.ADAPTER, AdapterNode.class));
        mainPanel.add(toolbar, BorderLayout.WEST);

        unitPanel = new UnitPanel();
        innerDiagramPane = new JBScrollPane(unitPanel);
        innerDiagramPane.setLayout(new ScrollPaneLayout());
        innerDiagramPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        innerDiagramPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        innerDiagramPane.getVerticalScrollBar().setUnitIncrement(50);

        mainPanel.add(innerDiagramPane, BorderLayout.CENTER);

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

    private void selectUnit(UnitNode selectedNode) {
        Figure selected = root.getContext().findFigure(selectedNode);

        if(selected == null || selected == lastSelected)
            return;

        selectedFigure(selected);

        DefaultMutableTreeNode treeNode = treeRoot.findEditPart(selectedNode).getTreeNode();
        if (treeNode != null)
            treeNavigator.setSelectionPath(new TreePath(treeNode.getPath()));

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
                if (lastSelected != null && lastHover != null && lastSelected != lastHover) {
                    updateHover(e);
                    if (lastHover instanceof UnitNodeDiagramFigure || lastHover instanceof UnitNodeContainerFigure)
                        update(unitNodeContainerLayoutPolicy.createAddCommand(lastHover, lastSelected.getPart()));
                    else
                        update(nodeLayoutPolicy.getAddCommand(lastHover.getPart(), lastSelected.getPart()));
                    return;
                }

                if (e.isPopupTrigger())
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
        if(lastSelected == selected)
            return;

        if(lastSelected != null)
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
        selectUnit((UnitNode) treePart.getModel());
    }

    private void adjust(JScrollBar scrollBar, int start, int length ) {
        if (scrollBar.getValue() > start || scrollBar.getValue() + scrollBar.getVisibleAmount() < start + length)
            scrollBar.setValue(start - 100);
    }

    private void showContexMenu(int x, int y) {
        UnitContextMenuProvider builder = new UnitContextMenuProvider(this);
        builder.buildContextMenu(project, diagram, lastSelected.getPart()).show(unitPanel, x, y);
    }

    private void reset() {
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
        JButton btn = new JButton("Select", IconLoader.findIcon(Activator.getIconPath(Activator.PROCESSOR)));
        btn.setContentAreaFilled(false);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        return btn;
    }

    private JButton createButton(String name, String icon, final Class unitNodeClass) {
        JButton btn = new JButton(name, IconLoader.findIcon(Activator.getIconPath(icon)));
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

    private void update(Runnable action) {
        if(action == null)
            return;

        action.run();
        rebuild();
        reset();
        save();
    }

    private void updateVisual() {
        if(lastSelected!=null) {
            PropertyTableModel model = new PropertyTableModel((IPropertySource) lastSelected.getPart().getModel(), this);
            setModel(model);
        }

        int height = unitPanel.getPreferredSize().height;
        innerDiagramPane.getVerticalScrollBar().setMaximum(height);
        repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        rebuild();
        save();
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
