package com.xrosstools.xunit.idea.editor;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public interface  XrossUnitIcons {
    Icon Start_point = IconLoader.getIcon(Activator.getIconPath(Activator.START_POINT), XrossUnitIcons.class);
    Icon End_point = IconLoader.getIcon(Activator.getIconPath(Activator.END_POINT), XrossUnitIcons.class);

    Icon Processor = IconLoader.getIcon(Activator.getIconPath(Activator.PROCESSOR), XrossUnitIcons.class);
    Icon Converter = IconLoader.getIcon(Activator.getIconPath(Activator.CONVERTER), XrossUnitIcons.class);
    Icon Validator = IconLoader.getIcon(Activator.getIconPath(Activator.VALIDATOR), XrossUnitIcons.class);
    Icon Locator = IconLoader.getIcon(Activator.getIconPath(Activator.LOCATOR), XrossUnitIcons.class);
    Icon Dispatcher = IconLoader.getIcon(Activator.getIconPath(Activator.DISPATCHER), XrossUnitIcons.class);

    Icon Decorator = IconLoader.getIcon(Activator.getIconPath(Activator.DECORATOR), XrossUnitIcons.class);
    Icon Adapter = IconLoader.getIcon(Activator.getIconPath(Activator.ADAPTER), XrossUnitIcons.class);
    Icon Chain = IconLoader.getIcon(Activator.getIconPath(Activator.CHAIN), XrossUnitIcons.class);
    Icon Bi_branch = IconLoader.getIcon(Activator.getIconPath(Activator.BI_BRANCH), XrossUnitIcons.class);
    Icon Branch = IconLoader.getIcon(Activator.getIconPath(Activator.BRANCH), XrossUnitIcons.class);
    Icon While_loop = IconLoader.getIcon(Activator.getIconPath(Activator.WHILE), XrossUnitIcons.class);
    Icon Do_while_loop = IconLoader.getIcon(Activator.getIconPath(Activator.DO_WHILE), XrossUnitIcons.class);
    Icon Parallel_branch = IconLoader.getIcon(Activator.getIconPath(Activator.PARALLEL_BRANCH), XrossUnitIcons.class);

    Icon GENERATE_HLPER = IconLoader.getIcon("/icons/generate_helper.png", XrossUnitIcons.class);
    Icon GENERATE_TEST = IconLoader.getIcon("/icons/generate_test.png", XrossUnitIcons.class);
}
