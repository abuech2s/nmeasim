# NMEA and Air survaillance Simulator

Simulates NMEA and SBS data via TCP Socket connections.

## Requirements

 - Java 1.8+
 - Maven 3.6.3

## Supported sentences

### ADSB

Simulates SBS data: MSG-Messages with subtypes 1,3 and 4

[Source](http://woodair.net/sbs/Article/Barebones42_Socket_Data.htm)

### AIS

Simulates AIS data: Message types: 1, 5

[Source](https://www.navcen.uscg.gov/?pageName=AISMessages)

### GPS

Simulates NMEA based $GPRMC and $GPGGA messages.

[Source 1](http://aprs.gids.nl/nmea/#rmc)
[Source 2](http://aprs.gids.nl/nmea/#gga)

### RADAR

Simulates NMEA based $RATTM messages.

[Source](http://www.nmea.de/nmea0183datensaetze.html#ttm)

## How to build

```shell
mvn clean package
```

## How to start

Either start `start.bat` or use the following command

```shell
java -DlogPath="." -Dlogback.configurationFile="logback.xml" -jar simulator.jar
```

While starting this program, the simulator expects a `config.xml` relative to itself.

### Configuration

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configs>
	<config type="adsb"  active="true"   port="10300"  nroftrack="1"/>
	<config type="ais"   active="false"  port="10200"  nroftrack="1"/>
	<config type="radar" active="false"  port="10400"  nroftrack="1"/>
	<config type="gps"   active="false"  port="10500"  nroftrack="1"/>
</configs>
```

If `radar` is active, `gps` will be automatically activated as well (the active-flag of `gps` will be ignored.).
The simulator will check every `15s`, if this file is modified (based on MD5 hash). In case of changes, the configuration is reloaded automatically.
  

## CopyRight

(c) Alexander Buechel, May 2020