package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class FlashRepo {
    private final Scanner SC = new Scanner(System.in);
    private final Map<String, String> rep = new LinkedHashMap<>();
    private final Map<String, Integer> mistakes = new HashMap<>(); // term - mistake
    private final ArrayList<String> log = new ArrayList<>();

    public void addCard() {
        System.out.println("The card:");
        log.add("The card:");

        String term, def;

        term = SC.nextLine();
        log.add(term);

        if (rep.containsValue(term)) {
            System.out.println("The card \"" + term + "\" already exists.");
            log.add("The card \"" + term + "\" already exists.\n");
            System.out.println();
            return;
        }

        System.out.println("The definition of the card:");
        log.add("The definition of the card:");

        def = SC.nextLine();
        log.add(def);

        if (rep.containsKey(def)) {
            System.out.println("The definition \"" + def + "\" already exists.\n");
            log.add("The definition \"" + def + "\" already exists.\n");
            return;
        }
        System.out.println("The pair (\"" + term + "\":\"" + def + "\") has been added.\n");
        log.add("The pair (\"" + term + "\":\"" + def + "\") has been added.\n");
        rep.put(def, term);
    }

    public void removeCard() {
        System.out.println("Which card?");
        log.add("Which card?");

        String card = SC.nextLine();
        log.add(card);

        boolean removed = false;
        String keyToRemove = null;
        for (var elem : rep.entrySet()) {
            if (elem.getValue().equals(card)) {
                keyToRemove = elem.getKey();
                removed = true;
                break;
            }
        }
        if (removed) {
            rep.remove(keyToRemove);
            mistakes.remove(card);

            System.out.println("The card has been removed.\n");
            log.add("The card has been removed.\n");

        } else {
            System.out.println("Can't remove \"" + card + "\": there is no such card.\n");
            log.add("Can't remove \"" + card + "\": there is no such card.\n");
        }
    }

    private void remove(String card) {
        boolean removed = false;
        String keyToRemove = null;
        for (var elem : rep.entrySet()) {
            if (elem.getValue().equals(card)) {
                keyToRemove = elem.getKey();
                removed = true;
                break;
            }
        }
        if (removed) {
            rep.remove(keyToRemove);
            mistakes.remove(card);
        }
    }

    public void ask() {
        System.out.println("How many times to ask?");
        log.add("How many times to ask?");

        int reps = Integer.parseInt(SC.nextLine());
        log.add(Integer.toString(reps));

        int i = 0;
        for(var elem : rep.entrySet()) {

            System.out.println("print the definition of \"" + elem.getValue() + "\":");
            log.add("print the definition of \"" + elem.getValue() + "\":");

            String input = SC.nextLine();
            log.add(input);

            if (input.equals(elem.getKey())) {
                System.out.println("Correct!");
                log.add("Correct!");
            } else {
                System.out.print("Wrong. The right answer is \"" + elem.getKey() + "\"");
                mistakes.put(elem.getValue(), mistakes.getOrDefault(elem.getValue(), 0) + 1);
                if (rep.containsKey(input)) {
                    System.out.println(", but your definition is correct for \"" + rep.get(input) + "\".");
                    log.add("Wrong. The right answer is \"" + elem.getKey() + "\"" +
                            ", but your definition is correct for \"" + rep.get(input) + "\".");
                } else {
                    System.out.println(".");
                    log.add("Wrong. The right answer is \"" + elem.getKey() + "\".");
                }
            }
            i++;
            if (i >= reps) {
                break;
            }
        }
        System.out.println();
        log.add("\n");
    }

    public void importCards() {
        System.out.println("File name:");
        log.add("File name:");

        String fileName = SC.nextLine();
        log.add(fileName);

        importt(fileName);
    }

    public void importt(String fileName) {
        File file = new File(fileName);
        int i = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String term = scanner.nextLine();
                String def = scanner.nextLine();
                int count = Integer.parseInt(scanner.nextLine());
                i++;
                remove(term);
                rep.put(def, term);
                mistakes.put(term, count);
            }

            System.out.println(i + " cards have been loaded.\n");
            log.add(i + " cards have been loaded.\n");

        } catch (FileNotFoundException e) {

            System.out.println("File not found.\n");
            log.add("File not found.\n");

        }
    }

    public void exportCards() {
        System.out.println("File name:");
        log.add("File name:");

        String fileName = SC.nextLine();
        log.add(fileName);

        export(fileName);
    }

    public void export(String fileName) {
        File file = new File(fileName);
        int i = 0;
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.flush();
            for (var elem : rep.entrySet()) {
                printWriter.println(elem.getValue());
                printWriter.println(elem.getKey());
                printWriter.println(mistakes.getOrDefault(elem.getValue(), 0));
                i++;
            }

            System.out.println(i + " cards have been saved.\n");
            log.add(i + " cards have been saved.\n");

        } catch (IOException e) {

            System.out.println("File not found.\n");
            log.add("File not found.\n");

        }
    }

    public void exportLog() {
        System.out.println("File name:");
        log.add("File name:");

        String fileName = SC.nextLine();
        log.add(fileName);

        File file = new File(fileName);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (String val : log) {
                printWriter.println(val);
            }

            System.out.println("The log has been saved.\n");
            log.add("The log has been saved.\n");

        } catch (FileNotFoundException e) {

            System.out.println("File not found.\n");
            log.add("File not found.\n");

        }
    }

    public void addLog(String elem) {
        log.add(elem);
    }

    public void resetStats() {
        mistakes.clear();

        System.out.println("Card statistics have been reset.\n");
        log.add("Card statistics have been reset.\n");
    }

    public void printHardestCards() {
        ArrayList<String> populars = getHardestCards();
        if (populars == null) {
            System.out.println("There are no cards with errors.\n");
            log.add("There are no cards with errors.\n");
        } else if (populars.size() == 1) {
            System.out.println("The hardest card is \"" +
                    populars.get(0) + "\". You have "+ mistakes.get(populars.get(0)) +
                    " errors answering it\n");
            log.add("The hardest card is \"" +
                    populars.get(0) + "\". You have "+ mistakes.get(populars.get(0)) +
                    " errors answering it\n");
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("The hardest cards are");
            for (int i = 0; i < populars.size(); ++i) {
                builder.append(" \"").append(populars.get(i)).append("\"");
                if (i + 1 < populars.size()) {
                    builder.append(",");
                } else {
                    builder.append(". ");
                }
            }
            builder.append("You have ").append(mistakes.get(populars.get(0))).append(" errors answering them.\n");
            System.out.println(builder);
            log.add(builder.toString());
        }
    }

    private ArrayList<String> getHardestCards() {
        if (mistakes.size() == 0) {
            return null;
        }
        int max = -1;
        for (var elem : mistakes.entrySet()) {
            if (elem.getValue() > max) {
                max = elem.getValue();
            }
        }
        ArrayList<String> result = new ArrayList<>();
        for (var elem : mistakes.entrySet()) {
            if (elem.getValue() == max) {
                result.add(elem.getKey());
            }
        }
        return result;
    }
}
