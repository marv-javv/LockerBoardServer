plugins {
    kotlin("jvm") version "2.0.20"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.server.application.ApplicationKt")
}

dependencies {
    // Ktor server dependencies
    implementation("io.ktor:ktor-server-core:2.x.x")
    implementation("io.ktor:ktor-server-netty:2.x.x")
    implementation("io.ktor:ktor-server-content-negotiation:2.x.x")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.x.x")
    implementation("ch.qos.logback:logback-classic:1.4.x")

    testImplementation(kotlin("test"))
}

// Native library (JNI support)
val os = org.gradle.internal.os.OperatingSystem.current()
val jniLib = when {
    os.isLinux -> "libjni-linux.so"
    os.isWindows -> "jni-windows.dll"
    os.isMacOsX -> "libjni-macos.dylib"
    else -> throw GradleException("Unsupported OS")
}
tasks.withType<JavaExec> {
    systemProperty("java.library.path", "src/main/resources/native")
}

tasks.test {
    useJUnitPlatform()
}
