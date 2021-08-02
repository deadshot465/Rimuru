package commands;

import discord4j.core.DiscordClient;
import discord4j.core.object.entity.Message;
import interfaces.Command;

import java.util.Vector;

public class Ping implements Command {
    @Override
    public void run(DiscordClient client, Message msg, Vector<String> args) {

        for (var s :
                args) {
            System.out.println(s);
        }

        final var channel = msg.getChannel().block();
        channel.createMessage("Hello").block();
    }
}
