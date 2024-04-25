# Horse Race Simulation

This is a simple program which consists of 2 parts. Part 1 consists of a textual based program ran in the console. Part 2 consists of a GUI based program ran in a window. The program simulates a horse race. 

## Part 1: Textual Based Program

Horses are represented by a character. Each horse has a name, confidence level, and symbol. Its distance and its status of it being fallen is also stored within its object. 

A race consists of a maximun number of n horses where n is the number of lanes which is passed as an argument when creating an object of class Race as well as the distance. Not all tracks need to be filled.

You can create a simple race by creating an instance of Race, adding instances of Horse to the race, then calling startRace() method.
```java
Race race = new Race(10, 3);
Horse horse1 = new Horse('A', "HorseA", 0.5);
Horse horse2 = new Horse('B', "HorseB", 0.4);
Horse horse3 = new Horse('C', "HorseC", 0.8);
race.addHorse(horse1, 1);
race.addHorse(horse2, 2);
race.addHorse(horse3, 3);
race.startRace();
```

Each time startRace() is called, the horses are moved back to the start, the confidence level is preserved. When a horse wins a race, its confidence level is increased by 10%. When a horse falls, its confidence level is decreased by 10%.

### How to Run
There is a test program included in part1 directory (**Test.java**), which demonstrates the functionality of the program, which is a interactive program that allows the user to input all the data and repeatedly start a race.

## Part 2: GUI Based Program


