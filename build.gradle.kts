import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("plugin.jpa") version "1.8.22"
	id("org.openapi.generator") version "6.3.0"

}

group = "com.api"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	// swagger
	//implementation("io.springfox:springfox-swagger-ui:3.0.0")
	//implementation("io.springfox:springfox-swagger2:3.0.0")
	//implementation("io.springfox:springfox-swagger-ui:2.7.0")
	//implementation("io.springfox:springfox-swagger2:2.7.0")
//	implementation("org.springdoc:springdoc-openapi-data-rest:1.6.3")
//	implementation("org.springdoc:springdoc-openapi-ui:1.6.3")
//	implementation("org.springdoc:springdoc-openapi-kotlin:1.6.3")
	//
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation(kotlin("script-runtime"))

}

val oasPackage = "com.api.sisged"
val oasSpecLocation = "src/main/resources/sisged-spec.yaml"
val oasGenOutputDir = project.layout.buildDirectory.dir("generated-oas")

//tasks.openApiGenerate {
//	//generatorName.set("java")
//	generatorName = "kotlin"
//	inputSpec = "$oasSpecLocation".toString()
//	outputDir = "src/main/resources/generated".toString()
//	apiPackage = "org.openapi.example.api"
//	invokerPackage = "org.openapi.example.invoker"
//	modelPackage = "org.openapi.example.model"
////	modelFilesConstrainedTo = ["Error"]
////	configOptions = [dateLibrary: "java"]
//}

// The values oasPackage, oasSpecLocation, oasGenOutputDir are defined earlier
tasks.register("generateServer", org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class) {
	input = project.file(oasSpecLocation).path
	outputDir.set(oasGenOutputDir.get().toString())
	modelPackage.set("$oasPackage.model")
	apiPackage.set("$oasPackage.controller")
	packageName.set(oasPackage)
	generatorName.set("kotlin-spring")
	configOptions.set(
			mapOf(
					"dateLibrary" to "java8",
					"interfaceOnly" to "true",
					"useTags" to "true"
			)
	)
}

val clientOutput = project.layout.buildDirectory.dir("generated-oas-test")

tasks.register("generateClient", org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class) {
	input = project.file(oasSpecLocation).path
	outputDir.set(clientOutput.get().toString())
	modelPackage.set("$oasPackage.model")
	apiPackage.set("$oasPackage.controller")
	packageName.set(oasPackage)
	generatorName.set("kotlin")
	configOptions.set(
			mapOf(
					"dateLibrary" to "java8",
					"useTags" to "true"
			)
	)
}

sourceSets {
	val main by getting
	main.java.srcDir("${oasGenOutputDir.get()}/src/main/kotlin")
	val test by getting
	test.java.srcDir("${clientOutput.get()}/src/main/kotlin")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
	//dependsOn("generateServer")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}