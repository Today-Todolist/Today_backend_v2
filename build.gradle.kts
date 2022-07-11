import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val testExclusionList = arrayOf(
    "**Application.**",

    "**/domain/*/domain/**",
    "**/domain/*/dto/**",
    "**/domain/*/exception/**",

    "**/global/config/**",
    "**/global/domain/**",
    "**/global/dto/**",
    "**/global/error/dto/**",
    "**/global/error/exception/**",
    "**/global/security/ErrorCode",
    "**/global/security/***Config***",

    "**/infra/file/**",
    "**/infra/mail/**")

plugins {
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    id ("org.sonarqube") version "3.3"
    jacoco
    groovy
}

group = "today"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

kotlin.sourceSets.test {
    kotlin.srcDir("src/test/kotlin")
}

dependencies {
    // etc
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-validation")

    // mongodb
    implementation ("org.springframework.boot:spring-boot-starter-data-mongodb")

    // security
    implementation ("org.springframework.boot:spring-boot-starter-security")

    // redis
    implementation ("org.springframework.boot:spring-boot-starter-data-redis")
    implementation ("it.ozimov:embedded-redis:0.7.2")

    // validation
    implementation ("org.springframework.boot:spring-boot-starter-validation")

    // jwt
    implementation ("io.jsonwebtoken:jjwt:0.9.1")

    // kotlin
    implementation ("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation ("org.jetbrains.kotlin:kotlin-reflect")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // configure
    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor")

    // aws
    implementation ("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")

    // mail
    implementation ("org.springframework.boot:spring-boot-starter-mail")

    // log
    implementation("io.github.microutils:kotlin-logging:2.1.21")

    // test - etc
    testImplementation ("org.springframework.boot:spring-boot-starter-test")

    // test - kotest
    testImplementation("io.kotest:kotest-runner-junit5:5.3.2")
    testImplementation("io.kotest:kotest-assertions-core:5.3.2")

    // test - embedded mongodb
    testImplementation ("de.flapdoodle.embed:de.flapdoodle.embed.mongo:3.4.6")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

sonarqube {
    properties {
        property ("sonar.projectKey", "Today-Todolist_Today_backend_v2")
        property ("sonar.organization", "today-todolist")
        property ("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.exclusions", testExclusionList.toMutableList())
    }
}

tasks.jacocoTestReport {
    reports {
        html.required.set(true)
        xml.required.set(true)
    }

    executionData(files(testExclusionList.toMutableList()))

    finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = "CLASS"
            excludes = testExclusionList.toMutableList()
        }
    }
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy (tasks.jacocoTestReport)
}