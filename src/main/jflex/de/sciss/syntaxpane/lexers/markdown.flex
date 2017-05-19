/*
 * Copyright 2011-2017 Hanns Holger Rutz.
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

// cf. http://spec.commonmark.org

package de.sciss.syntaxpane.lexers;

import de.sciss.syntaxpane.Token;
import de.sciss.syntaxpane.TokenType;

%%

%public
%class MarkdownLexer
%extends DefaultJFlexLexer
%final
%unicode
%char
%type Token

%{
    /**
     * Create an empty lexer, yyrset will be called later to reset and assign
     * the reader
     */
    public MarkdownLexer() {
        super();
    }

    @Override
    public int yychar() {
        return yychar;
    }

    private static final byte TAG_OPEN      =  1;
    private static final byte TAG_CLOSE     = -1;

    private static final byte COMMENT_OPEN  =  4;
    private static final byte COMMENT_CLOSE = -4;
%}

%xstate COMMENT, TAG, ESCAPESTATE, CODESTATE, CHARVALUE

/* markdown */

//nl                  = [\n|\r|\r\n]
//tab                 = [\t\f]
//space               = " "
//newstring           = ([\n|\r|\r\n]{3,100})([a-zA-Z0-9.,;:]+)
//string              = [a-zA-Z0-9.,;:]+
//newstar             = ([\n|\r|\r\n]{3,100})(\*)
//star                = \*
//newstarDouble       = ([\n|\r|\r\n]{3,100})(\*\*)
//starDouble			= \*\*
//newunderscore       = ([\n|\r|\r\n]{3,100})(\_)
//underscore          = \_
//newunderscoreDouble = ([\n|\r|\r\n]{3,100})(__)
//underscoreDouble    = __
//squareBracketO      = \[
//squareBracketC      = \]
//roundBracketO       = \(
//roundBracketC       = \)
//equal               = (\=)+
//hash                = #
//rowSpaces           = [" "]{1,3}
//startOrdList        = [" "]{1,3}[0-9]+.[" "]*
//startUnList         = [" "]{1,3}(\*|\+|\-)((" ")+| \t)
//startPre            = [" "]{4}
//id                  = [A-Za-z_][A-Za-z0-9_]*
//IntegerNumber       = 0 | [1-9][0-9]*
//charValue           = [a-zA-Z]
//FloatLiteral        = ({FLit1}|{FLit2}|{FLit3}) {Exponent}?
//FLit1               = [0-9]+ \. [0-9]*
//FLit2               = \. [0-9]+
//FLit3               = [0-9]+
//Exponent            = [eE] [+-]? [0-9]+



/* embedded HTML */

/* white space */
S = (\u0020 | \u0009 | \u000D | \u000A)+

/* characters */
// Char = \u0009 | \u000A | \u000D | [\u0020-\uD7FF] | [\uE000-\uFFFD] | [\u10000-\u10FFFF]

/* comments */
CommentStart = "<!--"
CommentEnd = "-->"

NameStartChar = ":" | [A-Z] | "_" | [a-z]
NameChar = {NameStartChar} | "-" | "." | [0-9] | \u00B7
Name = {NameStartChar} {NameChar}*

/* Tags */
OpenTagStart = "<" {Name}
OpenTagClose = "/>"
OpenTagEnd = ">"
CloseTag = "</" {Name} {S}* ">"

/* attribute */
Attribute = {Name} "="

/* HTML specifics */
HTMLTagName =
    "address"        |
    "applet"         |
    "area"           |
    "a"              |
    "b"              |
    "base"           |
    "basefont"       |
    "big"            |
    "blockquote"     |
    "body"           |
    "br"             |
    "caption"        |
    "center"         |
    "cite"           |
    "code"           |
    "dd"             |
    "dfn"            |
    "dir"            |
    "div"            |
    "dl"             |
    "dt"             |
    "font"           |
    "form"           |
    "h"[1-6]         |
    "head"           |
    "hr"             |
    "html"           |
    "img"            |
    "input"          |
    "isindex"        |
    "kbd"            |
    "li"             |
    "link"           |
    "LINK"           |
    "map"            |
    "META"           |
    "menu"           |
    "meta"           |
    "ol"             |
    "option"         |
    "param"          |
    "pre"            |
    "p"              |
    "samp"           |
    "span"           |
    "select"         |
    "small"          |
    "strike"         |
    "sub"            |
    "sup"            |
    "table"          |
    "td"             |
    "textarea"       |
    "th"             |
    "title"          |
    "tr"             |
    "tt"             |
    "ul"             |
    "var"            |
    "xmp"            |
    "script"         |
    "noscript"       |
    "style"

