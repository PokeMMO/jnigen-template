# gdx-jnigen-gradle

This example depends on a WIP gradle plugin for jnigen. You can build it yourself, or wait. https://github.com/PokeMMO/gdx-jnigen

# jnigen-template

Template project for setting up [jnigen](https://github.com/libgdx/libgdx/wiki/jnigen) projects.

# Things to change:

* Project name
  - `settings.gradle` contains rootProject.name
* Classpath/Class names.
  - Demo uses demo.JnigenDemo for the main class. Rename them both to your preferred naming.
* jnigen section in build.gradle
  - `sharedLibName` is the name of your resulting shared library. It defaults to "demo" resulting in names such as "demo.dll"
* Publish artifact group name
  - `publish.gradle` contains group 'demo.jnigen' used when publishing maven artifacts.

# How to use

- `./gradlew jnigen` Generates jnigen native code files and build scripts.
- `./gradlew jnigenBuild` Executes all available jnigen build scripts for the current platform. (Depends on `jnigen`)
- `./gradlew jnigenJarNativesPLATFORM` Assembles a jar archive containing the native libraries. (Run `jnigenBuild` first)
- `./gradlew publish` will publish to build/repos. (Run `jnigenBuild` first)
- `./gradlew publishToMavenLocal` will publish to your local maven repo. (Run `jnigenBuild` first)
- `./gradlew runTest` will run the `JnigenDemoTest` java class with the build natives in classpath.

# Setup

- Ant is required to be on path.
- mingw (i686 and x86_64) is required to be on path.
- Android requires a populated `NDK_HOME` environment variable pointing to the NDK.

# Github Actions

- A fully functional Github Action is setup to build Windows/Linux/macOS/Android/iOS and publish to maven.
