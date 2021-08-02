import discord4j.core.DiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String[] args) {
        final var dotenv = Dotenv.load();
        final var token = dotenv.get("TOKEN");
        final var prefix = dotenv.get("PREFIX");

        final var botClient = DiscordClient.create(token);
        final var gateWay = botClient.login().block();

        gateWay.on(ReadyEvent.class)
                .subscribe(event -> System.out.printf("%s#%s has logged in.", event.getSelf().getUsername(), event.getSelf().getDiscriminator()));

        gateWay.on(MessageCreateEvent.class).subscribe(event -> {
            final var message = event.getMessage();

            if (message.getContent().equals(prefix + "ping")) {
                final var channel = message.getChannel().block();
                channel.createMessage("Hello").block();
            }
        });
        gateWay.onDisconnect().block();

    }
}
