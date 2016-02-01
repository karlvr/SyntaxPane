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

import javax.swing.JEditorPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.util.*;

/**
 * A Pair action inserts a pair of characters (left and right) around the
 * current selection, and then places the caret between them
 *
 * The pairs are hard-coded here.
 */
public class PairAction extends DefaultSyntaxAction implements DocumentListener {

    public PairAction() {
        super("PAIR_ACTION");
    }

    private Document listeningDocument = null;
    private final List<Integer> endPositions = new LinkedList<Integer>();

    private static Map<String, String> PAIRS = new HashMap<String, String>(4);

    static {
        PAIRS.put("(", ")");
        PAIRS.put("[", "]");
        PAIRS.put("\"", "\"");
        PAIRS.put("'", "'");
    }

    @Override
    public void actionPerformed(JTextComponent target, SyntaxDocument sDoc,
                                int dot, ActionEvent e) {

        final String key = e.getActionCommand();

        if (listeningDocument != sDoc) {
            if (listeningDocument != null) {
                listeningDocument.removeDocumentListener(this);
            }
            listeningDocument = sDoc;
            sDoc.addDocumentListener(this);
        }

        String left = key;
        String right = PAIRS.get(left);
        String selected = target.getSelectedText();
        if (selected != null) {
            if (right != null) {
                target.replaceSelection(left + selected + right);
            } else {
                target.replaceSelection(key);
            }
        } else {
            boolean unhandled = true;
            try {
                Iterator<Integer> positionIter = endPositions.iterator();
                while (positionIter.hasNext()) {
                    int trackedPosition = positionIter.next();
                    if (target.getCaretPosition() == trackedPosition) {
                        String nextChar = target.getDocument().getText(target.getCaretPosition(), 1);
                        if (nextChar.equals(key)) {
                            target.replaceSelection("");
                            target.setCaretPosition(target.getCaretPosition() + 1);
                            positionIter.remove();
                            unhandled = false;
                            break;
                        }
                    }
                }
            } catch (BadLocationException e1) {
                throw new RuntimeException("Internal logic error", e1);
            }
            if (unhandled) {
                if (right != null) {
                    target.replaceSelection(left + right);
                    target.setCaretPosition(target.getCaretPosition() - right.length());
                    endPositions.add(target.getCaretPosition());
                } else {
                    target.replaceSelection(key);
                }
            }
        }
    }

    @Override
    public void deinstall(JEditorPane editor) {
        super.deinstall(editor);

        editor.getDocument().removeDocumentListener(this);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        ListIterator<Integer> positionIter = endPositions.listIterator();
        while (positionIter.hasNext()) {
            int position = positionIter.next();
            if (position >= e.getOffset()) {
                position += e.getLength();
                positionIter.set(position);
            } else {
                positionIter.remove();
            }
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        ListIterator<Integer> positionIter = endPositions.listIterator();
        while (positionIter.hasNext()) {
            int position = positionIter.next();
            if (position >= e.getOffset()) {
                if (position >= e.getOffset() + e.getLength()) {
                    position -= e.getLength();
                    positionIter.set(position);
                } else {
                    positionIter.remove();
                }
            } else {
                positionIter.remove();
            }
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        // Do nothing
    }
}
