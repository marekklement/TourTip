# TourTip application
This application was implemented to provide backend logic for tournament tip tool. This tool should provide simple UI for groups of people who like to play guess the winner game during some world tournaments - for example during Ice 
hockey championship. Backend application should provide API for three kind of users - not authenticated, basic registered users and admins. Each group has different permissions for endpoints.

> **_NOTE:_**  App is not yet finished.

## Users
### Not authenticated user
Can only achieve endpoints for registering and logging in the app. 
### Basic user
This user has to authenticate. After that he is allowed to see the tournaments, guess the winners of some games and see the results of tournaments.
### Admin
Admin user is master user. He can create new tournaments, teams, games and also to view all the other users.

## Technologies and versions
 - ktorVersion = 2.2.1
- kotlinVersion = 1.7.22
- logbackVersion = 1.4.5
- exposedVersion = 0.40.1
- psqlDriverVersion = 42.5.1
- flywayVersion = 9.8.3
- kodeinVersion = 7.15.1
- cryptoVersion = 0.4

## Aplication model
### Users:
- id (primary key)
- username
- password
- email
- first_name
- last_name
- created_at
- updated_at

### Games:
- id (primary key)
- tournament_id (foreign key to Tournaments)
- home_team_id (foreign key to Teams)
- away_team_id (foreign key to Teams)
- start_time
- home_score
- away_score
- created_at
- updated_at

### Predictions:
- id (primary key)
- user_id (foreign key to Users)
- game_id (foreign key to Games)
- home_score
- away_score
- points (integer, default 0)
- created_at
- updated_at

### Tournaments:
- id (primary key)
- name
- start_date
- end_date
- created_at
- updated_at

### Teams:
- id (primary key)
- name
- created_at
- updated_at

### Winners:
- id (primary key)
- user_id (foreign key to Users)
- tournament_id (foreign key to Tournaments)
- points
- created_at
- updated_at


