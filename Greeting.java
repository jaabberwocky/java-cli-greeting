///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS info.picocli:picocli:4.5.0

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "Greeting", mixinStandardHelpOptions = true, version = "Greeting 0.1",
        description = "Greeting made with jbang")
class Greeting implements Callable<Integer> {

    @Parameters(index = "0", description = "Person to greet", defaultValue = "World!")
    private String greeting;

    @Parameters(index = "1", description = "How many times to repeat the command", defaultValue = "1")
    private String repeat;

    @CommandLine.Option(
            names = {"-l", "--lang"},
            description = "Language to use: en (English), cn (Mandarin), de (Deutsch), fr (French)",
            required = false,
            defaultValue = "en"
    )
    private String language;

    public static void main(String... args) {
        int exitCode = new CommandLine(new Greeting()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        String s = "";
        int i = 0;
        int rep = Integer.parseInt(repeat);

        if (rep < 1) {
            throw new IllegalArgumentException("Repeat value less than 1");
        }

        switch (language) {
            case "en":
                s = "Hello ";
                break;
            case "de":
                s = "Hallo ";
                break;
            case "cn":
                s = "你好 ";
                break;
            case "fr":
                s = "Bonjour ";
            default:
                throw new IllegalArgumentException("Language not recognised");
        }

        while (i < rep) {
            System.out.println(s + greeting);
            i += 1;
        }
        return 0;
    }
}
