# Forum Application (MVC, Spring, Hibernate)
This is a web-based forum application built using the MVC architecture, Spring Framework, and Hibernate for ORM. The application allows users to register, create discussion topics, comment on topics, and manage their content. Administrators have additional privileges to manage users, topics, and comments.
## System overview
The forum application is designed to provide a platform for users to engage in discussions by creating topics and commenting on them. The application is built using:
  - **MVC Architecture:** Clear separation of presentation, business logic, and data access layers.
  - **Spring Framework:** For dependency injection, MVC, and security.
  - **Apache Tomcat:** As the application server. 
  - **Hibernate:** For ORM with an SQL database. 
  - **SQL Database:** Using MySQL for persistent data storage. 
  - **Redis for sessions:** Redis stored user sessions.
  - **Frontend Templating:** Using JSP, Thymeleaf, or Mustache.
## Actors
  - **Regular User:** Can register, create topics, comment on topics, and archive their own topics. 
  - **Administrator (Admin)**: Can manage forum content, invite new admins, and moderate user interactions.
## User Stories Summary
- **Registration with Email Confirmation:** Users can register and activate their accounts via email.
- **User Topic and Comment Management:** Users can create, comment on, and archive topics.
- **Admin Invitation and Registration:** Admins can invite new admins via email.
- **Admin Content Moderation:** Admins can manage topics and comments.
## How to Run App
### Prerequisites
- **Java Development Kit (JDK):** Version 11 or higher.
- **Apache Maven:** For building the project.
- **Docker:** For running the SQL database.
- **Apache Tomcat:** For deploying the application.
### Step 1: Run docker composer
Open cmd in project file and run:
```bash
docker-compose up --build
```
### Step 2: Create .env
Next step is to create `.env` file in resource directory based on `.env.example`. 
```yaml
FORUM_MAIL_USERNAME=your_email@example.com
FORUM_MAIL_PASSWORD=password
```
### Step 2: Configure `application.properties`
Set database connection, redis, configure email settings, liquibase and hibernate.
### Step 3: Build the Application and deploy on Apache Tomcat
- Build the WAR File
```bash
mvn clean package
```
- Copy generated WAR file to the webapps directory of Tomcat installation
```bash
cp target/forum-application.war /path/to/tomcat/webapps/
```
- Start tomcat server
```bash
/path/to/tomcat/bin/startup.sh
```
- Open your browser and navigate to `http://localhost:8080`