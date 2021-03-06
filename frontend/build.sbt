name := """frontend"""

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
