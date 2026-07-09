# README_RESTRICTED

Guide personnel pour lancer MediaPilot / FlowPilot en local dans un contexte restreint, sans Docker.

Ce fichier est volontairement court. Le README principal reste la source de documentation complète du projet.

## Contexte

Ce mode est prévu pour un poste où Docker / Docker Desktop n'est pas disponible.

Dans ce cas, les services sont lancés directement sur la machine :

```text
PostgreSQL local
Spring Boot local
Quarkus local
Angular local
React local
```

## Prérequis

À installer ou vérifier sur la machine :

```powershell
java -version
mvn -v
node -v
npm -v
```

Versions recommandées :

```text
Java        21
Maven       3.9+
Node        22+ ou 24+
npm         10+
PostgreSQL  16+
```

## Base de données locale

Créer une base PostgreSQL locale :

```text
Database : flowpilot
User     : postgres
Password : postgres
Port     : 5432
```

L'URL attendue par défaut est :

```text
jdbc:postgresql://localhost:5432/flowpilot
```

Si besoin, adapter les identifiants dans les fichiers de configuration locaux des backends.

Attention : sans Docker, la persistance dépend de l'installation PostgreSQL locale. Les données ne sont pas supprimées quand les applications sont arrêtées.

## Générer les APIs OpenAPI

Depuis le dossier Angular :

```powershell
cd frontend\angular
npm install
npm run generate:api
```

Depuis le dossier React :

```powershell
cd frontend\react
npm install
npm run generate:api
```

Répartition actuelle :

```text
Angular -> Core API
React   -> Analytics API
```

## Lancer Spring Boot

Depuis la racine du projet :

```powershell
cd backend\springboot
.\mvnw.cmd clean generate-sources spring-boot:run
```

Spring Boot expose l'API principale sur :

```text
http://localhost:8080
```

Swagger / OpenAPI :

```text
http://localhost:8080/swagger-ui.html
```

## Lancer Quarkus

Depuis la racine du projet :

```powershell
cd backend\quarkus
.\mvnw.cmd clean generate-sources quarkus:dev
```

Quarkus expose l'API analytics sur :

```text
http://localhost:8081
```

## Lancer Angular

Depuis la racine du projet :

```powershell
cd frontend\angular
npm install
npm run generate:api
npm start
```

Angular est disponible sur :

```text
http://localhost:4200
```

## Lancer React

Depuis la racine du projet :

```powershell
cd frontend\react
npm install
npm run generate:api
npm run dev
```

React est disponible sur :

```text
http://localhost:5173
```

## Ordre conseillé de lancement

```text
1. PostgreSQL local
2. Spring Boot
3. Quarkus
4. Angular
5. React
```

## URLs locales

| Service | URL |
|---|---|
| Angular | http://localhost:4200 |
| React | http://localhost:5173 |
| Spring Boot | http://localhost:8080 |
| Quarkus | http://localhost:8081 |
| PostgreSQL | localhost:5432 |

## Notes

Ce mode ne remplace pas l'environnement Docker du projet.

Il sert uniquement à travailler dans un environnement restreint où Docker n'est pas disponible.
