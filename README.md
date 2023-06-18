# TimeTracker

` !! under development`
`missing features`
`- project security`

TimeTracker is a comprehensive time tracking application built with Java, Spring Boot, and Thymeleaf. It provides efficient tracking of employees' working hours, break times, and more, offering an easy-to-use and accurate way to monitor productivity.

## Technologies

Since Spring Boot 3.1 added docker-compose support I am utilizing it to run the application locally. The application is built with the following technologies:

* Java 17
* Gradle 8.1
* Spring Boot 3.1
* PostgreSQL via Docker

The only thing you need is Java 17/18/19 and Docker.
##### NOTE: Java 20 and above are not supported by Gradle 8.1

## Installing TimeTracker

To install TimeTracker, follow these steps:

1. Clone the repository
2. Navigate into the project directory
3. Start the application with 
```bash
./gradlew run
```
* Open your browser and navigate to [http://localhost:8080](http://localhost:8080)

## License

This project uses the MIT License - see the [LICENSE.md](./LICENSE.md) file for details.
