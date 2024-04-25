import java.util.Scanner;
public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of lanes: ");
        int lanes = scanner.nextInt();
        System.out.println("Enter Distance: ");
        int distance = scanner.nextInt();
        Race race = new Race(distance, lanes);
        for (int i = 0; i < lanes; i++) {
            System.out.println("Would you like a horse at lane "+(i+1)+"? (Y/N)");
            String response = scanner.next();
            if (response.equals("N")) {
                continue;
            } else if (!response.equals("Y")) {
                System.out.println("Invalid Response");
                i--;
                continue;
            }
            System.out.println("Enter Horse Name: ");
            String name = scanner.next();
            System.out.println("Enter Horse Symbol: ");
            char symbol = scanner.next().charAt(0);
            System.out.println("Enter Horse Confidence: ");
            double confidence = scanner.nextDouble();
            Horse horse = new Horse(symbol, name, confidence);
            race.addHorse(horse, i+1);
        }
        while (true) {
            System.out.println("Would you like to start? (Y/N)");
            String response = scanner.next();
            if (response.equals("N")) {
                break;
            } else if (!response.equals("Y")) {
                System.out.println("Invalid Response");
                continue;
            }
            race.startRace();
        }
    }
}
