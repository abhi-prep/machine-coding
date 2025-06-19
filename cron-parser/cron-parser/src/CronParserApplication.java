import controller.CronParserController;
import exception.CronParseException;
import factory.CronParserFactory;

public class CronParserApplication {
    public static void main(String[] args) {
        if(args.length != 1){
            System.err.println("Usage: Please refer the README.md file.");
            System.exit(1);
        }

        String cronExpression = args[0];

        try{
            CronParserController controller = CronParserFactory.getInstance().getController();
            String formattedOutput = controller.processCronExpression(cronExpression);
            System.out.println(formattedOutput);
        } catch (CronParseException e){
            System.err.println("Error parsing cron expression: " + e.getMessage());
            System.exit(1);
        } catch (Exception e){
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

    }
}
