import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell and Uddin
 * @version 1.1
 */
public class Race
{
    private int raceLength;
    private Horse[] lanes;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     * @param lanes the number of lanes in track
     */
    public Race(int distance, int lanesNumber)
    {
        // initialise instance variables
        raceLength = distance;
        lanes = new Horse[lanesNumber];
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        if (laneNumber > lanes.length) {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        } else {
            if (lanes[laneNumber - 1] != null) {
                System.out.println("Cannot add horse to lane " + laneNumber + " because there is already a horse in that lane");
                return;
            }
            lanes[laneNumber - 1] = theHorse;
        }
    }

    /**
     * Removes a horse from a lane
     * 
     * @param laneNumber the lane that the horse will be removed from
     */
    public void removeHorse(int laneNumber) {
        if (laneNumber > lanes.length) {
            System.out.println("Cannot remove horse from lane " + laneNumber + " because there is no such lane");
        } else {
            lanes[laneNumber - 1] = null;
        }
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace() {
        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        
        //reset all the lanes (all horses not fallen and back to 0). 
        for (Horse horse : lanes) {
            if (horse == null) { // Empty lane
                continue;
            }
            horse.goBackToStart();
        }
                      
        while (!finished) {
            //move each horse
            for (Horse horse : lanes) {
                if (horse == null) { // Empty lane
                    continue;
                }
                moveHorse(horse);
            }
                        
            //print the race positions
            printRace();
            
            //if any of the three horses has won or all are fallen the race is finished
            boolean allFallen = true;
            for (Horse horse : lanes) {
                if (horse == null) { // Empty lane
                    continue;
                }
                if (raceWonBy(horse)) {
                    System.out.println("And the winner is "+ horse.getName());
                    horse.setConfidence(horse.getConfidence() * 1.1);
                    if (horse.getConfidence() > 1.0) {
                        horse.setConfidence(1.0);
                    } else if (horse.getConfidence() == 0.0) {
                        horse.setConfidence(0.1);
                    }
                    finished = true;
                }
                if (allFallen && !horse.hasFallen()) {
                    allFallen = false;
                }
            }
            if (allFallen) {
                System.out.println("All horses have fallen");
                finished = true;
            }
           
            //wait for 100 milliseconds
            try { 
                TimeUnit.MILLISECONDS.sleep(100);
            } catch(Exception e){}
        }
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse) {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen()) {
            //the probability that the horse will move forward depends on the confidence;
            if (((int)((Math.random()*10.0)+0.5)/10.0) <= theHorse.getConfidence()) {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence())) {
                theHorse.fall();
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        System.out.print('\u000C');  //clear the terminal window
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();
        
        for (Horse horse : lanes) {
            printLane(horse);
            System.out.println();
        }
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse) {
        if (theHorse == null) { // Empty lane
            System.out.print('|');
            multiplePrint(' ',raceLength+1);
            System.out.print('|');
            return;
        }
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen()) {
            System.out.print("\u001B[31m" + '\u0078' + "\u001B[0m");
        } else {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print("| " + theHorse.getName() + " (Current confidence " + ((int)((theHorse.getConfidence()*100.0)+0.5)/100.0) + ")");
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }
}