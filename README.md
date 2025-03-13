# 🗳️ Electronic Voting System  

## 📌 About This Project    

This is a **Voting System** developed to allow users to **cast votes, tally results, and manage elections online securely.** 
The system is designed to ensure **fairness, transparency, and ease of use** for all voters.  

This is a project I originally worked on and contributed to in 2021.  
Due to the **accidental deletion of my previous GitHub** account, I am **re-uploading** it here.

## 💡 Why Re-Upload?  
Since my old GitHub was deleted, I wanted to ensure this project remains accessible in my portfolio. 

## 🔍 Project Details
- **Project Name:** Electronic Voting System
- **Primary Language:** Java
- **Features:**
  - Voter Registration and Authentication
  - Candidate Management
  - Secure Vote Casting
  - Real-time Result Tallying

## 🛠️ Technologies Used
### **Programming Language**
- Java

### **Database Management**
- MySQL

### **User Interface**
- JavaFX (for desktop applications)
- JSP/Servlets (for web applications)

### **Build Tools**
- Maven or Gradle (depending on your setup)

## 🚀 Getting Started
To set up and run the project locally, follow these steps:

### **1️⃣ Clone the Repository**
```bash
git clone https://github.com/kanagajayasankar/Voting_System.git
cd Voting_System
```
### **2️⃣ Set Up the Database** 
    -Install MySQL: Ensure MySQL is installed on your machine.
    -Create Database: Create a database named voting_system.
    -Import Schema: Use the provided SQL script (voting_system.sql) to set up the database schema
```sql
mysql -u [username] -p voting_system < voting_system.sql
```
    Replace [username] with your MySQL username.



### **3️⃣Configure Database Connection** 
Update Configuration: Modify the database connection settings in the project to match your MySQL credentials. This is typically found in a configuration file or within the codebase:
```java
// Example JDBC URL
String url = "jdbc:mysql://localhost:3306/voting_system";
String user = "your_username";
String password = "your_password";
```

### **4️⃣Build the Project ** 
#### Using Maven:
```bash
mvn clean install
```
#### Using Gradle
```bash
gradle build
```

### **5️⃣ Run the Application** 
```bash
java -cp target/voting_system.jar com. example.Main

```

### **▶️ How to Use the Application**
    
 **Voter Registration:**  Users can register by providing necessary details and will receive login credentials.
 **Login:** Registered voters can log in using their credentials.
 **Vote Casting:** Authenticated voters can cast their votes for available candidates.
 **View Results:** Once voting concludes, results can be viewed in real time.
 
### **🔒 Security Features**
**Authentication:** Ensures only registered voters can access the voting system.
**Data Validation:** Prevents invalid or malicious data entries.
**Secure Connections:** Implements HTTPS for secure data transmission (if applicable).

### **🤝 Contributing**
Contributions are welcome! Please fork the repository and submit a pull request to suggest improvements.

### 📜 License
This project is open-source and available under the MIT License.

