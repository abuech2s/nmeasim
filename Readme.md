# NMEA and Air Surveillance Simulator

Simulates NMEA and SBS data via TCP/UDP connections.

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

Either execute the file `start.bat` or use the following command

```shell
java -DlogPath="." -Dlogback.configurationFile="logback.xml" -jar simulator.jar
```

While starting this program, the simulator expects a `config.xml` relative to itself.

### Configuration

The content of `config.xml` must have the following structure 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configs>
	<config type="adsb"  sink="tcp" active="true"  ip="" port="10300"  nroftrack="1"/>
	<config type="ais"   sink="tcp" active="false" ip="" port="10200"  nroftrack="1"/>
	<config type="radar" sink="tcp" active="false" ip="" port="10400" />
	<config type="gps"   sink="tcp" active="false" ip="" port="10500" />
</configs>
```

where

 * `type` equals to a stream type. Possible values: `adsb`, `ais`, `gps`, `radar`
 * `sink` equals to a sink type. Possible values: `tcp`, `udp`
 * `active` is the flag, to decide, if this stream is active or not. Possible value: `true`, `false` 
 * `ip` is used in case of `sink=udp` and is the target address
 * `port` is the TCP-Socket-Port or in case of `sink=udp` the target port
 * `nroftracks` equals to the number of generated tracks.

If `radar` is active, `gps` will be automatically activated as well (the active-flag of `gps` will be ignored.).
The simulator will check every `15s`, if this file is modified (based on MD5 hash). In case of changes, the configuration file is reloaded automatically.<br/>

The variable `nroftrack` in case of `gps` will always be ignored. We expect, that we produce just GPS data for one object.<br/>
The variable `nroftrack` in case of `radar` will always be ignored. There is a list of fixed positions of track objects.

## Changelog

2020-08-11 : V1.0
 - Initial version

## CopyRight

(c) Alexander Buechel, abuech2s@gmail.com, August 2020