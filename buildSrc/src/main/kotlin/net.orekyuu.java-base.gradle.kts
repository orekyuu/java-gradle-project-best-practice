plugins {
    java
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-Xlint:all")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
dependencies {
    testRuntimeOnly(libs.findLibrary("junitEngine").get())
    testImplementation(libs.findLibrary("junitJupiter").get())
    testImplementation(libs.findLibrary("assertj").get())

    compileOnly(libs.findLibrary("lombok").get())
    annotationProcessor(libs.findLibrary("lombok").get())
}