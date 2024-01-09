plugins {
    id("net.orekyuu.java-base")
}

dependencies {
    implementation(project(":extensions:base"))
    runtimeOnly(project(":extensions:console"))
}
