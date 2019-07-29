# jnigen-template

Template project for setting up [jnigen](https://github.com/libgdx/libgdx/wiki/jnigen) projects.

# Things to change:

* Project name
  - `settings.gradle` contains rootProject.name
* Classpath/Class names.
  - Demo uses demo.JnigenDemo for the main class. Rename them both to your preferred naming.
* SHARED_LIBRARY_NAME
  - This is the name of your resulting shared library. It defaults to "demo" resulting in names such as "demo.dll"
  - `JnigenDemoBuild.SHARED_LIBRARY_NAME`
  - `build.gradle`'s `libraryName` property.
* Publish artifact group name
  - `publish.gradle` contains group 'demo.jnigen' used when publishing maven artifacts.

# TODO (PR's welcome)

* Better support for macOS when not cross-compiling.
* Better support for iOS. Currently is not even published in any artifacts.
* Include examples for compiling external C/C++ libraries
* Uncomment ARM builds when libgdx ARM PR is merged.

# How to use

- `./gradlew dist` will clean and build everything.
- `./gradlew publish` will publish to build/repos. (Run dist first)
- `./gradlew publishToMavenLocal` will publish to your local maven repo.
- `./gradlew jnigen` will run jnigen and build natives.
- `./gradlew jnigenDev` will run jnigen and build natives for linux64 only.
- `./gradlew runTest` will run the `JnigenDemoTest` java class with the build natives in classpath.
