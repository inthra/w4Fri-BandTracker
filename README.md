# Advance databases independent project: Band Tracker

#### _A program to track bands and the venues where they've played concerts., May 13, 2016_

#### By _Inthrayuth Mahaphol_

## Description

A program to track bands and the venues where they've played concerts. Users are be able to add, update, delete, and list all bands and venues. Also, the venues can be added to a band to show where they have played. Users will be able to view all venues that linked to a band and show on the individual band page.

* New Bands and venues can't be saved if a user leave the blank input field.
* "Update" and "Delete" functions will not show up if there is nothing in databases.
* "Add Venues to a Band" and "Add Bands to a Venue" functions will not show up if there is no a venue or a band in databases.

Databases:
* Production database is called "band_tracker"
* Development database is called "band_tracker_test". The development database should be created by duplicating from production database.
* There are three tables in the production database: "bands", "venues", and "bands_venues".
* SQL database dump file is named band_tracker.sql
* The app uses many-to-many relationship database style.

## Setup/Installation Requirements

* _Link to repository: https://github.com/inthra/w4Fri-BandTracker.git_
* _Download this app to your computer_
* _Java, Gradle, and Postgres apps need to be installed on your computer_
* _Run "gradle run" on command line and go to url "localhost:4567" on a web browser_

## Setup database

* Open a terminal and run "postgres" to access the Postgres server.
* Open another terminal and run "psql". These process will give you ability to create databases and tables.

  In PSQL:
*  =# CREATE DATABASE band_tracker;
*  =# \c band_tracker;
*  band_tracker=# CREATE TABLE bands (id serial PRIMARY KEY, name varchar);
*  band_tracker=# CREATE TABLE venues (id serial PRIMARY KEY, name varchar);
*  band_tracker=# CREATE TABLE bands_venues (id serial PRIMARY KEY, band_id int, venue_id int);
*  band_tracker=# CREATE DATABASE band_tracker_test WITH TEMPLATE band_tracker;

## Known Bugs

* There is no feature to prevent duplication data when user input new Bands or Venues.
* "Add Venues to a Band" and "Add Bands to a Venue" still do not have feature to prevent duplication adding.

## Support and contact details

_Feel free to contact me with questions or suggestions. Inthrayuth Mahaphol: zign.int@gmail.com_

## Technologies Used

* _Git_
* _Github_
* _Java_
* _Postgres_
* _Gradle_
* _Spark_
* _Apache Velocity_ (with using the Velocity Template Engine)

### License

*This software is licensed under the MIT license*

Copyright (c) 2016 Inthrayuth M.
