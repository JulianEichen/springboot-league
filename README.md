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
