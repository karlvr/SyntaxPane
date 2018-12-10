# SyntaxPane

[![Build Status](https://travis-ci.org/Sciss/SyntaxPane.svg?branch=master)](https://travis-ci.org/Sciss/SyntaxPane)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/de.sciss/syntaxpane/badge.svg)](https://maven-badges.herokuapp.com/maven-central/de.sciss/syntaxpane)

## Statement

SyntaxPane is an extension to Java Swing's JEditorKit component which adds syntax highlighting support for various languages, including Scala and Java.
The original project JSyntaxPane can be found [on google-code](http://code.google.com/p/jsyntaxpane/). This is a fork from the 0.9.6 branch.

The original project is (C)opyright by Ayman Al-Sairafi and released under the [Apache License, Version 2.0](https://git.iem.at/sciss/SyntaxPane/raw/master/LICENSE).

All changes, reworkings and extensions in SyntaxPane (C)opyright 2011&ndash;2018 by Hanns Holger Rutz and contributors. Released under that same license.

## Supported languages

Find here a list of all the [supported languages.](https://git.iem.at/sciss/SyntaxPane/tree/master/src/main/jflex/de/sciss/syntaxpane/lexers) 

## Linking

The group-id and version have been adjusted to use my name space at Maven Central:

    "de.sciss" % "syntaxpane" % v

The current version `v` is `"1.2.0"`

## Building

JSyntaxPane builds with [sbt 1.x](http://www.scala-sbt.org/). The source code is purely Java, so no Scala compilation is run.
The project uses the [sbt-jflex](https://git.iem.at/sciss/sbt-jflex) plugin v0.4.0, which in turn uses [JFlex](http://jflex.de/) 1.7.0
to generate the lexer Java sources for the supported languages.

To build run `sbt compile`. To run a demo application, run `sbt test:run`. Alternatively, you can build and run the test with

    sbt package test:package
    java -cp target/syntaxpane-<version>.jar:target/syntaxpane-<version>-tests.jar de.sciss.syntaxpane.SyntaxTester

## Contributing

Please see the file [CONTRIBUTING.md](CONTRIBUTING.md) for details.

The people with the following GitHub handles and names have contributed (after the fork), thank you:
mattiaslundstrom, alexeyr, ShalokShalom, Qualtagh, karlvr, Patrick Guermonprez.

