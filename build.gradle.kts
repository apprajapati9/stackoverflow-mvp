// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false //without apply false, you will get an error that plugin in applied but no impl is added. apply false is like saying “I want to make this plugin available for all modules, but I’ll let each module decide if it wants to use it.”
}