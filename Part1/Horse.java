
/**
 * Abstract version of a horse within the race. Represents horse using its name,
 * distance, if fallen, confidence and holds it symbol to be used.
 * 
 * @author Rayaan Uddin
 * @version 1.0
 */
public class Horse {
    // Fields of class Horse
    private final String horseName;
    private char horseSymbol;
    private double horseConfidence;
    private int horseDistance;
    private boolean horseFallen;

    // Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence) {
        this.horseName = horseName;
        this.horseSymbol = horseSymbol;
        this.horseConfidence = horseConfidence;
    }

    // Mutator methods
    public void fall() {
        horseFallen = true;
        horseConfidence = horseConfidence * 0.9; // Confidence level drops by 10% when horse falls
    }

    public void goBackToStart() {
        horseDistance = 0;
        horseFallen = false;
    }

    public void moveForward() {
        horseDistance++;
    }

    public void setConfidence(double newConfidence) {
        if (newConfidence <= 1 && newConfidence >= 0) {
            horseConfidence = newConfidence;
        } else {
            System.out.println("Confidence Level must be between 0 and 1");
        }
    }

    public void setSymbol(char newSymbol) {
        horseSymbol = newSymbol;
    }

    // Accessor methods

    public double getConfidence() {
        return horseConfidence;
    }

    public int getDistanceTravelled() {
        return horseDistance;
    }

    public String getName() {
        return horseName;
    }

    public char getSymbol() {
        return horseSymbol;
    }

    public boolean hasFallen() {
        return horseFallen;
    }
}

