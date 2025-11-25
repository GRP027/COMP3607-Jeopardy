# COMP3607-Jeopardy
Multi-Player Jeopardy Game
This project is a Java-based Multi-Player Jeopardy Game designed to run locally on a single device. It supports one to four players, loads dynamic question data from CSV, JSON, or XML files, logs all user interactions for process-mining analysis, and generates end-of-game summary reports in PDF, TXT, and DOCX formats. The system follows SOLID principles, incorporates multiple design patterns, and includes full JUnit testing along with Maven packaging. Documentation is provided through an organised GitHub Wiki and accompanying demonstration and reflection videos.
<h>
Overview
The game simulates a traditional Jeopardy-style experience where players select categories, answer questions, and accumulate points. Questions are loaded from external files to allow easy customisation and extensibility. Throughout the session, all gameplay interactions are recorded in a structured process log, which can be analysed for behavioural and system-performance insights. At the end of each match, a comprehensive report summarises scores, player accuracy, activity patterns, and dataset information.
<h>
Features
The system supports up to four players on a single machine and reads question data from CSV, JSON, or XML formats. It includes a flexible loading architecture based on the Strategy pattern, enabling new formats to be added without changes to core game logic. Reports are generated automatically in PDF, TXT, and DOCX using dedicated reporter classes. Every user action, including question selection and answer submission, is stored in a process-mining log in CSV format.

The software integrates multiple design patterns, including Strategy for file loading, Factory Method for question object creation, Observer for updating user interface elements or listeners, and Singleton for the game engine. The project is built according to SOLID principles to ensure maintainability and extensibility.