package com.xrosstools.xunit.editor.model;

import com.xrosstools.xunit.BehaviorType;



public abstract class IconNode extends PrimaryNode {
	private String iconId;
	private boolean showTooltip;
	
	public IconNode(BehaviorType type, String iconId, boolean showTooltip){
		super(type);
		this.iconId = iconId;
		this.showTooltip = showTooltip;
	}
	
	public String getIconId() {
		return iconId;
	}

	public boolean isShowTooltip() {
		return showTooltip;
	}
}
