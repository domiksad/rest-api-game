## Hunter & Quest API
REST API dla zarządzania postaciami Hunter i Quest w grze RPG, zbudowane w Spring Boot i zarządzane przez Maven. 
API umożliwia tworzenie, odczyt, aktualizację i usuwanie hunterów oraz questów.

## Technologie
- Java 25+
- Spring Boot 4.x
- Maven 3.x
- H2 / MySQL (konfiguracja w application.properties)
- Lombok
- Spring Data JPA
- Spring Web

## Instalacja

1. Sklonuj repozytorium:
```
git clone https://github.com/domiksad/rest-api-game.git
cd hunter-quest-api
```

2. Zbuduj projekt przy pomocy Mavena:
```
mvn clean install
```

3. Uruchomienie
Uruchom aplikację lokalnie:
```
mvn spring-boot:run
```

Domyślnie API działa pod adresem:
`http://localhost:8080/api`

## API Endpoints
### Hunter
Metoda | Endpoint	| Opis
|:-:|:-:|:-:|
GET | /api/hunters | Pobierz listę hunterów
GET | /api/hunters/{id}	| Pobierz pojedynczego huntera
POST | /api/hunters |	Dodaj nowego huntera
PATCH	| /api/hunters/{id}	| Zaktualizuj huntera
DELETE | /api/hunters/{id} | Usuń huntera

### Quest
Metoda | Endpoint | Opis
|:-:|:-:|:-:|
GET | /api/quests |	Pobierz listę questów
GET | /api/quests?dangerLevel={dangerLevel}&questStatus={questStatus} | Pobierz listę questów z filtrami
GET | /api/quests/{id} | Pobierz pojedynczy quest
POST | /api/quests | Dodaj nowy quest
POST | /api/quests/{id}/complete | Zakończ quest
PATCH | /api/quests/{id} | Zaktualizuj quest
DELETE | /api/quests/{id} | Usuń quest

Przykłady żądań
Dodanie nowego huntera
```
POST /api/hunters
Content-Type: application/json

{
  "name": "Geralt",
  "level": 10,
  "experience": 2500
}
```
Pobranie listy questów
```
GET /api/quests
```
Odpowiedź:
```
[
  {
    "id": 1,
    "title": "Zabij smoka",
    "difficulty": "HARD",
    "reward": 500
  }
]
```
## Testowanie

Projekt obsługuje testy jednostkowe i integracyjne za pomocą JUnit i Spring Boot Test:
```
mvn test
```
