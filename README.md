# Offtopic!

[![Build Status](https://travis-ci.org/rtyler/offtopic.svg?branch=master)](https://travis-ci.org/rtyler/offtopic)
[ ![Download](https://api.bintray.com/packages/rtyler/maven/offtopic/images/download.svg) ](https://bintray.com/rtyler/maven/offtopic/_latestVersion)


Offtopic is a web application for inspecting and consuming events from
[Kafka](http://kafka.apache.org).

The primary goal of this tool is to give you real-time insight into a running
Kafka cluster.

![Watching topics in action](http://strongspace.com/rtyler/public/offtopic-secevents-20141125.png
)

## Requirements

* Java 7 or higher
* Zookeeper
* Kafka

## Features

 * **Watch**: From the `/topics` page clicking the "watch" button will set up a
   WebSockets-based stream of events straight from Kafka to your browser
 * **Multipass**: From the `/topics` page you can select multiple topics to
   watch at once ([screenshot of multipass in action](http://strongspace.com/rtyler/public/offtopic-usemultipass-20141125.png))
 * **Binary data support**: Clicking on a message row in the "watch" view will
   drop down a base64-encoded version of the message data

