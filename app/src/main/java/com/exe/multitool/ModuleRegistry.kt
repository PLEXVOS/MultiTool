package com.exe.multitool

/**
 * ModuleRegistry - registro di tutti i moduli installati in MultiTool.
 *
 * Per aggiungere un nuovo modulo:
 * 1. Aggiungere il modulo in settings.gradle: include ':module-nomemodulo'
 * 2. Aggiungere implementation project(':module-nomemodulo') in app/build.gradle
 * 3. Aggiungere qui una riga ModuleInfo con l'entryClass corretto
 * 4. Compilare. Fine.
 */
data class ModuleInfo(
    val icon: String,
    val name: String,
    val description: String,
    val entryClass: String
)

object ModuleRegistry {
    val modules: List<ModuleInfo> = listOf(

        ModuleInfo(
            icon = "▶",
            name = "FFMPEG TOOLKIT",
            description = "Converti, taglia, unisci video e audio",
            entryClass = "com.exe.ffmpeg.MainActivity"
        )

        // Esempio prossimo modulo:
        // ModuleInfo(
        //     icon = "◈",
        //     name = "IMAGE TOOL",
        //     description = "Comprimi, converti, ridimensiona immagini",
        //     entryClass = "com.exe.imagetool.MainActivity"
        // )
    )
}
