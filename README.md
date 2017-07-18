# osm-graph-parser
![travis](https://travis-ci.org/rovaniemi/osm-graph-parser.svg?branch=master)
[![Code Coverage](https://img.shields.io/codecov/c/github/rovaniemi/osm-graph-parser/master.svg)](https://codecov.io/github/rovaniemi/osm-graph-parser)
[![License: GPL v3](https://img.shields.io/aur/license/yaourt.svg)](https://github.com/rovaniemi/osm-graph-parser/blob/master/LICENSE)

Java program that parses OSM XML files into a json graph representation. 

## Example

In output file you will have nodes and edges with weight (unit: `centimetres`).
```json
[
  {
    "la": 60.1570875,
    "lo": 24.9563234,
    "e": [
      {
        "i": 1,
        "w": 514
      },
      {
        "i": 17,
        "w": 574
      }
    ]
  },
  {
    "la": 60.1570488,
    "lo": 24.9562727,
    "e": [
      {
        "i": 0,
        "w": 514
      },
      {
        "i": 2,
        "w": 784
      },
      {
        "i": 11302,
        "w": 413
      }
    ]
  }
 ]
 ```
 
 ### How to read that json
 ```
 la = latitude
 lo = longitude
 e = edges
 i = main array index, if the i is 0 it refers to the first element in the array
 w = weight
 ```

 ### Command-line flags

```
-f, --files           osm files to be parsed
-i, --includeWays     way tags to include
-e, --excludeWays     way tags to exclude (overrides includeWay)
-o, --output          output file name
-q, --quiet           suppress console output
```

## Getting Started

1. [Download](https://github.com/rovaniemi/osm-graph-parser/releases) latest version under the releases tab.
2. [Download](https://www.openstreetmap.org/) openstreetmap data. (click export, select area, and then export)
3. Use -f flag to select osm files.
4. Use -i flag to select ways. You can view osm map features [here.](http://wiki.openstreetmap.org/wiki/Map_Features)
5. Use -o flag to defining output file name.
6. Run the jar file. (terminal `java -jar <jar-file-name>.jar -f files -i selected ways -o output file name`)
7. Now you have `json` in the same directory where the .jar file is.
8. If you use this data in your own service read [openstreetmap licence.](https://opendatacommons.org/licenses/odbl/1.0/)

### Possible errors

1. Parsing take much time or program crash.
    - The program does not have enought memory. Use `-Xmx` flag with java. Example run command `java -jar -Xmx4096m <jar-file-name>.jar`. That will increase java heap max size to 4gb. You will need 4gb ram for that. 

### Prerequisites

You will need a Java Runtime Environment (JRE) to run java programs. You can download it [here](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html).

## Built With

* [Gradle](https://gradle.org) - Dependency Management

## Authors

* **Mauri Karlin** - *Owner* - [Rovaniemi](https://github.com/Rovaniemi)
* **Joona Heikkilä** - Optimizations and refactorings - [cxcorp](https://github.com/cxcorp)

## License

This project is licensed under the GNU GENERAL PUBLIC LICENSE - see the [LICENSE](LICENSE) file for details
