# Tvservice

provides information about a tv show and recommends random tvshow

## Description

tvservice suggests user a random tvservice , fetches currently streaming tvshows 
and provides user the ability to like and save a tvshow.
each tvshow contains useful information such as summary , genre , popularity, and so on.
## Getting Started

### Dependencies

* java 11
* maven 
* all further dependences are included in pom.xml

### Installing

1. git clone https://github.com/DhineshL/tvservice.git
2. git checkout tvservice 
3. go to application.properties in src/main/java/resource folder
4. configure your database connection
5. mvn install
6. mvn spring-boot:run 
7. go to http://localhost:8080/
8. If the service is running you should see a login page


### Architecture
![tvservice (1)](https://user-images.githubusercontent.com/60684164/162636264-49896e42-fac9-4abd-8158-7e9e3fa928d9.png)

## Features
1. An endpoint */register* is provided for users to register themselves (note: for easier accessibility users can use simple passwords)
2. After registration user can */login* using the credentials
3. User can like , save , remove  tvshows
4. Top page represents the top liked tvshows among the application
5. An endpoint *user/suggest* is provided to suggest tvshow to an user
6. Each suggested tvshow can never be repeated 
7. Azure sql database is used to store the tvshow entries
8. Azure key vault is used to store the database crendentials
9. Very fast inmemory caffine cache is used to cache frequent response to tvmaze api


## Home Page
![image](https://user-images.githubusercontent.com/60684164/162637166-23f825b6-c470-4c5b-9cd0-a475761e7233.png)


## Suggest Page
![image](https://user-images.githubusercontent.com/60684164/162637249-60a4f79c-36cd-4066-a24b-17e8e4b15e6a.png)


## Saved Shows
![image](https://user-images.githubusercontent.com/60684164/162637296-4590b1de-d12b-4a71-ae6e-fcc0364d7a57.png)


## Top 

![image](https://user-images.githubusercontent.com/60684164/162637333-4038f13c-b69a-415f-9044-42e6d0c5dd90.png)


## Authors

Contributors names and contact info

Dhinesh L 
[LinkedIn](https://www.linkedin.com/in/dhineshbharathi/)



