/*
 * Copyright 2008 Ayman Al-Sairafi ayman.alsairafi@gmail.com
 * Copyright 2013-2014 Hanns Holger Rutz.
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

import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.JTextComponent;
import de.sciss.syntaxpane.SyntaxDocument;

/**
 * This action will toggle comments on or off on selected whole lines.
 * 
 * @author Ayman Al-Sairafi, Hanns Holger Rutz
 */
public class ToggleCommentsAction extends DefaultSyntaxAction {

    protected String lineCommentStart     = "//";
    protected Pattern lineCommentPattern1 = null;
    protected Pattern lineCommentPattern2 = null;

    /**
     * creates new JIndentAction.
     * Initial Code contributed by ser... AT mail.ru
     */
    public ToggleCommentsAction() {
        super("toggle-comment");
    }

    /**
     * {@inheritDoc}
     * @param e 
     */
    @Override
    public void actionPerformed(JTextComponent target, SyntaxDocument sDoc,
            int dot, ActionEvent e) {
        if (lineCommentPattern1 == null) {
            lineCommentPattern1 = Pattern.compile("(^\\s*)(" + lineCommentStart + " )(.*)");
            lineCommentPattern2 = Pattern.compile("(^\\s*)(" + lineCommentStart + ")(.*)");
        }
        String[] lines = ActionUtils.getSelectedLines(target);
        int start = target.getSelectionStart();
        StringBuilder toggled = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            Matcher m1 = lineCommentPattern1.matcher(lines[i]);
            if (m1.find()) {
                toggled.append(m1.replaceFirst("$1$3"));
            } else {
                Matcher m2 = lineCommentPattern2.matcher(lines[i]);
                if (m2.find()) {
                    toggled.append(m2.replaceFirst("$1$3"));
                } else {
                    toggled.append(lineCommentStart);
                    toggled.append(' ');
                    toggled.append(lines[i]);
                }
            }
            toggled.append('\n');
        }
        target.replaceSelection(toggled.toString());
        target.select(start, start + toggled.length());
    }

    public void setLineComments(String value) {
        lineCommentStart    = value.replace("\"", "");
        lineCommentPattern1 = null;
        lineCommentPattern2 = null;
    }
}
