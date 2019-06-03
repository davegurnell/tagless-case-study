name         := "tagless-case-study"
organization := "com.example"
version      := "1.0-SNAPSHOT"
scalaVersion := "2.12.8"

scalacOptions ++= Seq(
  "-encoding", "UTF-8",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-language:higherKinds",
  "-Xlint:adapted-args",
  "-Xlint:nullary-unit",
  "-Xlint:inaccessible",
  "-Xlint:nullary-override",
  "-Xlint:infer-any",
  "-Xlint:missing-interpolator",
  "-Xlint:doc-detached",
  "-Xlint:private-shadow",
  "-Xlint:type-parameter-shadow",
  "-Xlint:poly-implicit-overload",
  "-Xlint:option-implicit",
  "-Xlint:delayedinit-select",
  "-Xlint:by-name-right-associative",
  "-Xlint:package-object-classes",
  "-Xlint:unsound-match",
  "-Xlint:stars-align",
  "-Xlint:constant",
  "-Xfatal-warnings",
  "-Ypartial-unification",
)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.4.0",
)
