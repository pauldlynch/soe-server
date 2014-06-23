#!/usr/bin/env bash

datetext=`date +'%Y%m%d%H%M%S'`
outfile=/Volumes/Macintosh_HD_2/Projects/SOE_logs/soe_status_${datetext}.json
databasedir=/Volumes/Macintosh_HD_2/Projects/Clojure/soe-loader/
database=/Volumes/Macintosh_HD_2/Projects/Clojure/soe-loader/soe_status.sq3
command="/usr/bin/java -jar /Volumes/Macintosh_HD_2/Projects/Clojure/soe-loader/target/soe-loader-0.1.0-SNAPSHOT-standalone.jar"

/usr/bin/curl -silent --no-include --output ${outfile} http://data.soe.com/json/status/
cd ${databasedir}
${command} ${outfile} ${datetext}
