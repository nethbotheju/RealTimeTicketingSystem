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


> **IMPORTANT NOTICE**
>   
> **System Startup Sequence:** To ensure optimal performance and prevent unexpected behavior, it is crucial to initiate the system components in the following order:
> 1.  **Frontend (GUI)**
> 2.  **Backend**
> 3.  **CLI**
>     
> Starting the components in any other order may result in errors or system malfunctions. Please adhere to this sequence for correct operation.


# System Usage Instructions

To ensure proper functionality, please initiate the system components in the following order: **CLI, Backend, Frontend**.

### 1. CLI Configuration

   *   Begin by navigating to the command-line interface (CLI).
   *   The CLI will prompt you to enter the required configuration parameters. Please provide the necessary values to proceed.

### 2. System Initialization

   *   **After** completing the CLI configuration, you can start the system in one of two ways:
   *   **GUI:** Use the "Start" button within the frontend graphical user interface (GUI).
   *    **CLI:** Execute the command `start` within the CLI terminal.

### 3. System Shutdown

   *   To halt the system, you have two options:
   *   **GUI:** Use the "Stop" button within the frontend GUI.
   *    **CLI:** Execute the command `stop` within the CLI terminal.

### 4. System Reset

   *   To restore the system to its initial state, utilize the "Reset" button available within the frontend GUI.

### 5. Configuration Parameter Updates

   *   To modify the system's configuration parameters:
   *   Navigate to the configuration form within the frontend GUI.
   *    Update the desired parameters.
   *    Submit the form to apply the changes. This will update the system with the new configuration.
