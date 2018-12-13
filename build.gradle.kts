import com.android.builder.model.AndroidLibrary

buildscript {

    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Dependencies.androidGradlePlugin)
        classpath(Dependencies.kotlinGradlePlugin)

        classpath("com.chaitanyapramod.gradle:findbugs-android:1.1")
    }
}



plugins {
    java
    jacoco
    id("com.github.ben-manes.versions").version("0.20.0")
    id("io.gitlab.arturbosch.detekt").version("1.0.0-RC11")
}

apply {
    from("$rootDir/team/version_bumper.gradle")
    from("$rootDir/team/dependency-analysis.gradle")
    from("$rootDir/team/jacoco.gradle.kts")
    from("$rootDir/team/tests.gradle")
}

allprojects {

    apply {
        from("$rootDir/team/ktlint.gradle")
        from("$rootDir/team/detekt.gradle")
        from("$rootDir/team/pmd.gradle")
    }

    repositories {
        google()
        jcenter()
    }
}

subprojects{
}

tasks.register("cleanBuild", Delete::class){
    delete(rootProject.buildDir)
}