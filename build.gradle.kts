import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    base
    kotlin("jvm") version Vers.Plugins.kotlinVersion
    id("org.jetbrains.kotlin.kapt") version Vers.Plugins.kotlinVersion
    id("org.sonarqube") version Vers.Plugins.sonarqubeVersion
    id("org.hidetake.ssh") version Vers.Plugins.hidetakeSSHVersion
    id("org.springframework.boot") version Vers.Plugins.springBootVersion
    maven
    jacoco
}

group = "tech.chaosmin"
version = "0.0.1.SNAPSHOT"

repositories {
    maven(url = "https://maven.aliyun.com/repository/central")
    maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
    maven(url = "https://maven.aliyun.com/nexus/content/groups/public")
    maven(url = "https://jitpack.io")
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Vers.Deps.kotlinCoroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Vers.Deps.kotlinCoroutinesVersion}")

    // spring boot
    implementation("org.springframework.boot:spring-boot-starter:${Vers.Deps.springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-web:${Vers.Deps.springBootVersion}"){
        exclude("org.springframework.boot:spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-undertow:${Vers.Deps.springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-aop:${Vers.Deps.springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-actuator:${Vers.Deps.springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:${Vers.Deps.springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:${Vers.Deps.springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-mail:${Vers.Deps.springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-security:${Vers.Deps.springBootVersion}")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:${Vers.Deps.springBootVersion}")

    // jackson
    implementation("com.fasterxml.jackson.core:jackson-core:${Vers.Deps.jacksonVersion}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${Vers.Deps.jacksonVersion}")
    implementation("com.fasterxml.jackson.core:jackson-annotations:${Vers.Deps.jacksonVersion}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-joda:${Vers.Deps.jacksonVersion}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Vers.Deps.jacksonVersion}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Vers.Deps.jacksonVersion}")
    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names:${Vers.Deps.jacksonVersion}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:${Vers.Deps.jacksonVersion}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${Vers.Deps.jacksonVersion}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${Vers.Deps.jacksonVersion}")

    // database
    implementation("mysql:mysql-connector-java:${Vers.Deps.mysqlConnectorVersion}")
    implementation("com.baomidou:mybatis-plus-boot-starter:${Vers.Deps.mybatisPlusVersion}") {
        exclude("com.alibaba", "fastjson")
    }
    // implementation("com.github.chaosmin:chaosmin-common:${Vers.Deps.chaosminCommonVersion}")

    // common tools
    implementation("commons-codec:commons-codec:${Vers.Deps.commonsCodecVersion}")
    implementation("org.mapstruct:mapstruct:${Vers.Deps.mapStructVersion}")
    kapt("org.mapstruct:mapstruct-processor:${Vers.Deps.mapStructVersion}")
    implementation("cn.hutool:hutool-all:${Vers.Deps.hutoolVersion}")
    implementation("com.google.guava:guava:${Vers.Deps.guavaVersion}")
    implementation("io.jsonwebtoken:jjwt:${Vers.Deps.jwtVersion}")
    implementation("org.apache.poi:poi:${Vers.Deps.poiVersion}")

    // implementation("com.opencsv:opencsv:${Vers.Deps.opencsvVersion}")

    // config discovery
     implementation("org.springframework.cloud:spring-cloud-starter-consul-discovery:${Vers.Deps.springCloudVersion}")
     implementation("org.springframework.cloud:spring-cloud-starter-alibaba-nacos-config:${Vers.Deps.nacosVersion}") {
         exclude("com.alibaba", "fastjson")
     }

    // swagger
    implementation("io.springfox:springfox-boot-starter:${Vers.Deps.swaggerVersion}")

    // logback
    implementation("ch.qos.logback:logback-classic:${Vers.Deps.logbackVersion}")
    implementation("org.codehaus.janino:janino:${Vers.Deps.janinoVersion}")
    implementation("org.codehaus.janino:commons-compiler:${Vers.Deps.janinoVersion}")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test:${Vers.Deps.springBootVersion}")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        csv.isEnabled = false
        html.isEnabled = false
        // html.destination = file("${buildDir}/jacocoHtml")
    }
}

tasks {
    getByName<BootJar>("bootJar") {
        println("准备打包项目...")
        archiveName = "app.jar"
        mainClassName = "tech.chaosmin.framework.ApplicationKt"
    }

    getByName<BootRun>("bootRun") {
        main = "tech.chaosmin.framework.ApplicationKt"
        args("--spring.profiles.active=demo")
    }
}

tasks.create("deploy") {
    dependsOn("bootJar").doLast {
        println("准备部署...")
        println("项目部署成功!")
    }
}