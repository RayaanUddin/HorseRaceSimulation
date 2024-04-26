/**
 * Holds the statisical data of a horse to be displayed within an instance of a StatsFrame
 * 
 * @author Rayaan Uddin
 * @version 1.0
 * @see StatsFrame
 * 
 */
public class StatsData {
    String horseName;
    int distanceTravelled;
    double speed;
    double time;
    boolean won;

    /**
     * Constructor for StatsData using horse details manually.
     * @param horseName
     * @param distanceTravelled
     * @param time
     * @param won
     */
    public StatsData(String horseName, int distanceTravelled, double time, boolean won) {
        this.horseName = horseName;
        this.distanceTravelled = distanceTravelled;
        this.speed = ((int)(((distanceTravelled/ time)*100.0)+0.5)/100.0);
        this.time = time;
        this.won = won;
    }

    /**
     * Constructor for StatsData using a LanePanel to extract horse details.
     * @param lane
     * @see LanePanel
     */
    public StatsData(LanePanel lane) {
        this.horseName = lane.horsePanel.horse.getName();
        this.distanceTravelled = lane.horsePanel.horse.getDistanceTravelled();
        this.speed = ((int)(((lane.horsePanel.horse.getDistanceTravelled()/ lane.horsePanel.getFinishingTime())*100.0)+0.5)/100.0);
        this.time = lane.horsePanel.getFinishingTime();
        this.won = lane.horsePanel.won;
    }

    /**
     * Constructor for StatsData using encoded stats data read from a file.
     * @param encodedStats
     */
    public StatsData(String encodedStats) {
        StatsData stats = decodeData(encodedStats);
        this.horseName = stats.horseName;
        this.distanceTravelled = stats.distanceTravelled;
        this.speed = stats.speed;
        this.time = stats.time;
        this.won = stats.won;
    }

    // Note Data is split in lines using ;
    // Example of encoded data: HorseName1,DistanceTravelled1,Speed1,Time1,Won1;HorseName2,DistanceTravelled2,Speed2,Time2,Won2;

    public String encodeData() {
        // Encode stats data
        return horseName + "," + distanceTravelled + "," + time + "," + won;
    }

    public static StatsData decodeData(String encodedStats) {
        // Decode stats data
        String[] data = encodedStats.split(",");
        StatsData decodeData = new StatsData(data[0], Integer.parseInt(data[1]), Double.parseDouble(data[2]), Boolean.parseBoolean(data[3]));
        return decodeData;
    }

    // Create stats data array from lanes ignoring null horsePanels
    public static StatsData[] createStatsData(LanePanel[] lanes) {
        int count = 0;
        for (LanePanel lane : lanes) {
            if (lane.horsePanel != null) {
                count++;
            }
        }
        StatsData[] statsData = new StatsData[count];
        int count2 = 0;
        for (LanePanel lane : lanes) {
            if (lane.horsePanel == null) {
                continue;
            }
            statsData[count2] = new StatsData(lane);
            count2++;
        }
        return statsData;
    }
}
