import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class MyTravelPlanner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter date of plan (YYYY-MM-DD): ");
        LocalDate dateOfPlan = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter weather prediction (Sunny/Cloudy/Rainy/Snowy): ");
        String weatherPrediction = scanner.nextLine();

        System.out.print("Enter time of first appointment (HH:mm): ");
        LocalTime firstAppointment = LocalTime.parse(scanner.nextLine());

        System.out.print("Enter time of last appointment (HH:mm): ");
        LocalTime lastAppointment = LocalTime.parse(scanner.nextLine());

        String plan = getPlan(dateOfPlan, weatherPrediction, firstAppointment, lastAppointment);
        System.out.println(plan);

        scanner.close();
    }

    public static String getPlan(LocalDate dateOfPlan, String weatherPrediction,
                                 LocalTime firstAppointment, LocalTime lastAppointment) {
        LocalTime latestTrainTime = findLatestTrainTime(dateOfPlan, firstAppointment);
        LocalTime earliestReturnTrainTime = lastAppointment.plusHours(1);

        if (earliestReturnTrainTime.isAfter(LocalTime.of(22, 0)) ||
                latestTrainTime.isBefore(LocalTime.of(6, 0))) {
            return "Please cancel or reschedule your appointments on " + dateOfPlan + ".";
        }

        if (weatherPrediction.equals("Rainy") || weatherPrediction.equals("Snowy")) {
            return "Please drive on " + dateOfPlan + ", and leave the house at " + latestTrainTime.minusHours(1) + ".";
        }

        return "Please take the " + latestTrainTime + " train to go to the city (one hour before the first appointment), and " +
                earliestReturnTrainTime + " train (one hour after the last appointment time) to get back home on " + dateOfPlan + ".";
    }

    private static LocalTime findLatestTrainTime(LocalDate dateOfPlan, LocalTime firstAppointment) {
        LocalTime lastTrainTime;
        if (dateOfPlan.getDayOfWeek().getValue() >= 6) {
            lastTrainTime = LocalTime.of(22, 0);
        } else {
            lastTrainTime = LocalTime.of(23, 0);
        }

        LocalTime latestTrainTime = firstAppointment.minusHours(1);
        return latestTrainTime.isAfter(lastTrainTime) ? lastTrainTime : latestTrainTime;
    }
}
