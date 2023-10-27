# Stib Itinerary Planner

A Java project made for one my courses at HE2B-ESI.

A JavaFx application that allows to calculate the shortest route between two stations on the [STIB](https://www.stib-mivb.be/index.htm?) metro network. The application also implements a CRUD to manage favorites routes.

It uses the Dijkstra algorithm to calculate the shortest path between two stations (in this case the metro network is represented by a Graph containing multiple nodes, each node representing a station)

Design Pattern : MVP along with Observer/Observable

# Usage

**Please ensure you have maven installed**

1. Compile the project with maven :

```
mvn compile
```

2. Run the project with maven :

```
mvn javafx:run
```
