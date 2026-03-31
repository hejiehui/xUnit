package com.xrosstools.xunit.editor.actions;

import org.eclipse.jface.dialogs.Dialog;  
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class CodeDisplayer extends Dialog {
	private String title;
	private String content;
    private StyledText styledText;  
  
    public CodeDisplayer(String title, String content) {  
        super(Display.getCurrent().getActiveShell());
        this.title = title;
        this.content = content;
    } 
    
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		if (title != null) {
			shell.setText(title);
		}
	}
  
    @Override  
    protected Control createDialogArea(Composite parent) {  
        Composite composite = (Composite) super.createDialogArea(parent);  
        composite.setLayout(new FillLayout());  
  
        ScrolledComposite scrolledComposite = new ScrolledComposite(composite, SWT.V_SCROLL | SWT.H_SCROLL);  
        scrolledComposite.setExpandHorizontal(true);  
        scrolledComposite.setExpandVertical(true);  
  
        Composite contentComposite = new Composite(scrolledComposite, SWT.NONE);  
        contentComposite.setLayout(new GridLayout(1, false));  
  
        styledText = new StyledText(contentComposite, SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);  
        styledText.setText(content);  
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);  
        gd.heightHint = 200;  
        styledText.setLayoutData(gd);  
  
        scrolledComposite.setContent(contentComposite);  
        scrolledComposite.setMinSize(contentComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));  
  
        return composite;  
    }  
  
    @Override  
    protected void createButtonsForButtonBar(Composite parent) {  
        createButton(parent, Dialog.OK, "OK", true);  
    }  
  
    @Override  
    protected void buttonPressed(int buttonId) {  
        if (buttonId == Dialog.OK) {  
            close();  
        } else {  
            super.buttonPressed(buttonId);  
        }  
    }  
}
