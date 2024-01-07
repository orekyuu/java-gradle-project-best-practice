plugins {
    java
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-Xlint:all")
}

dependencies {
    testRuntimeOnly(libs.junitEngine)
    testImplementation(libs.junitJupiter.get())
    testImplementation(libs.assertj.get())

    compileOnly(libs.lombok.get())
    annotationProcessor(libs.lombok)
}