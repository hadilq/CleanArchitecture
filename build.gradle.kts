buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.3.1")
        classpath(kotlin("gradle-plugin", version = Versions.kotlinVersion))
        classpath("io.objectbox:objectbox-gradle-plugin:${Versions.objectboxVersion}")
        classpath("android.arch.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationVersion}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}