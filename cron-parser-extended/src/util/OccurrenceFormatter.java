package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OccurrenceFormatter {
    public static String formatOccurrences(List<LocalDateTime> occurrences) {
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dayFmt = DateTimeFormatter.ofPattern("EEEE");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

        StringBuilder sb = new StringBuilder();
        sb.append("Here are the next ").append(occurrences.size()).append(" occurrences:\n");
        sb.append(String.format("%-12s | %-10s | %s%n", "Date", "Day", "Time"));
        sb.append("-------------|------------|------\n");

        for (LocalDateTime dt : occurrences) {
            String date = dt.format(dateFmt);
            String day = dt.format(dayFmt);
            String time = dt.format(timeFmt);
            sb.append(String.format("%-12s | %-10s | %s%n", date, day, time));
        }
        return sb.toString();
    }
}