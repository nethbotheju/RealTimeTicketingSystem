# Setup Instructions
1.  **Extract the ZIP File:**
    *   Extract the contents of the provided ZIP file to your desired location.


## Frontend (Angular) Setup

1.  **Navigate to the Client Folder:**
    *   Open your terminal or command prompt.
    *   Use the `cd` command to navigate into the `client` directory within the extracted folder:
        ```bash
        cd client
        ```


2.  **Install Packages:**
    *   Run the following command to install the necessary Node.js packages for the Angular application:
        ```bash
        npm install
        ```

3.  **Run the Angular Application:**
    *   Once the packages are installed, start the Angular development server using:
        ```bash
        ng serve
        ```
    *   The Angular application will now be running and accessible in your web browser, at `http://localhost:4200`.

## Backend (Java Spring Boot) Setup

1.  **Navigate to the Server Folder:**
    *   Open a new terminal or command prompt.
    *   Use the `cd` command to navigate to the `server` directory within the extracted folder:
        ```bash
        cd server
        ```

2.  **Open Server Folder in IntelliJ IDEA:**
    *   Launch IntelliJ IDEA.
    *   Click on "Open" and select the `server` folder.  This will import the project as a Java project.

3.  **Run the Server Application:**
    *   In IntelliJ IDEA, navigate to the `src/main/java/com/example/server` directory.
    *   Locate the `ServerApplication.java` file.
    *   Right-click on this file and select "Run 'ServerApplication'".
    *   IntelliJ IDEA will automatically download any required dependencies (Maven dependencies) and start the Spring Boot application.

4.  **Access the Backend:**
    *   Once started, the backend application will be running on a local host and accessible through defined endpoints, at `http://localhost:8080`.

## CLI (Java) Setup

1.  **Navigate to the cli Folder:**
    *   Open a new terminal or command prompt.
    *   Use the `cd` command to navigate to the `cli` directory within the extracted folder:
        ```bash
        cd cli
        ```
2.  **Open Cli Folder in IntelliJ IDEA:**
    *   Launch IntelliJ IDEA.
    *   Click on "Open" and select the `cli` folder.  This will import the project as a Java project.

3.  **Run the Cli Application:**
    *   In IntelliJ IDEA, navigate to the `src/main/java/org/example/` directory.
    *   Locate the `Main.java` file.
    *   Right-click on this file and select "Run 'Main'".
    *   IntelliJ IDEA will automatically download any required dependencies (Maven dependencies) and start the Cli application in the Intellij IDEA terminal.
