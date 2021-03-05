import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "2.3.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("org.asciidoctor.convert") version "1.5.9.2"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.3.70" apply true
    id("org.jetbrains.kotlin.plugin.allopen") version "1.3.70" apply true
    id("org.jetbrains.kotlin.plugin.jpa") version "1.3.70" apply true
    kotlin("jvm") version "1.3.70"
    kotlin("plugin.spring") version "1.3.70"
    maven
    `maven-publish`
}

group = "br.com.booking"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val snippetsDir = file("build/generated-snippets")

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://plugins.gradle.org/m2/") }
    maven { url = uri("https://mvnrepository.com/artifact/") }
}

extra["springCloudVersion"] = "Hoxton.SR6"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.cloud:spring-cloud-starter-kubernetes-config")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    asciidoctor("org.springframework.restdocs:spring-restdocs-asciidoctor")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("io.github.resilience4j:resilience4j-kotlin:1.1.0")
    implementation("com.oracle.ojdbc:ojdbc8:19.3.0.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.6.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.6.2")
}

configurations {
    all {
        exclude(group = "ch.qos.logback", module = "logback-classic")
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
        exclude(group = "org.slf4j", module = "log4j-over-slf4j")
        exclude(group = "org.apache.tomcat", module = "tomcat-jdbc")
    }

}


dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks{

    withType<Test> {
        useJUnitPlatform()
        if (project.hasProperty("dev")) {
            systemProperties("log4j.configurationFile" to "log4j2-local.yml")
            systemProperties("spring.profiles.active" to "local")
        }
    }

    springBoot {
        buildInfo()
    }

    test{
        outputs.dir(snippetsDir)
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    withType<BootRun> {
        if (project.hasProperty("dev")) {
            systemProperties("log4j.configurationFile" to "log4j2-local.yml")
            systemProperties("spring.profiles.active" to "local")
        }

    }
    asciidoctor {
        inputs.dir(snippetsDir)
        dependsOn("test")
        outputDir = file("build/docs")
    }

    bootJar {
        dependsOn(asciidoctor)
        copy {
            from("${asciidoctor.get().outputDir}/html5")
            into("build/resources/main/docs")
        }
    }

}

publishing {
    publications {
        this.
        create<MavenPublication>("bootJava") {
            artifact(tasks.getByName("bootJar"))
        }
    }
    repositories {
        maven {
            url = uri("https://upload.mvn.repo.intranet/uol")
        }
    }

}