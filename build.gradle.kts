plugins {
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.kotlin.spring)
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.dependency.management)
}

group = "com.kgrevehagen.goat-notes"
version = "0.0.1"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(enforcedPlatform(libs.aws.sdk.bom))
	implementation(libs.aws.sdk.dynamodb)
	implementation(libs.aws.sdk.dynamodb.enhanced)
	implementation(libs.aws.sdk.sso)
	implementation(libs.aws.sdk.sso.oidc)
	implementation(libs.spring.boot.starter.web)
	implementation(libs.spring.boot.starter.security)
	implementation(libs.spring.boot.starter.oauth2.resource.server)
	implementation(libs.jackson.module.kotlin)
	implementation(libs.kotlin.reflect)

	testImplementation(libs.assertk)
	testImplementation(libs.spring.boot.starter.test)
	testImplementation(libs.spring.security.test)
	testImplementation(libs.kotlin.test.junit5)
	testRuntimeOnly(libs.junit.platform.launcher)
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
