# 🎥 FilmWebsite_IT3180_BE

## Capstone Project of **Introduction to Software Engineering** - Group 21

---

### INTRODUCTION

This project is built using **Java** for the backend and **MySQL** for database management.

---

### 🛠️ Setting Up the Project

#### **1. Prerequisites**
- Install **XAMPP** to manage MySQL and Apache servers.
- Ensure the MySQL port is set to **3306** by default.

#### **2. Configuring MySQL Database**
- Use the following database settings:
  ```
  Display Name: MySQL
  Host: localhost
  Port: 3306
  Database Name: web_film_update
  Username: root
  Password: 
  ```

#### **3. Running the Project**
- Import the `NewDatabase.sql` file to initialize the database.
- Start the MySQL service using XAMPP.

---

### ⚙️ Handling Port 3306 Blocked Issue

If **Port 3306** is blocked, follow these steps to resolve it:

1. **Identify the Process Using Port 3306**:
   - Open **Command Prompt** (`Win + R`, type `cmd`, and press Enter).
   - Run the command:
     ```sh
     netstat -ano | findstr :3306
     ```
   - Example output:
     ```
     TCP    0.0.0.0:3306       0.0.0.0:0              LISTENING       12345
     ```

2. **Terminate the Blocking Process**:
   - Use the Process ID (e.g., `12345` in the example above):
     ```sh
     taskkill /F /PID 12345
     ```

3. **Restart XAMPP**:
   - Open XAMPP Control Panel, start MySQL, and verify that the port is now available.

4. **Adjust Port Settings if Needed**:
   - If Port 3306 is permanently unavailable, update the database configuration in your project to match the new port.

---

### 📸 Screenshots

#### **1. Resolving MySQL Blocked Port**
Below is a screenshot illustrating the process to fix the blocked port:

![Blocked Port Screenshot](https://github.com/khnhk0ogei04/FilmWebsite_IT3180_BE/blob/main/1.BlockedPort.png)

#### **2. Demo Project - Login Page**
Here’s the Login Page of the project:

![Login Page Screenshot](https://github.com/khnhk0ogei04/FilmWebsite_IT3180_BE/blob/main/images/Screenshot%202024-12-10%20043137.png)

---

### 👥 Contributors

| **Student ID**   | **Full Name**       |
|------------------|---------------------|
| 20220041         | Nguyen Ngoc Phuc   |
| 20225019         | Nguyen Duy Khanh   |
| 20224836         | Vi Hung Duc        |
| 20224988         | Ha Huy Hoang       |
| 20224866         | Luong Thai Khang   |
| 20224889         | Dinh Bao Phuc      |

---

### 📄 Additional Notes
- Replace **3306** with the blocked port if it differs.
- Replace **12345** with the Process ID returned by the `netstat` command.
- Ensure MySQL is restarted properly after clearing the blocked port.

---

Guidance for setting up and running the project

