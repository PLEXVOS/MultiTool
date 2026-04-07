# MultiTool

App Android modulare con sistema di moduli integrati.

## Struttura repository

```
MultiTool/
├── app/                    <- Host MultiTool (launcher, lista moduli, log globale)
├── module-ffmpeg/          <- Modulo FFmpeg (libreria Android)
│   └── libs/               <- POSIZIONARE QUI ffmpeg-kit-full.aar
└── .github/workflows/      <- Build automatica via GitHub Actions
```

## Setup iniziale OBBLIGATORIO

Prima di compilare devi posizionare il file `.aar` nella cartella corretta:

1. Copia `ffmpeg-kit-full.aar` nella cartella `module-ffmpeg/libs/`
2. Fai commit e push su GitHub
3. GitHub Actions compilerà automaticamente e produrrà l'APK

## Aggiungere un nuovo modulo

1. Creare una nuova cartella `module-nomemodulo/` con struttura Android library
2. In `settings.gradle`: aggiungere `include ':module-nomemodulo'`
3. In `app/build.gradle`: aggiungere `implementation project(':module-nomemodulo')`
4. In `app/.../ModuleRegistry.kt`: aggiungere una riga `ModuleInfo(...)`
5. Push. Fine.

## Output APK

L'APK compilato si trova in GitHub Actions > Build > Artifacts > app-debug.zip
