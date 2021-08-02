package interfaces;

import discord4j.core.DiscordClient;
import discord4j.core.object.entity.Message;

import java.util.Vector;

public interface Command {
    void run(DiscordClient client, Message msg, Vector<String> args);
}
