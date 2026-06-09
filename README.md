# Cookbook App — Android (Jetpack Compose + MVVM + Room)

Pełnoprawna aplikacja mobilna na Androida zbudowana zgodnie z wymaganiami projektowymi.

---

## ✅ Spełnione wymagania

| Wymaganie | Realizacja |
|-----------|-----------|
| **Platforma Android** | `minSdk 26`, `targetSdk 35` |
| **Architektura MVVM** | `HomeViewModel`, `RecipeListViewModel`, `RecipeDetailViewModel`, `FavoritesViewModel`, `AddRecipeViewModel` — każdy z `UiState` + `StateFlow` |
| **Jetpack Compose UI** | 100% deklaratywne UI, Material3, `NavigationBar`, animacje |
| **Room (baza danych)** | `RecipeEntity`, `CategoryEntity`, `RecipeDao`, `CategoryDao`, `CookbookDatabase` |
| **≥ 15 zdjęć/obrazów** | 15+ unikalnych URL zdjęć w `seedRecipes` (Unsplash); pager obrazów na ekranie szczegółów |
| **≥ 1 film/video** | `videoUrl` w pierwszym przepisie → odtwarzacz ExoPlayer w `RecipeDetailScreen` |
| **≥ 1 plik audio** | `audioUrl` w pierwszym przepisie → `AudioPlayerBar` z ExoPlayer |
| **Animacje** | Wiele: animowany przycisk ulubione (spring scale), pager dots, staggered list enter, shimmer loading, pulse na audio, AnimatedContent, AnimatedVisibility, slideIn/Out transitions nawigacji |

---

## 🏗️ Struktura projektu

```
CookbookApp/
├── app/src/main/java/com/cookbook/app/
│   ├── data/
│   │   ├── local/
│   │   │   ├── CookbookDatabase.kt          ← Room DB
│   │   │   ├── dao/RecipeDao.kt
│   │   │   ├── dao/CategoryDao.kt
│   │   │   └── entities/RecipeEntity.kt
│   │   └── repository/RecipeRepository.kt   ← single source of truth
│   ├── di/DatabaseModule.kt                 ← Hilt DI
│   ├── model/Models.kt                      ← domain models + routes
│   ├── ui/
│   │   ├── Navigation.kt                    ← NavHost + BottomNav
│   │   ├── components/Components.kt         ← shared Composables
│   │   ├── theme/Theme.kt
│   │   └── screens/
│   │       ├── home/HomeScreen.kt
│   │       ├── recipelist/RecipeListScreen.kt
│   │       ├── recipedetail/RecipeDetailScreen.kt
│   │       ├── favorites/FavoritesScreen.kt
│   │       ├── addrecipe/AddRecipeScreen.kt
│   │       └── ingredients/IngredientsScreen.kt
│   └── viewmodel/
│       ├── HomeViewModel.kt
│       ├── RecipeListViewModel.kt
│       ├── RecipeDetailViewModel.kt
│       └── FavoritesAndAddViewModel.kt
├── CookbookApplication.kt
└── MainActivity.kt
```

---

## 🚀 Uruchomienie

### Wymagania
- **Android Studio** Ladybug (2024.2+) lub nowszy
- **JDK 17**
- Urządzenie/emulator z API 26+

### Kroki
1. Otwórz folder `CookbookApp/` w Android Studio jako projekt
2. Poczekaj na synchronizację Gradle
3. Uruchom `Run ▶` na urządzeniu lub emulatorze Android

> Pierwsze uruchomienie automatycznie zasila bazę danych przykładowymi przepisami (seed data).

---

## 📦 Zależności (kluczowe)

| Biblioteka | Wersja | Cel |
|-----------|--------|-----|
| Jetpack Compose BOM | 2024.09.03 | UI |
| Material3 | via BOM | Komponenty |
| Navigation Compose | 2.8.2 | Nawigacja |
| Hilt | 2.52 | Dependency Injection |
| Room | 2.6.1 | Lokalna baza danych |
| Coil | 2.7.0 | Ładowanie obrazów |
| Media3 ExoPlayer | 1.4.1 | Video + Audio |

---

## 🎨 Ekrany

1. **Home** — siatka kategorii 2×N z wyszukiwaniem live
2. **Recipe List** — lista przepisów w kategorii z kartami
3. **Recipe Detail** — galeria obrazów (HorizontalPager), odtwarzacz wideo, audio, instrukcje krok po kroku
4. **Favorites** — ulubione przepisy / stan pusty z animacją
5. **Add Recipe** — formularz dodawania nowego przepisu
6. **Ingredients** — lista składników z checkboxami i kopiowaniem do schowka
