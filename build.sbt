name := "JSyntaxPane"

version := "1.0.0"

organization := "de.sciss"

scalaVersion := "2.9.2"

description := "An extension of Java Swing's JEditorKit that supports syntax highlighting for several languages."

homepage := Some( url( "https://github.com/Sciss/JSyntaxPane" ))

licenses := Seq("Apache 2.0 License" -> url( "http://www.apache.org/licenses/LICENSE-2.0.txt" ))

scalaVersion := "2.9.2"

crossPaths := false  // this is just a Java projet right now!

retrieveManaged := true

// ---- JFlex ----

jflexSettings

// sbt-jflex assumes directory is called `flex` by default. make sure to correct this here:
sourceDirectory in jflex <<= (sourceDirectory in Compile) { _ / "jflex" }

sourceGenerators in Compile <+= generate in jflex

// ---- publishing ----

publishMavenStyle := true

publishTo <<= version { (v: String) =>
   Some( if( v.endsWith( "-SNAPSHOT" ))
      "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
   else
      "Sonatype Releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
   )
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra :=
<scm>
  <url>git@github.com:Sciss/JSyntaxPane.git</url>
  <connection>scm:git:git@github.com:Sciss/JSyntaxPane.git</connection>
</scm>
<developers>
   <developer>
      <id>sciss</id>
      <name>Hanns Holger Rutz</name>
      <url>http://www.sciss.de</url>
   </developer>
</developers>

