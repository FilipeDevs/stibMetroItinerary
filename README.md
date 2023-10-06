# Stib Itinerary Planner

A Java project made for one my courses at HE2B-ESI. 

A JavaFx application that allows to calculate the shortest route between two stations on the Stib metro network. The application also allows to store/delete/edit favorites routes.

It uses the Dijkstra algorithm to calculate the shortest path between two stations (in this case the metro network is represented by a Graph containing multiple nodes, each node representing a station)

# Usage 

**Please ensure you have maven installed**

1. cd into the project root

```
cd maven_StibRide
```

2. Compile the project with maven : 

```
mvn compile
```

3. Run the project with maven : 

```
mvn javafx:run
```
