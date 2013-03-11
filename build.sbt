name := "JSyntaxPane"

version := "1.0.1-SNAPSHOT"

organization := "de.sciss"

scalaVersion := "2.9.2"

description := "An extension of Java Swing's JEditorKit that supports syntax highlighting for several languages."

homepage := Some(url("https://github.com/Sciss/JSyntaxPane"))

licenses := Seq("Apache 2.0 License" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

scalaVersion := "2.10.0"

crossPaths := false  // this is just a Java project right now!

retrieveManaged := true

autoScalaLibrary := false

mainClass in Compile := Some("jsyntaxpane.SyntaxTester")

// ---- JFlex ----

seq(jflexSettings: _*)

// ---- publishing ----

publishMavenStyle := true

publishTo <<= version { (v: String) =>
  Some(if (v endsWith "-SNAPSHOT")
    "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  else
    "Sonatype Releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
  )
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra <<= name { n =>
<scm>
  <url>git@github.com:Sciss/{n}.git</url>
  <connection>scm:git:git@github.com:Sciss/{n}.git</connection>
</scm>
<developers>
   <developer>
      <id>sciss</id>
      <name>Hanns Holger Rutz</name>
      <url>http://www.sciss.de</url>
   </developer>
</developers>
}
