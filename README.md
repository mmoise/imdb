# IMDB 2018 Titles

## Getting Started
### System Requirements
* JAVA 8+
* mvn

#### Filtering the source files
The source files for the data can be found here: [https://datasets.imdbws.com/](https://datasets.imdbws.com/)<br/>
You will need to download the below files

* title.basics.tsv.gz
* title.episode.tsv.gz
* title.principals.tsv.gz
* title.ratings.tsv.gz
* name.basics.tsv.gz

Navigate to the `src/main/resources/imdb/original` directory and delete all of the files except the **filter_data.sh**
file. <br/> Extract these files and place them in the `src/main/resources/imdb/original` directory. **Do not change the
 file names.** Execute the following
command to run the filter data script from the `src/main/resources/imdb/original` directory:<br/> **`sh filter_data.sh`**<br/> This takes 
approximately 2.5 mins to complete. Read the file to uncover the implementation details. After the script completes execution 
the following files will now appear in the `src/main/resources/imdb/filtered` directory

* 2018actors.tsv
* 2018cast.tsv
* 2018episodes.tsv
* 2018ratings.tsv
* 2018titles.tsv

## Running the app
Using Maven: `mvn spring-boot:run` from the root directory. This will take approx 75 secs to start up

### Run Tests
`mvn test` from the root directory

### Viewing the Database contents
Console URL: `http://localhost:8080/h2-console/login.jsp`<br/>
JDBC URL: `jdbc:h2:mem:imdb`<br/>
User Name: `sa`<br/>
Password: blank

### API Documentation
There are three endpoints defined in the service:<br/> 
`http://localhost:8080/titles` - returns all titles (is paginated by default (20 records)<br/>
`http://localhost:8080/titles/{titleid}` - returns the title with teh specified titleid<br/>
`http://localhost:8080/ratings` - assigns a new rating for each title in the database<br/>
`http://localhost:8080/actuator/health` - provides simple health check
Additional documentation can be found here:<br/>
`http://localhost:8080/swagger-ui.html#/`

# Movie API Solution
## Part 1
### Problem
Build an application in Java that pulls movies, their ratings, and cast lists from IMDB. To limit the
scope, please pull only movies released in 2018.

Additional Details:

* IMDB provides details on how to download the data: http://www.imdb.com/interfaces
* You may persist the data however you choose
* APIs is sufficient

### Solution
I chose to implement this as a microservice because the requirement was to store the data and it made sense to have it as a
service where there are endpoints that allow access to the data. For this, I chose Spring Boot as my framework because of 
the ease in which it allows you to quickly start coding, its embedded web server, and its handling of dependencies and 
boiler plate code. I looked at the data and saw that it was relational and unique identitfiers and foreign keys were 
present for most of the datasets so I decided to choose a RDBMS. I chose H2 for my database because it is a relational, 
in-memory database that would enable high performance for read/writes because it is in memory. The downside being that the 
data will need to be loaded every single time that the app is started. However, I felt that the performance hit taken during startup 
 greatly outweighed the performance hit duringI also decided to use an ORM 
in order to manage the database connections, schema creation, and it abstracts the database layer. I chose Hibernate because 
the learning curve is short, its widely used, and it is supported by Spring Boot. However, I rried to stick withthe javax.persistence
annotations instead of using the Hibernate annotations when possible in order to try to abstract from the ORM implementation. 

To filter the files for only 2018 data I decided that the filtering should take place outisde of the java app. I chose to 
do this as a bash script because of the speed of performing this in bash (both performance and lines of code) vs performing 
the filtering in Java. The script creates a new file containing the 2018 data for each of the applicable datasets. 

#### Approach
##### Strategy
Some of these were confirmed by Sudhir and others were deduced by investigating the data and the structure of the datasets

* I'm only concerned with the data relevant to the problem.
* I do not need **all** of the data in a specific dataset
* Crew information is included in Cast and does not need to be loaded
* The titleId will be used as a Foreign key on the non-title datasets
* Episodes will be stored as a List on the Title object
* Episodes do not have an **explicitly** stated unique identifier. A composite primary key will be needed consisting of 
 titleId, seasonNumber, and EpisodeNumber to ensure a unique row
* Cast represents the relationship between an Actor and a Title. It will not be its own Entity but instead be represented
as a Join table between Title and Actor. It will be stored as a list of Actors on the Title object
* The default column length of 255 is enough for all string values

#### Implementation Steps
1. Determined that I only needed to import the **title.basics.tsv**, **title.episode.tsv**, **title.principals.tsv**, 
**title.ratings.tsv**, **name.basics.tsv** files. Any other files were additional and unnecessary information
2. Created an ERD diagram so i can map out the needed entities and relationships (src/main/resources/diagrams/titles_erd.jpg)
3. Created the entities in java using the javax.persistence annotations (Title, Episode, Actor, Rating
4. Implemented the relationships using the javax.persistence annotations
5. Wrote test code and test route to test code
6. Noticed that Ratings object doesn't need to be an entity and should be an attribute of both Titles and Episodes. This 
will reduce the complexity of the schema, the performance of queries (less joins) and the performance of the data load
7. Updated my ERD diagram (src/main/resources/diagrams/titles_erd_v2.jpg) and refactored to use rating as an attribute
8. Created the bash script to retrieve only the 2018 data
9. Created DataLoad service and another service that implements `CommandLineRunner` in order to ensure that data load happens 
during app start up
10. To improve performance of the data load I enabled Hibernate batch saving and refactored to allow saving lists of entities 
instead of one at a time
11. Created a restful controller to accept http requests to get all titles and get a single title
12. Noticed the performance hit on the get all titles so I refactored to implement pagination
13. Created Junit tests along with test files

## Part 2
### Problem
For TV series, the ratings on episodes were pretty accurate compared to the season ratings.
Calculate the rating for a season based on its episodes. The way to calculate is simply add
ratings from all episodes within a season, and the number becomes the season’s rating.
Please implement method to compute ratings using above algorithm.
Nice to have: If you think there is a better way to compute the ratings, go ahead and implement it.
### Solution
While inspecting the data I notices that a large number of episodes do not have rating data. By using the suggested 
algorithm it would penalize those shows since they would have less ratings data to contribute to its sum. The reverse is 
also true. Shows with more episodes in a season (with ratings data) would have a higher rating and would appear to be a 
better reviewed show. I chose to use the average rating as my algorithm for this problem because it would eliminate the 
advantage of a show having more rated episodes and reward shows that consistently earn high ratings. Since I already have 
the episodes stored as List on the Title entity I will calculate the average of those episode ratings.

#### Strategy
* Only titles with a titleType of **tvSeries** will be considered as season
* There is only one season per year so season level ratings will be stored as the the rating on the Title record with the
titleType of tvSeries

#### Implementation Steps
1. Did a POC to verify that I can use Jackon annotations to hide the existing rating and label a method as the new rating value
2. Created a getCalculatedRating method using streams to iterate through the list of episodes and return the average rating, only 
if the titleType is tvSeries.
3. Tested it and realized that alot of the data has null value for ratings. Decided to default those values to zero since 
having a rating as zero is the same as having it as null. You cannot rate a show as zero.
4. Refactoring the data loading service to default null ratings to zero and filtering out ratings with zero values from 
the calculation

## Part 3
### Problem
The ratings for episodes are changing constantly, and we want to reflect those changes in our
data. Upgrade you design to synchronize and update the ratings, including re-calculating the
season-level ratings.
### Solution
Update the ratings values on both the Title and Episode entities.
#### Strategy
* Use a random number generator for the new ratings
* Create an endpoint that will update the ratings on demand
#### Implementation Steps
1. Create a randomizer service to generate and save ratings
2. Create a ratings controller to call the randomizer service and test
3. During testing I noticed that the generated rated had 2+ decimal places. I decided to refactor the code to limit to a
single decimal place
4. Created a proper response object for the route and updated from GET to POST. I decided to make it a POST route because it
 is not an idempotent operation and should be a POST as per best practices.

## Bonus
Identify areas where you can make the APIs performant and make suggestions/implement on
how you can improve them.
### Solution
There are a number of ways and areas to improve the performance:<br/>
### System Design
* Deploy application in a cluster with load balancing
* Deploy database in a cluster with specified read and write instances along with load balancing

### Database
* Horizontal Partioning of database - table sizes would be smaller which would result in faster database operations
* Flatten data and use NoSQL DB - Make use of Lists as a data type and store episode and actors directly on a document. 
The lack of extra tables would eliminate joins would increase performance and would increase the load time by reducing 
database operations
* Use DB Cacheing - Enable so that recently accessed objects are available in memory which would eliminate a trip to the 
database

### Data Loading
* Use threads to process the data loading in parallel
* Spring Batch (bypass Hibernate)
* Import data files directly to DB (via DB processing tool)
