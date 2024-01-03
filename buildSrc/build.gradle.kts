plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies{
    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}