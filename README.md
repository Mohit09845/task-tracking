# Task Tracking Backend (Servlet + Tomcat + Gson)

This is a Java Servlet-based backend application for managing:

- Programs  
- Projects  
- Users (Individuals)  
- Tasks  
- Pending task counts  

Data is stored in a JSON file inside the Tomcat runtime directory.

---

## Path Where Your File Will Be Stored (SmartTomcat)
C:\Users\<your-username\.SmartTomcat\backend\backend\data\prjdata.json

> **NOTE:**  
> To check the exact file path in your system, add the following line inside `DataService`:
>
> ```java
> System.out.println("FILE PATH: " + FILE_PATH);
> ```
> 
---

## Importing Data (POST API)
Send data using **raw JSON** in the request body.

---

## Working
- When the server starts, `DataService` checks if the file exists at `/data/prjdata.json`.
- If it exists → loads data.
- If it does not exist → creates an empty `DataStore`.
- When the POST import API is called → the file is updated.
