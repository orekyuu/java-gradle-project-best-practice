plugins {
    `java-library`
    id("com.github.spotbugs")
    id("com.diffplug.spotless")
}
if (gradle.startParameter.isBuildScan) {
    throw RuntimeException("--scanは禁止")
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
    testImplementation(libs.junitJupiter)
    testImplementation(libs.assertj)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    spotbugs(libs.spotbugs.core)
    spotbugsPlugins(libs.spotbugs.contrib)
    spotbugsPlugins(libs.findsecbugs)
}

spotbugs {
    toolVersion = libs.versions.spotbugs.core
}

spotless {
    java {
        googleJavaFormat()
        endWithNewline()
    }
    format("kts") {
        target("**/*.kts")
        endWithNewline()
    }
}