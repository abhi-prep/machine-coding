package service.impl;

import java.util.HashMap;
import java.util.Map;

import exception.CronParseException;
import model.Command;
import service.CommandHandler;

public class StandardCommandHandler implements CommandHandler {
    @Override
    public Command parse(String command) {
        if (command == null || command.isEmpty() || command.isBlank()) {
            throw new CronParseException("Command cannot be null or empty " + command);
        }
        String[] tokens = command.split("\\s+");

        String name = tokens[0].trim();

        Map<String, String> args = new HashMap<>();

        int i = 1;
        while (i < tokens.length) {
            String arg = tokens[i].trim();
            if (!arg.startsWith("-")) {
                throw new CronParseException("Expected argument starting with '-', found: " + arg);
            }
            if (i + 1 >= tokens.length || tokens[i + 1].trim().startsWith("-")) {
                throw new CronParseException("Missing value for argument: " + arg);
            }
            String value = tokens[i + 1];
            args.put(arg, value);
            i += 2;
        }

        return new Command.Builder(name)
                .args(args)
                .build();
    }

    @Override
    public String format(Command command) {
        StringBuilder sb = new StringBuilder();
        if(command.getArgs() != null){
            command.getArgs().forEach((key, value) -> sb.append(key).append("=").append(value).append(" "));
        }
        return String.format("Name: %s | Args: %s", command.getName(), sb.toString().trim());
    }
}
