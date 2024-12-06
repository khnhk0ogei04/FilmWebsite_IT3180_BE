# 🎥 FilmWebsite_IT3180_BE

## Capstone Project of **Introduction to Software Engineering** - Group 21

---
### INTRODUCTION
#### This project use Java and MySQL for backend
---
### 🛠️ **Setting Up XAMPP on Port 3306**
<code style="color: aqua">**Running from scratch**:</code>
- Setup your user information
- Setup database: **MySQL** with settings: 
    ```
    Display name: MySQL
    Host: localhost
    Port: 3306
    Database name: web_film_update
    Username: root
    Password: 
    ```

#### If **Port 3306** is **blocked**, follow these steps:

##### 1. Open **Command Prompt**.
##### 2. Run the following commands:

   ```sh
   netstat -ano | findstr :3306
   ```

   This will display the process currently using **Port 3306**. For example:

   ```
   TCP    0.0.0.0:3306       0.0.0.0:0              LISTENING       12345
   ```

#####  3. Use the **Process ID** (12345 in the example above) to terminate the process:

   ```sh
   taskkill /F /PID 12345
   ```

##### 4. Restart **XAMPP**, and the issue should be resolved.

---
##### 5. Add database NewDatabase.sql 
### 📸 **Screenshot**
#### I. Fix MySQL Error
<img src="https://github.com/khnhk0ogei04/FilmWebsite_IT3180_BE/blob/main/1.BlockedPort.png" alt="Blocked Port Screenshot" width="600">
---

#### **Notes:**
- Replace **3306** with your specific blocked port if it differs.
- Replace **12345** with the Process ID returned by the `netstat` command.
- Ensure **MySQL** is properly started after clearing the blocked port.

---

## 👥 Contributors

| **Student ID** | **Full Name**       |
|----------------|---------------------|
| Nguyen Ngoc Phuc | 20220041          |
| Nguyen Duy Khanh | 20225019          |
| Vi Hung Duc      | 20224836          |
| Ha Huy Hoang     | 20224988          |
| Luong Thai Khang | 20224866          |
| Dinh Bao Phuc    | 20224889          |


