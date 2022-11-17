# springboot-league

## Purpose
The root purpose was to have first try at a proper Java web app, without an externally predefined task.
The idea for the app was to buil a league management system to manage the results of matches between a set of teams.

The user actions and resposibilites would include:
- registration of teams
- look up their league standings
- look up their matches
- input the results of their matches

The actions and resposibilites of admins would include:
- registration of season rulesets, concerning the reward of win/draw/loss
- management of teams enrolled in a season
- registration of matches between teams
- supervision input results 

On top of this 'business logic', I wanted to have a user management system with registration, login, logout and different roles. And below a database to handle whatever persistent data would arise.

## Database
I used a MySQL database. With the following tables and relations. The relations were added as needed.

![alt text](https://github.com/JulianEichen/springboot-league/blob/main/pictures/db_users.png?raw=true)

'users' and 'roles' offer very basic user management and security functionality and 'teams' is the backbone of the user related business logic. A user can own several teams, which track the relevant league and match statistics.

![alt text](https://github.com/JulianEichen/springboot-league/blob/main/pictures/db_matches.png?raw=true)

'matches' track the meetings and the related outcome of teams. 'results' were made into their own class, because an angreement functionality was needed. A result will only be further processed into league statistics, if it contains consensual input. 

![alt text](https://github.com/JulianEichen/springboot-league/blob/main/pictures/db_seasons.png?raw=true)

'seasons' contain whatever rulesets the admin defines. 

## Usage
