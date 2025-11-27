# GROUP 27 JEOPARDY PROJECT - COMP3607 S01 2025/2026

--------------------------------------------------------------
MEMBERS:
- Aaron Payne       |   816009846
- Calliste Charles  |   816036888
- Reshon Nelson     |   816041070
--------------------------------------------------------------
**Team:** Aaron Payne (816009846) , Calliste Charles ( 816036888), Reshon Nelson (816041070)
**Course:** COMP3607 (Object Oriented Programming II)  
**Repo:** [link to repo]

## Overview
This project is a Java-based Multi-Player Jeopardy Game designed to run locally on a single device. It supports one to four players, loads dynamic question data from CSV, JSON, or XML files, logs all user interactions for process-mining analysis, and generates end-of-game summary reports in PDF, TXT, and DOCX formats. The system follows SOLID principles, incorporates multiple design patterns, and includes full JUnit testing along with Maven packaging. Documentation is provided through an organised GitHub Wiki and accompanying demonstration and reflection videos.
<h>
Overview
The game simulates a traditional Jeopardy-style experience where players select categories, answer questions, and accumulate points. Questions are loaded from external files to allow easy customisation and extensibility. Throughout the session, all gameplay interactions are recorded in a structured process log, which can be analysed for behavioural and system-performance insights. At the end of each match, a comprehensive report summarises scores, player accuracy, activity patterns, and dataset information.
<h>
Features
The system supports up to four players on a single machine and reads question data from CSV, JSON, or XML formats. It includes a flexible loading architecture based on the Strategy pattern, enabling new formats to be added without changes to core game logic. Reports are generated automatically in PDF, TXT, and DOCX using dedicated reporter classes. Every user action, including question selection and answer submission, is stored in a process-mining log in CSV format.

The software integrates multiple design patterns, including Strategy for file loading, Factory Method for question object creation, Observer for updating user interface elements or listeners, and Singleton for the game engine. The project is built according to SOLID principles to ensure maintainability and extensibility.

See pages:
- Design
- Implementation
- Tests
- Demo Video
- AI Usage Log

# Design

## Architecture
We used MVC: GameModel, GameController, Swing Views.

## Design Patterns
- Factory Method — LoaderFactory for CSV/JSON/XML (justification).
- Strategy — AnswerStrategy (strict vs fuzzy).
- Observer — Model notifies ScoreObservers (UI) on changes.

## Class Diagram
(Include image exported from any UML tool).


# SOLID Principles

- SRP: loaders only parse; logger only logs; controllers only orchestrate.
- OCP: add loaders/strategies without editing core controller code.
- LSP: strategy implementations are substitutable.
- ISP: small focused interfaces (QuestionLoader, AnswerStrategy).
- DIP: controllers depend on interfaces not concrete implementations.



# Implementation

## Build
`mvn clean package`

## Run
`java -jar target/jeopardy-1.0-SNAPSHOT.jar`

## Input Formats
Describe CSV, JSON, XML expected schemas (sample files included).


# Implementation

## Build
`mvn clean package`

## Run
`java -jar target/jeopardy-1.0-SNAPSHOT.jar`

## Input Formats
Describe CSV, JSON, XML expected schemas (sample files included).

# Tests

Run `mvn test`. Unit tests cover loaders, scoring, and logging. Add additional tests for gameplay scenarios.


# Demo
Video link to 5–8 minute gameplay demo and 5–6 minute reflection video.


# AI Usage Log

| Date | Prompt | Code Generated | Member | Notes |
|------|--------|----------------|--------|-------|
| 2025-11-23 | ... | LoaderFactory, EventLogger | Alice | Reviewed and adapted |
