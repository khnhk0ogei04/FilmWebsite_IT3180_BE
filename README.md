# 🎥 FilmWebsite_IT3180_BE

## Capstone Project of **Introduction to Software Engineering** - Group 21

---

### 🛠️ **Setting Up XAMPP on Port 3306**

#### If **Port 3306** is **blocked**, follow these steps:

1. Open **Command Prompt**.
2. Run the following commands:

   ```sh
   netstat -ano | findstr :<port_number>
   taskkill /F /PID <process_id>
#### Example:
If Port <span style="color:red; font-size:16px;">3306</span>is blocked, the command output might look like this:

TCP    0.0.0.0:3306       0.0.0.0:0              LISTENING       12345
Run the following command:
   ```sh
   taskkill /F /PID 12345
   ```

#### 📸 Screenshot
<img src="https://github.com/khnhk0ogei04/FilmWebsite_IT3180_BE/blob/main/BlockedPort.png" alt="Blocked Port Screenshot" width="600">
