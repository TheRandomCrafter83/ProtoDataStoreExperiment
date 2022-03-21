package com.coderz.f1.protodatastoreexperiment

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val userName:String = "",
    val userEmail:String = "",
    val userLanguage:Language = Language.ENGLISH
    )


enum class Language{
    ENGLISH, GERMAN, SPANISH
}