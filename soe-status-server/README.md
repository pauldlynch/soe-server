# soe-status-server

FIXME

## Usage

FIXME

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## Notes (for LightTable)

Use Connections to create a connection to the project.clj file.  Disconnect and reconnect when you make changes to dependencies.

Create an InstaRepl to test.  Paste in:

```
;;(use 'soe-status-server.repl)
;;(start-server)
;;(stop-server)
(use 'soe-status-server.routes.home)
```

At times you may need to remove symbols:
```
(ns-unmap 'user 'save-message)
```

Use Cmd-Enter to evaluate a 'form' (function, statement, or line of code).

## TO DO

1. Update version of org.clojure/java.jdbc to 0.3+
2. Check for other ring handlers required.


## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

