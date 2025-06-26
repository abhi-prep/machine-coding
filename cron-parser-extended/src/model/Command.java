package model;

import exception.CronParseException;

import java.util.HashMap;
import java.util.Map;

public class Command {
    private final String name;
    private final Map<String, String> args;

    public Command(String name, Map<String, String> args) {
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public static class Builder {
        private String name;
        private Map<String, String> args;

        public Builder(String name) {
            if(name == null || name.isBlank()){
                throw new CronParseException("Command can't be null");
            }
            this.name = name;
            this.args = new HashMap<>();
        }

        public Builder args(Map<String, String> args) {
            this.args = args;
            return this;
        }

        public Command build() {
            return new Command(name, args);
        }
    }
}
