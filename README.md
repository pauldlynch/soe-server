soe-server
==========

Server code and uploader for SOE Status historical data.

This contains two projects, soe-server and soe-loader; each have their own README.md.

soe-loader
----------

Takes JSON files captured by `curl` via crontab and loads them into an SQLite3 database.  Code is all Clojure, invoked from a bash script.  If the database doesn't exist, it should be created.

soe-server
----------

Clojure program that runs a web server; a GET to the root URL will return a form; a POST with `game`, `server` and optional `from-date` and `to-date` will return the data requested, in JSON format.


