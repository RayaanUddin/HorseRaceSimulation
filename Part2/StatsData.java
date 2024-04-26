public class StatsData {
    String horseName;
    int distanceTravelled;
    double speed;
    double time;
    boolean won;

    public StatsData(String horseName, int distanceTravelled, double time, boolean won) {
        this.horseName = horseName;
        this.distanceTravelled = distanceTravelled;
        this.speed = ((int)(((distanceTravelled/ time)*100.0)+0.5)/100.0);
        this.time = time;
        this.won = won;
    }

    public StatsData(LanePanel lane) {
        this.horseName = lane.horsePanel.horse.getName();
        this.distanceTravelled = lane.horsePanel.horse.getDistanceTravelled();
        this.speed = ((int)(((lane.horsePanel.horse.getDistanceTravelled()/ lane.horsePanel.getFinishingTime())*100.0)+0.5)/100.0);
        this.time = lane.horsePanel.getFinishingTime();
        this.won = lane.horsePanel.won;
    }

    public StatsData(String encodedStats) {
        StatsData stats = decodeData(encodedStats);
        this.horseName = stats.horseName;
        this.distanceTravelled = stats.distanceTravelled;
        this.speed = stats.speed;
        this.time = stats.time;
        this.won = stats.won;
    }

    // Note Data is split in lines using ;

    public String encodeData() {
        // Encode stats data
        // Example: HorseName1,DistanceTravelled1,Speed1,Time1,Won1
        return horseName + "," + distanceTravelled + "," + time + "," + won;
    }

    public static StatsData decodeData(String encodedStats) {
        // Decode stats data
        // Example: HorseName1,DistanceTravelled1,Speed1,Time1,Won1
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
