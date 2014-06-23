# soe_loader

Takes a file of JSON content returned by the SOE status web service, and loads it into a SQLite3 database.

## Installation

Download from http://example.com/FIXME.

## Usage

FIXME: explanation

    $ java -jar soe_loader-0.1.0-standalone.jar [args]

## Options

Two arguments:

<file> JSON file to be processed.
<date> reference date for offset calculations, format <yyyyMMddHHmmss>.

## REPL code

```
(use 'soe-loader.core)
(use 'soe-loader.db)
;(create-schema)
;(-main "/Volumes/Macintosh_HD_2/Projects/SOE_logs/soe_status_20140603190000.json" "20140603190000")
```

## Execution Examples

    $ java -jar /Volumes/Macintosh_HD_2/Projects/Clojure/soe-loader/target/uberjar/soe-loader-0.1.0-SNAPSHOT-standalone.jar /Volumes/Macintosh_HD_2/Projects/SOE_logs/soe_status_20140603190000.json 20140603190000


Run using crontab -e:

    0 * * * * ~paul/bin/soe-loader.sh 2>&1

Script is:
```
#!/usr/bin/env bash

datetext=`date +'%Y%m%d%H%M%S'`
outfile=/Volumes/Macintosh_HD_2/Projects/SOE_logs/soe_status_${datetext}.json
databasedir=/Volumes/Macintosh_HD_2/Projects/Clojure/soe-loader/
database=/Volumes/Macintosh_HD_2/Projects/Clojure/soe-loader/soe_status.sq3
command="/usr/bin/java -jar /Volumes/Macintosh_HD_2/Projects/Clojure/soe-loader/target/soe-loader-0.1.0-SNAPSHOT-standalone.jar"

/usr/bin/curl -silent --no-include --output ${outfile} http://data.soe.com/json/status/
cd ${databasedir}
${command} ${outfile} ${datetext}
```

### Bugs

...


## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.


