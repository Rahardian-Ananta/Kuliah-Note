# Kuliah-Note

Academic Knowledge Notebook - Notebook akademik offline-first bergaya Neobrutalism.

## Fitur (Phase 1 MVP)
- Semester, Mata Kuliah, Minggu, Catatan (CRUD penuh)
- Markdown editor
- Pencarian global catatan
- Tugas aktif dengan checkbox
- Seed data semester 5
- Dark mode support

## Tech Stack
- Kotlin + XML Layouts
- Room Database (SQLite)
- MVVM Architecture
- Neobrutalism UI Theme
- GitHub Actions CI

## Build
```bash
./gradlew assembleDebug
```

## Struktur
```
Kuliah-Note/
├── .github/workflows/android-ci.yml
├── app/src/main/java/com/rahardian/kuliahnote/
│   ├── data/db/          # Room entities, DAOs, Database
│   ├── data/repository/   # Repository pattern
│   └── ui/               # Fragments, ViewModels, Adapters
└── gradle/               # Version catalog
```
