# Bus Booking Application - Endpoint Testing Commands

## Prerequisites
1. Start the application: `mvn spring-boot:run`
2. Ensure MySQL database is running with `busbooking_db` database
3. Application runs on: `http://localhost:8080`

## 🔐 User Management Endpoints (Browser Testing)

### User Registration & Login
- **GET** `http://localhost:8080/register` - Registration page
- **GET** `http://localhost:8080/login` - Login page
- **GET** `http://localhost:8080/dashboard` - Dashboard (requires login)

## 🚌 Bus Management API Endpoints

### 1. Get All Buses
```bash
curl -X GET http://localhost:8080/api/buses
```

### 2. Get Bus by ID
```bash
curl -X GET http://localhost:8080/api/buses/1
```

### 3. Create New Bus
```bash
curl -X POST http://localhost:8080/api/buses \
  -H "Content-Type: application/json" \
  -d '{
    "busName": "Express Bus 101",
    "source": "Mumbai",
    "destination": "Delhi",
    "departureTime": "2024-01-20T08:00:00",
    "arrivalTime": "2024-01-20T20:00:00",
    "totalSeats": 40,
    "fare": 1500.0
  }'
```

### 4. Update Bus
```bash
curl -X PUT http://localhost:8080/api/buses/1 \
  -H "Content-Type: application/json" \
  -d '{
    "busName": "Updated Express Bus 101",
    "source": "Mumbai",
    "destination": "Delhi",
    "departureTime": "2024-01-20T09:00:00",
    "arrivalTime": "2024-01-20T21:00:00",
    "totalSeats": 45,
    "fare": 1600.0
  }'
```

### 5. Delete Bus
```bash
curl -X DELETE http://localhost:8080/api/buses/1
```

## 🎫 Booking Management API Endpoints

### 1. Get All Bookings
```bash
curl -X GET http://localhost:8080/api/bookings
```

### 2. Get Booking by ID
```bash
curl -X GET http://localhost:8080/api/bookings/1
```

### 3. Create New Booking (requires existing user and bus)
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "seatsBooked": 2,
    "bookingDate": "2024-01-19T10:00:00",
    "status": "CONFIRMED",
    "userEmail": "test@example.com",
    "bus": {"id": 1},
    "user": {"id": 1}
  }'
```

### 4. Update Booking
```bash
curl -X PUT http://localhost:8080/api/bookings/1 \
  -H "Content-Type: application/json" \
  -d '{
    "seatsBooked": 3,
    "status": "CANCELLED",
    "userEmail": "test@example.com"
  }'
```

### 5. Delete Booking
```bash
curl -X DELETE http://localhost:8080/api/bookings/1
```

## Expected Responses

### Success Responses
- **200 OK**: Successful GET, PUT requests
- **201 Created**: Successful POST requests
- **204 No Content**: Successful DELETE requests

### Error Responses
- **400 Bad Request**: Validation errors, missing data
- **404 Not Found**: Resource not found
- **401 Unauthorized**: Authentication required

## Testing with Postman

1. Import the collection or create requests manually
2. Set base URL: `http://localhost:8080`
3. For POST/PUT requests, set Content-Type: `application/json`
4. Add JSON body for POST/PUT requests

## Database Testing

### Check Database Tables
```sql
-- Connect to MySQL and use busbooking_db
USE busbooking_db;

-- Check all tables
SHOW TABLES;

-- Check users table
SELECT * FROM users;

-- Check buses table  
SELECT * FROM bus;

-- Check bookings table
SELECT * FROM booking;
```

## Common Issues & Solutions

### 1. Connection Refused
- Ensure application is running on port 8080
- Check if MySQL is running and accessible

### 2. Validation Errors
- Ensure required fields are provided
- Check data types (dates, numbers)
- Verify field constraints (email format, positive numbers)

### 3. 404 Not Found
- Verify endpoint URLs are correct
- Check if resources exist in database
- Ensure proper HTTP method (GET, POST, PUT, DELETE)

### 4. Database Issues
- Check MySQL connection in application.properties
- Verify database `busbooking_db` exists
- Check if tables are created properly
