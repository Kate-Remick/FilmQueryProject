# FilmQueryProject
##Description:
This project is a practice in database access using mysql and Java, utilizing a database that includes a table of films and related information concerning a group of rental stores for these films. The program allows a user to access information for a film given a numerical id or a keyword, which would be matched to a film or films using the title and description. Using data from the mysql query, a film object (id query) or list of film objects (keyword query) are built and returned for the user. The user is then presented with an option to see more about the film(s) if they would like, giving them details about length, special features and rental information.

## Technologies Used:
* mysql
* Java
* Eclipse
* Maven
* Tomcat (server)
* Git/GitHub
* MAMP


## Lessons Learned
* I ran into some difficulty in my menu/user interface due to how I had constructed the methods for user input utilizing the scanner. All menus are displayed in 'while' loops, with a try/catch inside of them. This should allow users to continue with the program even when they input unexpected values that cause an exception. Unfortunately, my earlier build caused an infinite loop when an exception was thrown, due to some of the quirks of the scanner. I remedied this by changing the scanner method I was using, allowing the program to run as intended.

     
