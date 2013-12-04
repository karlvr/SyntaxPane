## JSyntaxPane

JSyntaxPane is an extension to Java Swing's JEditorKit component which adds syntax highlighting support for various languages, including Scala and Java. The original project can be found [on google-code](http://code.google.com/p/jsyntaxpane/). This is a fork from the 0.9.6 branch.

JSyntaxPane is (C)opyright by Ayman Al-Sairafi and released under the [Apache License, Version 2.0](http://github.com/Sciss/JSyntaxPane/blob/master/licenses/JSyntaxPane-License.txt). All changes by Hanns Holger Rutz released under that same license.

The motivation for this fork was to change google-code, svn and maven for GitHub, git and sbt, in order to be able to easily adjust the Scala support. Another motivation was to publish this project to Maven Central, so that linking to it from Scala projects becomes hassle-free (no transitive dependencies required, as Maven Central is a default lookup place for sbt).

### linking

The group-id and version have been adjusted to use my name space at Maven Central:

    "de.sciss" % "jsyntaxpane" % v

The current version `v` is `"1.0.1+"`

### building

JSyntaxPane builds with sbt 0.13. The source code is purely Java at the moment, so no Scala compilation is run. The project uses the [sbt-jflex](https://github.com/sbt/sbt-jflex) plugin v0.1-SNAPSHOT, which in turn uses [JFlex](http://jflex.de/) 1.4.3 to generate the lexer Java sources for the supported languages.

To build run `sbt compile`. To run a demo application, run `sbt run`.

