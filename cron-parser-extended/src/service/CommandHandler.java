package service;

import model.Command;

public interface CommandHandler {
    Command parse(String command);
    String format(Command command);
}
