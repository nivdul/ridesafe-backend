<center><img src="https://raw.githubusercontent.com/ridesafe/project/gh-pages/ridesafe_256.jpg" ></center>

## RideSafe
[RideSafe](http://www.ridesafe.io) is an open source project which detects bikers' falls. This is possible thanks to intelligent algorithms and data collection.

Our smartphones have accelerometers to measure acceleration forces of individuals, these data can be used to analyse the behaviour: when walking, running, biking and even falling !
The self learning algorithms are able to improve the detection of a fall by analysing such data.

Go to:
* [RideSafe Backend](#RideSafe Backend)
* [How it works](#How it works)
* [Running locally](#Running locally)
* [Our open backend](#Our open backend)
* [Contribute](#Contribute)
* [Demo](#Demo)
* [Who are we](#Who are we)
* [Partners](#Partners)
* [More](#More)

## RideSafe Backend
The RideSafe backend is in charge of receiving data from clients. It is [Spring Boot](http://projects.spring.io/spring-boot/) + [Kotlin](https://kotlinlang.org/) based application backed by [Cassandra](http://cassandra.apache.org/) database

## How it works
This is a simple service that only receipts Acceleration data and store it into its database.

Examples
```bash
curl -X POST --data '{"timestamp": 1234567890, "x": 0.323149, "y": -2.5231, "z": 9.28387237}' -H "Device-Id: your_device_uuid" -H "Content-type: application/json" http://localhost:8093/api/v1/acceleration
curl -X POST --data '[{"timestamp": 1234567890, "x": 0.323149, "y": -2.5231, "z": 9.28387237}, ...]' -H "Device-Id: your_device_uuid" -H "Content-type: application/json" http://localhost:8093/api/v1/accelerations
```

Basically, you need to generate a unique device id and put it as request header to store data correctly into database.
```
... -H "Device-Id: your_device_uuid" ...
```

To make data storing valuable, sender can categorize it's activity to make predictive model more accurate.
```
curl -X POST --data '{"start_timestamp": 1234567890, "end_timestamp": "2334567890", "activity_type": "MOTORBIKING", "bike_type": "ROADSTER", "road_type": "CITY", "road_condition": "GOOD"}' -H "Device-Id: your_device_uuid" -H "Content-type: application/json" http://localhost:8093/api/v1/acceleration/form
```

* **start_timestamp** <-> **end_timestamp** is the range date in milli seconds of your activity.
* **activity_type** has to be chosen between the followings: MOTORBIKING, MOTORBIKE_FALLING, MOTORBIKE_PUSHING, WALKING, JOGGING, SITTING, STANDING, SLEEPING, USING_PHONE
* **bike_type** can be null or: ROADSTER, TRAIL, SPORT, TOURING, SPORT_TOURING, CUSTOM, COLLECTOR
* **road_type** can be null or: PRIVATE_ROAD, TRACK, CITY, ROAD_ONE_LANE, ROAD_MULTIPLE_LANE, FREEWAY, TRACK_RACE
* **road_condition** can be null or: GOOD, FAIR, BAD, VERY_BAD

## Running locally
Note: If you aim to use your own RideSafe backend, keep in mind that our data are open as well. You are better to use **our provided Android connector** and pushing data to [our open backend](#Open backend).

Prerequisites:
* [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Cassandra database](http://cassandra.apache.org/download/)

Build app
```
# Clone this repository
cd ridesafe-backend

# Build app
chmod +x gradlew
./gradlew build

# Run app
java -jar build/libs/rs-backend-0.1.jar

# it's now live on http://localhost:8093
```

Cassandra schema
```SQL
CREATE KEYSPACE ridesafe WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

use ridesafe;

CREATE COLUMNFAMILY acceleration (
  device_id text,
  user_id bigint,
  "timestamp" bigint,
  x decimal,
  y decimal,
  z decimal,
  activity_type text,
  bike_type text,
  road_type text,
  road_condition text,
  PRIMARY KEY ((device_id, user_id), "timestamp")
);

CREATE INDEX activity_type_idx ON acceleration (activity_type);
CREATE INDEX bike_type_idx ON acceleration (bike_type);
CREATE INDEX road_type_idx ON acceleration (road_type);
CREATE INDEX road_condition_idx ON acceleration (road_condition);
```

## Our open backend
Instead of locally install the backend, you can target our backend server which is available at the following web address:
```
https://api.ridesafe.io
```

## Contribute
There are many ways to contribute to RideSafe.
* **You are developer**: You have an idea to make RideSafe better ? You want to clean the code ?.. Just send us a Pull Request :)
* **You are data scientist**: Take a look at our activity recognition algorithms and tell us what do you think and how to improve them.
* **You are motorcycling professional**: More we can test, more accurate are algorithms and fall detection. Contact us to see how you can contribute !

## Demo
RideSafe is used into our [Nousmotards](https://play.google.com/store/apps/details?id=com.nousmotards.android) app.


## Who are we ?
RideSafe has been launched by [Nousmotards](https://www.nousmotards.com).
**Nousmotards** is a service platform for bikers, created by [3 engineers and bikers](http://blog.nousmotards.com/2015/04/24/ouverture-du-blog-nousmotards/):
* [Romaric Philogène](https://fr.linkedin.com/in/romaricphilogene)
* [Rémi Demol](https://www.linkedin.com/in/demolremi/fr)
* [Pierre Mavro](https://fr.linkedin.com/in/pmavro/fr)

We are creating a set of services tailored exclusively to the world of motorcycling!
Nousmotards app is available on Mobile ([Android](https://play.google.com/store/apps/details?id=com.nousmotards.android), iOS) and on your [browser](https://www.nousmotards.com).

Contact us at [contact@ridesafe.io](mailto:contact@ridesafe.io) or [contact@nousmotards.com](mailto:contact@nousmotards.com)

## Partners
We are looking for worldwide partners specialised in motorbike and/or technology.

## More
The project is based on the work of [Amira Lakhal](https://github.com/MiraLak), which detects one type of activity from the accelerometer integrated in smartphones and a self-learning algorithm called machine learning.
The data and algorithms are available under the Apache license, this means that the changes and improvements made will be communicated to the community.
Commercial use is unrestricted.