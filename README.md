# springboot-league

## Purpose
The root purpose was to have a first try at a proper Java web app, without an externally predefined task.
The idea was to build a league management system to manage the results of matches between a set of teams.

The user actions and resposibilites would include:
- registration of teams
- looking up their league standings
- looking up their matches
- input of their results

The actions and resposibilites of admins would include:
- registration of season rulesets, concerning the reward of win/draw/loss
- management of teams enrolled in a season
- registration of matches between teams
- supervising input results 

On top of this 'business logic', I wanted to have a basic user management system with registration, login, logout and different roles. And below a database to handle whatever persistent data would arise.

## Understanding The App

### Structure

I tried to stick to the layered spring architecture with the following layers:

- Presentation: The Thymeleaf template engine is used to create simple forms and tables to communicate information with the user and the bootstrap framework to refine the visuals. Data exchange with the backend is handled by several controllers, related to the most important models, the authentication and landing page.
- Business: Conists of several services, related several models. On the one hand the conversion of input data into model objects, passing them onto the persistence layer and whatever side effects their persistence management might create. For example the deletion of a match will require the nullification of its resulted league points.

### Spring Starter Dependencies
- Spring Web
- Srping Boot DevTools
- Spring Data JPA
- Spring Security
- MySQL Driver
- Thymeleaf

### Database
I used a MySQL database. With the following tables and relations. The relations were added as needed.

![alt text](https://github.com/JulianEichen/springboot-league/blob/main/pictures/db_users.png?raw=true)

'users' and 'roles' offer very basic user management and security functionality and 'teams' is the backbone of the user related business logic. A user can own several teams, which track the relevant league and match statistics.

![alt text](https://github.com/JulianEichen/springboot-league/blob/main/pictures/db_matches.png?raw=true)

'matches' track the meetings and the related outcome of teams. 'results' were made into their own class, because an angreement functionality was needed. A result will only be further processed into league statistics, if it contains consensual input. 

![alt text](https://github.com/JulianEichen/springboot-league/blob/main/pictures/db_seasons.png?raw=true)

'seasons' contain whatever rulesets the admin defines. 

## Usage

### Running The App Locally
Springboot-League is built with Maven and uses a MySQL database. Before building and running the app from the command line, you need to create a MySQL database called 'leaguedb' or adjust the datasource properties in the 'application.properties' file. Assuming the use of windows you can then use the following commands:

```
git clone https://github.com/JulianEichen/springboot-league
mvnw clean package
java -jar target/springboot-league-0.0.1-SNAPSHOT.jar
```

And finally access the app through [http://localhost:8080/](http://localhost:8080/).

### Navigation

Navigation happens primarily through the navigation bar, which can take two major shapes.

![alt text](https://github.com/JulianEichen/springboot-league/blob/main/pictures/navbar_out.png?raw=true)

 A logged out user only has the options to register or login.

![alt text](https://github.com/JulianEichen/springboot-league/blob/main/pictures/navbar_in.png?raw=true)

While logged in, every user has the 'Season' drop down menu, wich leads to season standings, matchdays and the active season rules. The 'User' menu leads to match management, team registration and a listing of the users teams.  Additionally admin users get an 'Admin Actions' menu including season, team and match management options, explained further below.

### Season

The 'Season' menu options should be self explanatory and do not ask for any user input or interaction.

### User

#### 'Register Team' & 'Teams'

Under 'Register Team', the user cen register a unique team, which then needs to be enrolled by an admin. The user can see all his teams and their related statistics under 'Teams'.

#### 'My Matches'

![alt text](https://github.com/JulianEichen/springboot-league/blob/main/pictures/user_matches.png?raw=true)

'My Matches' shows all matches and their outcomes with teams owned by the user. If the user has not input a result yet, he can chose to do so clicking the 'Update' button, which leads to the following form:

![alt text](https://github.com/JulianEichen/springboot-league/blob/main/pictures/user_matchupdate.png?raw=true)

This form can only be submitted once! If both users input the same result, it gets further processed into league statistics for the respective team. If they input different results, the outcome stays 'TBA' and an admin has to reset the match or put in a result manually. 

### Admin Actions

#### 'All Season Rule Sets' & 'Register Season'

With 'Register Season' and admin can register the season rulesets, which are shown under 'All Season Rule Sets'. The admin must set one ruleset to 'active', which then defines how the outcomes of matches are processed into league points etc..

#### 'Enroll Teams'

![alt text](https://github.com/JulianEichen/springboot-league/blob/main/pictures/admin_teams.png?raw=true)

Once a team is registered by a user, the admin must set its 'enrolled' status to TRUE to have it show up under the current season standings. Setting the status to FALSE will not remove its related statistics from the league and simply change its visibility in the related table.
If a season is over, the admin can reset the statistics of a team for a new one.

#### 'All Matches' & 'Register Matches'

![alt text](https://github.com/JulianEichen/springboot-league/blob/main/pictures/admin_matches.png?raw=true)

This table lists all registered matches. It shows the matches results, whether they are 'TBA (to be announced/awaiting user input)' or if there is a conflict between the inputs of the users.

![alt text](https://github.com/JulianEichen/springboot-league/blob/main/pictures/admin_matches_details.png?raw=true)

'Details' shows the inputs of both users of a given match. 'Reset' resets the matches result statistics and also any derived league points, without deleting the match. 'Delete' completely delets it and any derived league points. 

![alt text](https://github.com/JulianEichen/springboot-league/blob/main/pictures/admin_matches_update.png?raw=true)

'Update' allows the admin to manually change the result, if there is a problem with the users or change other details, if there is faulty input on the admins side. 
