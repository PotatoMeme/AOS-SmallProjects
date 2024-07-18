// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.protobuf) apply false
}

/*
buildscript {
    dependencies {
        //navigation
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}*/
