import java.net.URI

buildscript {
    repositories {
        google()
        jcenter()
        maven {
            url = uri(Url.fabric)
        }
    }

    dependencies {
        classpath(kotlin(module = "gradle-plugin", version = Versions.kotlin))
        classpath(BuildPlugins.androidPlugin)
        classpath(BuildPlugins.kotlinPlugin)
        classpath(BuildPlugins.navigationSafe)
        classpath(BuildPlugins.googleService)
        classpath(BuildPlugins.fabric)
        classpath(BuildPlugins.jaredsburrows)

    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url = URI.create(Url.jitpack)
        }

        maven {
            url = URI.create(Url.sonatype)
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
