# NotesLibrary

Notes library and todo list.

It is my Java pet project.

* Java 8
* Glassfish 4

## Implemented:

* SWING application;
* Tree-like structure of simple notes;
* Database interaction via JDBC (Derby, GlassFish);
* Simple logging.

## TODO:

* Make icons for notes;
* Settings (look and feel, logs on\off);
* Other databases (Microsoft SQL Server, PostgreSQL);
* JavaEE based web application (Glassfish or Wildfly);
* JavaFX application;
* Markdown parser;
* Export note(s) to PDF, LaTeX, XML, HTML;
* Export note structure to XML, CSV;
* More complicated notes (creation/modification date, change history, tags, etc);
* Tasks (start date, due date, project, task dependency);
* Telegram messenger notifiactions (about tasks);
* Gantt diagram, Kanban board;
* Datasets, file server;
* Multi-user: access control list, ownership;
* Secure credentials storage.

# Requirements:

* Create database;
* Create tables and test data (testdatatable.sql file for example);
* Fill database credentials in settings.properties file;
* Important: note with id=1 is the root note. It does not have a parent, and other notes are its children and grand-children.
