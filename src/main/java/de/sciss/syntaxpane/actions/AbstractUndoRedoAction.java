/*
 * Copyright 2008 Ayman Al-Sairafi ayman.alsairafi@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License
 *       at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.sciss.syntaxpane.actions;

import de.sciss.syntaxpane.SyntaxDocument;
import de.sciss.syntaxpane.util.Configuration;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class AbstractUndoRedoAction extends DefaultSyntaxAction {
    private JEditorPane editor;
    protected SyntaxDocument doc;

    private final String property;

    protected AbstractUndoRedoAction(String property, String key) {
        super(key);
        this.property = property;
    }

    private PropertyChangeListener propListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent e) {
            // System.out.println(property + " - " + e);
            setEnabled(updateState());
        }
    };

    abstract protected boolean updateState();

    private void removeDocument() {
        if (doc != null) {
            doc.removePropertyChangeListener(property, propListener);
            doc = null;
        }
    }

    private void setDocument(SyntaxDocument newDoc) {
        if (doc != null) throw new IllegalStateException();
        doc = newDoc;
        doc.addPropertyChangeListener(property, propListener);
        setEnabled(updateState());
    }

    private PropertyChangeListener docListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent e) {
            // if (e.getPropertyName().equals("document")) {
                removeDocument();
                Object newDoc = e.getNewValue();
                if (newDoc instanceof SyntaxDocument) {
                    setDocument((SyntaxDocument) newDoc);
                    // editor.removePropertyChangeListener("document", docListener);
                }
//            } else {
//                System.out.println(e.getPropertyName() + " " + e.getNewValue());
//            }
        }
    };

    @Override
    public void install(JEditorPane editor, Configuration config, String name) {
        super.install(editor, config, name);

        if (this.editor != null) throw new IllegalStateException();

        this.editor = editor;
        editor.addPropertyChangeListener("document", docListener);

        // editor.addPropertyChangeListener("editorKit", docListener);

        // Document doc = editor.getDocument();
        // if (doc instanceof SyntaxDocument) {
        //     setDocument((SyntaxDocument) doc);
        // }
    }

    @Override
    public void deinstall(JEditorPane editor) {
        super.deinstall(editor);

        if (this.editor != editor) throw new IllegalStateException();

        editor.removePropertyChangeListener("document", docListener);
        removeDocument();
        this.editor = null;
    }
}