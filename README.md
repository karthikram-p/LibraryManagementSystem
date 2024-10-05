# Library Management System

A Library Management System built using Java, Swing, AWT, and SQL. This application provides a graphical user interface for managing library operations, including user authentication, book management, and user interactions.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [File Structure](#file-structure)

## Features

- User Login and Signup
- Librarian and User Menus
- Book Management (Add, Remove, View)
- User Management (View, Update)
- Borrow and Return Books
- Database connectivity using SQL

## Technologies Used

- **Java**: The primary programming language.
- **Java Swing**: For building the graphical user interface (GUI).
- **Java AWT**: For basic GUI components and layout management.
- **SQL**: For database management, using JDBC for database connectivity.
- **MySQL**: (or any other relational database) for storing user and book data.

## Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/karthikram-p/LibraryManagementSystem.git

2. **Navigate to the project directory**:
  
   ```bash
   cd LibraryManagementSystem
   
3. **Compile the Java files**: Navigate to the src/LMS directory and compile the Java files:

    ```bash
    cd src/LMS
    javac *.java
    
4. **Run the application**:

    ```bash
    java LibraryManagementSystem
    
5. **Set up the database**: Ensure you have MySQL (or your preferred SQL database) installed and create a database named library. Modify the database connection settings in DataBaseManagement.java to match your database configuration.

## Usage
1. Launch the application and log in using existing credentials or sign up as a new user.
2. Users can view available books, borrow books, and return them.
3. Librarians can add, remove, and view books, as well as manage user accounts.

   
## File Structure
LibraryManagementSystem
├── bin
│   └── LMS
│       ├── ... (compiled .class files)
├── src
│   └── LMS
│       ├── DataBaseManagement.java
│       ├── LibrarianMenu.java
│       ├── LibraryLoginPage.java
│       ├── LibraryManagementSystem.java
│       ├── LibrarySignUpPage.java
│       └── UserMenu.java
├── logo.png
└── README.md
