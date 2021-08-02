import commands.Ping;
import discord4j.core.DiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import interfaces.Command;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        final var dotenv = Dotenv.load();
        final var token = dotenv.get("TOKEN");
        final var prefix = dotenv.get("PREFIX");

        final var botClient = DiscordClient.create(token);
        final var gateWay = botClient.login().block();
        final var commands = new HashMap<String, Command>();
        commands.put("ping", new Ping());

        gateWay.on(ReadyEvent.class)
                .subscribe(event -> System.out.printf("%s#%s has logged in.\n", event.getSelf().getUsername(), event.getSelf().getDiscriminator()));

        gateWay.on(MessageCreateEvent.class).subscribe(event -> {
            final var message = event.getMessage();
            final var content = message.getContent();

            if (!content.startsWith(prefix)) return;

            final var rest = content.substring(prefix.length());
            final var arguments = rest.split(" ");

            //final var cmd = arguments[0].toLowerCase();

            final var argumentList = new Vector<>(Arrays.stream(arguments).toList());
            final var cmd = argumentList.remove(0).toLowerCase();
            commands.get(cmd).run(botClient, message, argumentList);
        });

        gateWay.onDisconnect().block();

    }
}
