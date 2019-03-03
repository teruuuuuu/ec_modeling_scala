name := """ec_modeling_scala"""
organization := "com.teruuu"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  filters,
  guice,
  "com.h2database"  %  "h2"      % "1.4.193",
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test
)