HTMLAttrName =
    "action"            |
    "align"             |
    "alink"             |
    "alt"               |
    "archive"           |
    "background"        |
    "bgcolor"           |
    "border"            |
    "bordercolor"       |
    "cellpadding"       |
    "cellspacing"       |
    "checked"           |
    "class"             |
    "clear"             |
    "code"              |
    "codebase"          |
    "color"             |
    "cols"              |
    "colspan"           |
    "content"           |
    "coords"            |
    "enctype"           |
    "face"              |
    "gutter"            |
    "height"            |
    "hspace"            |
    "href"              |
    "id"                |
    "link"              |
    "lowsrc"            |
    "marginheight"      |
    "marginwidth"       |
    "maxlength"         |
    "method"            |
    "name"              |
    "prompt"            |
    "rel"               |
    "rev"               |
    "rows"              |
    "rowspan"           |
    "scrolling"         |
    "selected"          |
    "shape"             |
    "size"              |
    "src"               |
    "start"             |
    "target"            |
    "text"              |
    "type"              |
    "url"               |
    "usemap"            |
    "ismap"             |
    "valign"            |
    "value"             |
    "vlink"             |
    "vspace"            |
    "width"             |
    "wrap"              |
    "abbr"              |
    "accept"            |
    "accesskey"         |
    "axis"              |
    "char"              |
    "charoff"           |
    "charset"           |
    "cite"              |
    "classid"           |
    "codetype"          |
    "compact"           |
    "data"              |
    "datetime"          |
    "declare"           |
    "defer"             |
    "dir"               |
    "disabled"          |
    "for"               |
    "frame"             |
    "headers"           |
    "hreflang"          |
    "lang"              |
    "language"          |
    "longdesc"          |
    "multiple"          |
    "nohref"            |
    "nowrap"            |
    "object"            |
    "profile"           |
    "readonly"          |
    "rules"             |
    "scheme"            |
    "scope"             |
    "span"              |
    "standby"           |
    "style"             |
    "summary"           |
    "tabindex"          |
    "valuetype"         |
    "version"

HTMLOpenTagStart = "<" {HTMLTagName}
HTMLCloseTag = "</" {HTMLTagName} {S}* ">"
HTMLAttribute = {HTMLAttrName} "="

/* string and character literals */
DQuoteStringChar = [^\r\n\"]
SQuoteStringChar = [^\r\n\']

%%

<YYINITIAL> {

/*
    A line consisting of 0-3 spaces of indentation, followed by a sequence of three or more
    matching -, _, or * characters, each followed optionally by any number of spaces,
    forms a thematic break.

 */

  ^\s*\n[ ]{0,3}("--"[\-]+ | "**"[\*]+ | "__"[\_]+ )[ ]*\n { return token(TokenType.DELIMITER); }

/* HTML support */

  "&"  [a-z]+ ";"                |
  "&#" [:digit:]+ ";"            { return token(TokenType.KEYWORD2); }

  {HTMLOpenTagStart}             {
                                     yybegin(TAG);
                                     return token(TokenType.KEYWORD2, TAG_OPEN);
                                 }
  {HTMLCloseTag}                 {   return token(TokenType.KEYWORD2, TAG_CLOSE); }
  {OpenTagStart}                 {
                                     yybegin(TAG);
                                     return token(TokenType.KEYWORD, TAG_OPEN);
                                 }
  {CloseTag}                     {   return token(TokenType.KEYWORD, TAG_CLOSE); }
  {CommentStart}                 {
                                     yybegin(COMMENT);
                                     return token(TokenType.COMMENT2, COMMENT_OPEN);
                                 }
}

<TAG> {
  {HTMLAttribute}                { return token(TokenType.KEYWORD2); }
  {Attribute}                    { return token(TokenType.IDENTIFIER); }

  \"{DQuoteStringChar}*\"        |
  \'{SQuoteStringChar}*\'        { return token(TokenType.STRING); }


  {OpenTagClose}                 {
                                     yybegin(YYINITIAL);
                                     return token(TokenType.KEYWORD, TAG_CLOSE);
}

  {OpenTagEnd}                   {
                                     yybegin(YYINITIAL);
                                     return token(TokenType.KEYWORD);
                                 }
}

<COMMENT> {
  {CommentEnd}                   {
                                     yybegin(YYINITIAL);
                                     return token(TokenType.COMMENT2, COMMENT_CLOSE);
                                 }
   ~{CommentEnd}                 {
                                     yypushback(3);
                                     return token(TokenType.COMMENT);
                                 }
}

<YYINITIAL,TAG,COMMENT> {
/* error fallback */
   .|\n                          {  }
   <<EOF>>                       { return null; }
}