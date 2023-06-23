package setup;

import java.util.Random;

/** RandomHello selects and prints a random greeting. */
public class RandomHello {

    /**
     * Greeting in Spanish.
     */
    public static final String[] FIVE_GREETINGS = new String[]{"Hello World",
                                                               "Hola Mundo",
                                                               "Bonjour Monde",
                                                               "Hallo Welt",
                                                               "Ciao Mondo"};

    /**
     * Prints a random greeting to the console.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        RandomHello randomHello = new RandomHello();
        System.out.println(randomHello.getGreeting());
    }

    /** @return a greeting, randomly chosen from five possibilities */
    public String getGreeting() {
        // YOUR CODE GOES HERE
        String[] greetings = new String[]{"Hello World",
                                          "Hola Mundo",
                                          "Bonjour Monde",
                                          "Hallo Welt",
                                          "Ciao Mondo"};
        Random rand = new Random();
        int idx = rand.nextInt(5);

        return FIVE_GREETINGS[idx];
    }
}
