# lein-licenses

A Leiningen plugin to list the license of each of your dependencies.

Looks in `pom.xml` inside the jar, `<parent>` declarations in the pom,
as well as files named `LICENSE.txt`, etc. inside the jar.

## Usage

Put `[lein-licenses "0.1.1"]` into the `:plugins` vector of your
`:user` profile and run `lein licenses` in your project directory.
(Not compatible with Leiningen 1.x.)


## Example

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

## License

Copyright © 2012 Phil Hagelberg

Distributed under the Eclipse Public License, the same as Clojure.
