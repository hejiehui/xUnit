package com.xrosstools.xunit.idea.editor;

import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.FileEditorStateLevel;

public class DummyFileEditorState implements FileEditorState
{
    public static final FileEditorState DUMMY = new DummyFileEditorState();

    public boolean canBeMergedWith(FileEditorState otherState,
                                   FileEditorStateLevel level)
    {
        return false;
    }
}