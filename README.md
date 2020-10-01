# lein-licenses

A Leiningen plugin to try to detect the license of each of your dependencies.

Please note that this is just a heuristic and can result in inaccurate readings.

This project is not under active development; if you wish to make a contribution please email me and I can move this repository to a new host to make further changes.

## Installation

If you are using Leiningen 2.7.0+, put 
```clojure
[lein-licenses "0.2.2"]
```
into the `:plugins` vector of your `:user` profile.

For leiningen < 2.7.0, use version `"0.2.0"`.

Not compatible with Leiningen 1.x.

## Usage

Run `lein licenses` in your project directory.

Pass `:csv` or `:edn` to change output format:

```
lein licenses :edn
```

## Lookup order

1. `pom.xml` inside the jar, including `<parent>` declarations.
2. `<artifact>.pom` file, including `<parent>` declarations.
3. License files (`LICENCE`, `LICENCE.txt`, etc) inside the jar.
4. `fallbacks.edn` in the project directory.

## Fallbacks

Fallbacks are only used if all other locations failed.

Example `fallbacks.edn` file could be found in the `examples` directory.

## License name normalisation

Since license names don’t have any specific format, it’s not uncommon to get different names for the same license.
Normalisation mechanism checks each license string against a map of synonyms defined in `synonyms.edn`, and returns a canonical license name on match.

Example `synonyms.edn` is available in `examples` directory.

## Example

    $ lein licenses # in the leiningen-core library
    org.clojure/clojure - 1.8.0 - Eclipse Public License 1.0
    org.apache.httpcomponents/httpclient - 4.5.3 - Apache License, Version 2.0
    commons-io - 2.5 - Apache License, Version 2.0
    org.apache.maven.resolver/maven-resolver-transport-wagon - 1.0.3 - Apache License, Version 2.0
    org.clojure/tools.macro - 0.1.5 - Eclipse Public License 1.0
    org.apache.maven/maven-resolver-provider - 3.5.0 - Apache License, Version 2.0
    org.apache.maven.wagon/wagon-provider-api - 2.12 - Apache License, Version 2.0
    com.google.guava/guava - 20.0 - The Apache Software License, Version 2.0
    com.hypirion/io - 0.3.1 - Eclipse Public License
    com.cemerick/pomegranate - 0.4.0 - Eclipse Public License 1.0
    org.codehaus.plexus/plexus-component-annotations - 1.7.1 - Apache License, Version 2.0
    org.clojure/tools.nrepl - 0.2.12 - Eclipse Public License 1.0
    org.apache.maven.resolver/maven-resolver-transport-http - 1.0.3 - Apache License, Version 2.0
    clojure-complete - 0.2.4 - Eclipse Public License
    robert/hooke - 1.3.0 - Eclipse Public License
    org.apache.maven.resolver/maven-resolver-connector-basic - 1.0.3 - Apache License, Version 2.0
    org.apache.maven.resolver/maven-resolver-util - 1.0.3 - Apache License, Version 2.0
    org.tcrawley/dynapath - 1.0.0 - Eclipse Public License
    org.apache.maven.wagon/wagon-http-shared - 2.12 - Apache License, Version 2.0
    org.jsoup/jsoup - 1.7.2 - The MIT License
    org.apache.maven.resolver/maven-resolver-impl - 1.0.3 - Apache License, Version 2.0
    org.slf4j/slf4j-nop - 1.7.22 - MIT License
    bultitude - 0.2.8 - Eclipse Public License 1.0
    org.slf4j/slf4j-api - 1.7.22 - MIT License
    org.apache.maven/maven-model - 3.5.0 - Apache License, Version 2.0
    org.apache.commons/commons-lang3 - 3.5 - Apache License, Version 2.0
    org.slf4j/jcl-over-slf4j - 1.7.22 - MIT License
    io.aviso/pretty - 0.1.20 - Apache Sofware License 2.0
    org.apache.maven/maven-model-builder - 3.5.0 - Apache License, Version 2.0
    org.apache.httpcomponents/httpcore - 4.4.4 - Apache License, Version 2.0
    org.codehaus.plexus/plexus-utils - 3.0.24 - Apache License, Version 2.0
    org.apache.maven/maven-builder-support - 3.5.0 - Apache License, Version 2.0
    org.apache.maven.resolver/maven-resolver-transport-file - 1.0.3 - Apache License, Version 2.0
    org.apache.maven/maven-repository-metadata - 3.5.0 - Apache License, Version 2.0
    org.codehaus.plexus/plexus-interpolation - 1.24 - Apache License, Version 2.0
    commons-logging - 1.2 - The Apache Software License, Version 2.0
    org.apache.maven.wagon/wagon-http - 2.12 - Apache License, Version 2.0
    commons-codec - 1.9 - The Apache Software License, Version 2.0
    org.flatland/classlojure - 0.7.1 - Eclipse Public License - v 1.0
    org.apache.maven.resolver/maven-resolver-api - 1.0.3 - Apache License, Version 2.0
    org.apache.maven/maven-artifact - 3.5.0 - Apache License, Version 2.0
    org.apache.maven.resolver/maven-resolver-spi - 1.0.3 - Apache License, Version 2.0

## License

Copyright © 2012-2017 Phil Hagelberg and contributors

Distributed under the Eclipse Public License, the same as Clojure.
