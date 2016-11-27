# lein-licenses

A Leiningen plugin to list the license of each of your dependencies.

## Installation

If you are using Leiningen 2.7.0+, put
```clojure
[lein-licenses "0.2.1"]
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

An example `fallbacks.edn` file [can be found in the `examples` directory](https://github.com/technomancy/lein-licenses/blob/master/examples/fallbacks.edn).

## License name normalisation

Since license names don’t have any specific format, it’s not uncommon to get different names for the same license.
The normalisation mechanism checks each license string against a map of synonyms defined in `synonyms.edn` in the project directory, and returns a canonical license name on match.

A simple example `synonyms.edn` is [available in the `examples` directory](https://github.com/technomancy/lein-licenses/blob/master/examples/synonyms.edn).

A more comprehensive [SPDX](https://spdx.org/) `synonyms.edn` file [is also available in in the `examples` directory](https://github.com/technomancy/lein-licenses/blob/master/examples/synonyms-spdx.edn).  Note that lein-licenses is not 100% SPDX compliant, since its license text analysis logic is more simplistic than that [recommended by SPDX](https://spdx.org/spdx-license-list/matching-guidelines).  It also mistakenly reports "Public Domain" as a license, when in fact [public domain is a concept distinct from copyright licensing](http://wiki.spdx.org/view/Legal_Team/Decisions/Dealing_with_Public_Domain_within_SPDX_Files).

## Examples

```shell
    $ lein licenses # in the leiningen-core library
    nekohtml/xercesMinimal - Unknown
    org.apache.maven.wagon/wagon-http - The Apache Software License, Version 2.0
    org.sonatype.aether/aether-connector-file - Eclipse Public License, Version 1.0
    classlojure - Unknown
    org.codehaus.plexus/plexus-interpolation - The Apache Software License, Version 2.0
    org.sonatype.sisu/sisu-inject-bean - The Apache Software License, Version 2.0
    org.codehaus.plexus/plexus-component-annotations - The Apache Software License, Version 2.0
    org.codehaus.plexus/plexus-utils - The Apache Software License, Version 2.0
    commons-logging - The Apache Software License, Version 2.0
    com.cemerick/pomegranate - Eclipse Public License 1.0
    org.apache.maven/maven-model-builder - The Apache Software License, Version 2.0
    org.clojure/tools.macro - Eclipse Public License 1.0
    org.sonatype.aether/aether-util - Eclipse Public License, Version 1.0
    nekohtml - Apache License
    org.apache.maven/maven-aether-provider - The Apache Software License, Version 2.0
    org.sonatype.aether/aether-api - Eclipse Public License, Version 1.0
    org.apache.maven.wagon/wagon-http-shared - The Apache Software License, Version 2.0
    commons-codec - /*
    org.clojure/clojure - Eclipse Public License 1.0
    org.codehaus.plexus/plexus-classworlds - The Apache Software License, Version 2.0
    org.sonatype.aether/aether-impl - Eclipse Public License, Version 1.0
    org.sonatype.sisu/sisu-guice - Apache License
    robert/hooke - Unknown
    org.apache.maven/maven-repository-metadata - The Apache Software License, Version 2.0
    org.sonatype.aether/aether-spi - Eclipse Public License, Version 1.0
    commons-httpclient - Apache License
    useful - Unknown
    org.apache.maven/maven-model - The Apache Software License, Version 2.0
    org.apache.maven.wagon/wagon-provider-api - The Apache Software License, Version 2.0
    org.sonatype.aether/aether-connector-wagon - Eclipse Public License, Version 1.0
    org.sonatype.sisu/sisu-inject-plexus - Eclipse Public License, Version 1.0
    ordered - Unknown
```

Obtaining the list of unique licenses:

```shell
    $ lein licenses :csv | cut -d , -f3 | sort | uniq   # In the lein-licenses library
    "Apache License 2.0"
    "Apache Sofware License 2.0"
    "Eclipse Public License 1.0"
    "Eclipse Public License"
    "Public Domain"
    "The BSD License"
    "unknown"
```

This example nicely demonstrates why [license name normalisation](#license-name-normalisation) is desirable.

## License

Copyright © 2012 Phil Hagelberg

Distributed under the Eclipse Public License, the same as Clojure.
