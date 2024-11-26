# 🎥 FilmWebsite_IT3180_BE

## Capstone Project of **Introduction to Software Engineering** - Group 21

---

### 🛠️ **Setting Up XAMPP on Port 3306**

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

### 📸 **Screenshot**
<img src="https://github.com/khnhk0ogei04/FilmWebsite_IT3180_BE/blob/main/BlockedPort.png" alt="Blocked Port Screenshot" width="600">

---

#### **Notes:**
- Replace \`3306\` with your specific blocked port if it differs.
- Replace \`12345\` with the Process ID returned by the \`netstat\` command.
- Ensure **MySQL** is properly started after clearing the blocked port.

---
