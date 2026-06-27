# CookM8

Aplikacja kulinarna na Androida zbudowana w Jetpack Compose, MVVM i Room.

![Makiety ekranów aplikacji](.github/images/mockups.png)

![Schemat bazy danych](.github/images/schema.png)

---

## View Model

Aplikacja działa w oparciu o kilka wyspecjalizowanych view modeli:

HomeViewModel — zarządza ekranem głównym: ładuje listę kategorii z bazy danych i obsługuje wyszukiwarkę przepisów wykorzystującą optymalizacje debounce, by nie wysyłać zapytania po każdej wciśniętej literze.
RecipeListViewModel — pobiera i wyświetla listę przepisów dla wybranej kategorii, którą odczytuje automatycznie z parametrów nawigacji. Pozwala też na dodawanie i usuwanie przepisów z ulubionych.
RecipeDetailViewModel — obsługuje ekran szczegółów przepisu: ładuje pełne dane po ID, zarządza aktywnym zdjęciem w galerii oraz przełączaniem statusu ulubionego. Reużywany również przez ekran składników, żeby nie pobierać tych samych danych dwa razy.
FavoritesViewModel — obserwuje listę ulubionych przepisów w bazie danych i automatycznie odświeża ekran, gdy cokolwiek się zmieni. Udostępnia metodę usuwania przepisu z ulubionych.
AddRecipeViewModel — przechowuje stan całego formularza dodawania przepisu, obsługuje wybór zdjęć i wideo z galerii systemowej (bez żądania uprawnień), kopiuje je do pamięci wewnętrznej aplikacji tak by były dostępne po restarcie, a po zatwierdzeniu waliduje dane i zapisuje nowy przepis do bazy Room.

## Funkcje

- **Przeglądanie po kategoriach** — siatka 2-kolumnowa kategorii z okładkami
- **Wyszukiwanie na żywo** — wyszukiwanie przepisów z debounce podczas pisania
- **Szczegóły przepisu** — galeria zdjęć do przesuwania, instrukcje krok po kroku, czas gotowania, porcje i ocena gwiazdkowa
- **Odtwarzacz wideo** — wbudowany ExoPlayer do filmów z przepisem
- **Odtwarzacz audio** — pasek z narracją audio z odtwarzaniem/pauzą/przewijaniem
- **Ekran składników** — lista z checkboxami i kopiowaniem do schowka jednym kliknięciem
- **Ulubione** — zapisywanie i zarządzanie ulubionymi przepisami
- **Dodaj przepis** — formularz do tworzenia przepisu ze zdjęciami i filmem z galerii
- **Import z aniagotuje.pl** — wklej link do przepisu, a formularz wypełni się automatycznie

---

## Ekrany

| Ekran | Opis |
|---|---|
| Strona główna | Siatka kategorii + wyszukiwanie przepisów |
| Lista przepisów | Wszystkie przepisy w wybranej kategorii |
| Szczegóły przepisu | Galeria, wideo, audio, instrukcje, dodawanie do ulubionych |
| Składniki | Lista składników z możliwością odhaczania i kopiowania |
| Ulubione | Zapisane przepisy |
| Dodaj przepis | Tworzenie przepisu ręcznie lub import z aniagotuje.pl |

---

## Stos technologiczny

| Warstwa | Technologia |
|---|---|
| UI | Jetpack Compose + Material 3 |
| Architektura | MVVM — `ViewModel` + `StateFlow` |
| Nawigacja | Navigation Compose z animacjami przejść |
| Baza danych | Room (lokalny SQLite) |
| Dependency Injection | Hilt |
| Ładowanie obrazów | Coil |
| Wideo / Audio | Media3 ExoPlayer |
| Parsowanie HTML | Jsoup (do importu przepisów) |

---

## Uruchomienie

**Wymagania:** Android Studio Ladybug (2024.2+), JDK 17, urządzenie lub emulator z API 26+

1. Sklonuj repozytorium i otwórz folder `CookM8/` w Android Studio
2. Poczekaj na synchronizację Gradle
3. Uruchom **Run** na podłączonym urządzeniu lub emulatorze

Baza danych uzupełnia się przykładowymi przepisami przy pierwszym uruchomieniu — żadna konfiguracja nie jest wymagana.

---

## Import przepisów

Na ekranie **Dodaj przepis** wklej dowolny link `aniagotuje.pl/przepis/…` i naciśnij **Importuj przepis**. Aplikacja pobiera stronę, parsuje tytuł, opis, składniki, instrukcje krok po kroku, czas gotowania, porcje i zdjęcia, a następnie wypełnia formularz automatycznie. Możesz wszystko przejrzeć i edytować przed zapisaniem.
