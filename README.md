# Offtopic

Offtopic is a simple web application built with [Ratpack](http://ratpack.io)
for inspecting and consuming events from [Kafka](http://kafka.apache.org).

The primary goal of this tool is to give you real-time insight into a running
Kafka cluster.

![Watching topics in action](http://strongspace.com/rtyler/public/offtopic-secevents-20141125.png
)

## Requirements 

* Java 8
* Zookeeper
* Kafka

## Features

 * **Watch**: From the `/topics` page clicking the "watch" button will set up a
   WebSockets-based stream of events straight from Kafka to your browser
 * **Multipass**: From the `/topics` page you can select multiple topics to
   watch at once
 * **Binary data support**: Clicking on a message row in the "watch" view will
   drop down a base64-encoded version of the message data
