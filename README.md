# SACET Travel - Travel Planning Application

## Overview
SACET Travel is a comprehensive travel planning application that helps users discover destinations, book hotels, and plan their itineraries. The application provides detailed information about various destinations, including attractions, weather conditions, and recommended activities.

## Features

### For Users
- **User Authentication**
  - Sign up and login functionality
  - Secure password management
  - Role-based access (User/Admin)

- **Destination Exploration**
  - Browse various travel destinations
  - View detailed information including:
    - Best time to visit
    - Estimated budget
    - Things to see
    - Weather information
    - Travel routes
    - Recommended duration
    - Trip type (family, adventure, romantic)
    - Attractions

- **Hotel Booking**
  - Search and browse hotels
  - View hotel details:
    - Price per night
    - Amenities
    - Room types
    - Check-in/out times
    - Ratings and reviews
    - Available facilities (WiFi, Parking, Pool, Restaurant)
  - Make and manage bookings

- **Itinerary Planning**
  - Day-wise activity planning
  - Morning, afternoon, and evening activity suggestions
  - Accommodation recommendations
  - Transportation details
  - Meal suggestions
  - Daily cost estimates

### For Administrators
- **Comprehensive Dashboard**
  - Manage destinations
  - Manage hotels
  - Handle bookings
  - Create and edit itineraries

- **Booking Management**
  - View all bookings
  - Track booking status
  - Process cancellations
  - Monitor revenue

## Technical Stack

### Backend
- Java Spring Boot
- Spring Security
- Spring Data JPA
- MySQL Database

### Frontend
- Thymeleaf templating engine
- TailwindCSS for styling
- JavaScript for interactive features
- Font Awesome icons

## Setup Instructions

1. **Prerequisites**
   - Java JDK 17 or higher
   - Maven
   - MySQL

2. **Database Setup**
   ```sql
   CREATE DATABASE sacet_travel;
   ```

3. **Configuration**
   - Update `application.properties` with your database credentials
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/sacet_travel
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. **Access the Application**
   - Open a web browser and navigate to `http://localhost:8080`
   - Default admin credentials:
     - Username: admin@sacet.com
     - Password: admin123

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/sacet/travelplanner/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── model/
│   │       ├── repository/
│   │       └── service/
│   └── resources/
│       ├── static/
│       └── templates/
└── test/
```

## Security Features
- Password encryption
- Session management
- Role-based access control
- Form validation
- CSRF protection

## Contributing
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License
This project is licensed under the MIT License - see the LICENSE file for details. # travel-planner-java-spring
