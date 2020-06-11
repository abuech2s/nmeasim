# NMEA and Air survaillance Simulator

Simulates NMEA and SBS data via TCP Socket

## Supported sentences

### ADSB

Simulates SBS data: MSG-Messages with subtypes 1,3,4

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

```shell
java -jar simulator*.jar <type> <port> <nrOfTracks>
```

where 

```shell
   <type> is {ADSB, AIS, GPS, RADAR}
   <port> is a number
   <nrOfTracks> is a number
```
  

## CopyRight

(c) Alexander Buechel, May 2020