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

configurations.all {
    // nacos强制依赖fastjson
    // exclude("com.alibaba", "fastjson")
    exclude("com.netflix.archaius", "archaius-core")
    exclude("org.springframework.cloud", "spring-cloud-netflix-archaius")
    exclude("org.springframework.cloud", "spring-cloud-starter-netflix-archaius")
    exclude("org.springframework.boot", "spring-boot-starter-tomcat")
}

dependencies {
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Vers.Deps.kotlinCoroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Vers.Deps.kotlinCoroutinesVersion}")

    // spring boot
    implementation("org.springframework.boot:spring-boot-starter:${Vers.Deps.springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-web:${Vers.Deps.springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-undertow:${Vers.Deps.springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-aop:${Vers.Deps.springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-actuator:${Vers.Deps.springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:${Vers.Deps.springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:${Vers.Deps.springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-amqp:${Vers.Deps.springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-mail:${Vers.Deps.springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-security:${Vers.Deps.springBootVersion}")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:${Vers.Deps.springBootVersion}")

    // config discovery
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:3.0.3")
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer:3.0.3")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery:${Vers.Deps.nacosVersion}")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config:${Vers.Deps.nacosVersion}")

    // swagger
    implementation("io.springfox:springfox-boot-starter:${Vers.Deps.swaggerVersion}") {
        exclude("org.springframework.boot")
    }

    // logback
    implementation("ch.qos.logback:logback-classic:${Vers.Deps.logbackVersion}")
    implementation("org.codehaus.janino:janino:${Vers.Deps.janinoVersion}")
    implementation("org.codehaus.janino:commons-compiler:${Vers.Deps.janinoVersion}")

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
    implementation("p6spy:p6spy:${Vers.Deps.p6spyVersion}")
    implementation("com.baomidou:mybatis-plus-boot-starter:${Vers.Deps.mybatisPlusVersion}") {
        exclude("org.springframework.boot")
        exclude("com.alibaba", "fastjson")
    }

    // common tools
    implementation("jakarta.servlet:jakarta.servlet-api:4.0.4")
    implementation("commons-codec:commons-codec:${Vers.Deps.commonsCodecVersion}")
    implementation("org.mapstruct:mapstruct:${Vers.Deps.mapStructVersion}")
    kapt("org.mapstruct:mapstruct-processor:${Vers.Deps.mapStructVersion}")
    implementation("cn.hutool:hutool-all:${Vers.Deps.hutoolVersion}")
    implementation("com.google.guava:guava:${Vers.Deps.guavaVersion}")
    implementation("io.jsonwebtoken:jjwt:${Vers.Deps.jwtVersion}")
    implementation("org.apache.poi:poi:${Vers.Deps.poiVersion}")
    implementation("org.apache.poi:poi-ooxml:${Vers.Deps.poiVersion}")
    implementation("com.google.zxing:core:3.4.1")
    implementation("com.github.wechatpay-apiv3:wechatpay-apache-httpclient:0.2.2")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test:${Vers.Deps.springBootVersion}")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
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
    getByName<BootRun>("bootRun") {
        main = "tech.chaosmin.framework.ApplicationKt"
        args("--spring.profiles.active=demo")
    }

    getByName<BootJar>("bootJar") {
        mainClassName = "tech.chaosmin.framework.ApplicationKt"
    }
}

tasks.create<Exec>("imageBuild") {
    println("准备创建Docker镜像...")
    commandLine("docker", "build", "-t", "registry.cn-shanghai.aliyuncs.com/chaosmin/chaosmin-cdpt:latest", ".")
    commandLine("docker", "rmi", """$(docker images | grep "none" | awk '{print $3}') -f""")
    println("Docker镜像创建成功!")
    println("准备推送Docker镜像...")
    commandLine("docker", "push", "registry.cn-shanghai.aliyuncs.com/chaosmin/chaosmin-cdpt:latest")
    println("Docker镜像推送成功!")
}.dependsOn("bootJar")

tasks.create("deploy") {
    println(">>> Prepare to deploy service to online environment...")
    println("HOST:" + System.getProperty("PSE_HOST"))
    println("USR :" + System.getProperty("PSE_USERNAME"))
    println("PWD :" + System.getProperty("PSE_PASSWORD"))
    val myServer = org.hidetake.groovy.ssh.core.Remote(
        mapOf<String, String>(
            "host" to System.getProperty("PSE_HOST"),
            "user" to System.getProperty("PSE_USERNAME"),
            "password" to System.getProperty("PSE_PASSWORD")
        )
    )
    doLast {
        ssh.run(delegateClosureOf<org.hidetake.groovy.ssh.core.RunHandler> {
            session(myServer, delegateClosureOf<org.hidetake.groovy.ssh.session.SessionHandler> {
                execute("docker-compose stop chaosmin-cdpt")
                execute("docker-compose rm -f")
                execute("docker-compose pull")
                execute("""docker rmi ${'$'}(docker images | grep "none" | awk '{print ${'$'}3}') -f """)
                execute("docker-compose up --build -d chaosmin-cdpt")
            })
        })
        println(">>> Service deployed successfully!")
    }
}