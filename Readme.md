# NMEA and Air Surveillance Simulator

Simulates NMEA and SBS data via TCP/UDP connections.

## Requirements

 - Java 1.8+
 - Maven 3.6.3

## Supported sentences

### ADSB

Simulates SBS data: MSG-Messages with subtypes 1, 3 and 4

[Source](http://woodair.net/sbs/Article/Barebones42_Socket_Data.htm)

### AIS

Simulates AIS data: Message types: 1 and 5

[Source](https://www.navcen.uscg.gov/?pageName=AISMessages)

### GPS

Simulates NMEA based `$GPRMC` and `$GPGGA` messages.

[Source 1](http://aprs.gids.nl/nmea/#rmc)
[Source 2](http://aprs.gids.nl/nmea/#gga)

### RADAR

Simulates NMEA based `$RATTM` messages.

[Source](http://www.nmea.de/nmea0183datensaetze.html#ttm)

### WEATHER

Simulates NMEA based `$WIMDA` messages.

[Source](https://gpsd.gitlab.io/gpsd/NMEA.html#_mda_meteorological_composite)

### COURSE

Simulates NMEA based `$GPHDT` and `$HEHDT` messages.

[Source](https://www.trimble.com/OEM_ReceiverHelp/V4.44/en/NMEA-0183messages_HDT.html)

## How to build

```shell
mvn clean package
```

## How to start

Either execute the file `start.bat` or use the following command

```shell
java -DlogPath="." -Dlogback.configurationFile="logback.xml" -jar simulator.jar
```

While starting this program, the simulator expects the file `config.xml` relative to itself.

### Configuration

The content of `config.xml` must have the following structure 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configs>
	<config type="adsb"    sink="tcp" active="true"  ip="" port="10300"  nroftrack="1"/>
	<config type="ais"     sink="tcp" active="false" ip="" port="10200"  nroftrack="1"/>
	<config type="radar"   sink="tcp" active="false" ip="" port="10400" />
	<config type="gps"     sink="udp" active="true" ip="192.168.2.100" port="10500" />
	<config type="weather" sink="tcp" active="false" ip="" port="10600" />
	<config type="course" sink="tcp" active="false" ip="" port="10700" />
</configs>
```

where

 * `type` equals to a stream type. Possible values: `adsb`, `ais`, `gps`, `radar`, `weather`
 * `sink` equals to a sink type. Possible values: `tcp`, `udp`
 * `active` is the flag, to decide, if this stream is active or not. Possible values: `true`, `false` 
 * `ip` is used in case of `sink=udp` and is the target address.
 * `port` is the TCP-Socket-Port or in case of `sink=udp` the target port.
 * `nroftracks` equals to the number of generated tracks.

### Advices:

 * General advices:
   - The simulator checks every `15s`, if the file `config.xml` was modified (based on MD5 hash). In case of changes, the configuration file is reloaded automatically.
   - It is recommended, that the IP-Address should be set with the default IPv4 structure `x.x.x.x`.

 * Advices for `gps`:
   - If `radar` or `weather` are active, `gps` will be automatically activated as well (in this case the `active`-flag of `gps` will be ignored).
   - The variable `nroftrack` will be ignored. GPS sentences correspond just to a single object.

 * Advices for `radar`:
   - The variable `nroftrack` will be ignored.
   - The simulator handles a list of fixed positions of track objects. 
   - RATTM-messages are generated, if the GPS position is close enough to the stored track objects.

 * Advices for `weather`:
   - The variable `nroftrack` will be ignored. 
   - We expect, that produced weather sentences correspond to current GPS position.

## Changelog

2021-07-11 : V1.0.6

- add ETA for AIS tracks
- fix bug, where \r\n at udp packages is missing
- fix bug, where the checksum is missing at GPHDT messages
- update dependencies

2021-03-13 : V1.0.5
- add more ships
- fix wrong timezone in timestamp of gps and radar
- fix bug of wrong ais callsign
- fix bug of missing ais ship name

2021-02-15 : V1.0.4
- Add course information (Gphdt and Hehdt)

2020-11-17 : V1.0.3
- Add course, speed and gphdt message for GPS

2021-11-14 : V1.0.2
- Optimization of radar plots

2021-02-15 : V1.0.1
- Add course information (Gphdt and Hehdt)

2020-10-27 : V1.0
 - Initial version

## CopyRight

(c) Alexander Buechel, abuech2s@gmail.com, May 2021