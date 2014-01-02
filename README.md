# my-compojure

A wrapper around the basic pattern I generally use for compojure projects.

Basically it lets you define a default configuration for your app, then give the
user the choice of loading a config file on the command-line or dumping the
default to stdout.

The library also gives a method for easily running your app with jetty.

## Usage

```clojure
[org.clojars.mediocregopher/my-compojure "0.1.0"]

;You may also want the following, so you can run "lein ring server"
:plugins [[lein-ring "0.8.5"]]
:ring {:handler my-app.core/app}
```

Check the [examples.clj][example] file for example usage.

## License

Copyright © 2014 Brian Picciano

Distributed under the Eclipse Public License, the same as Clojure.

[example]: /src/my_compojure/example.clj
