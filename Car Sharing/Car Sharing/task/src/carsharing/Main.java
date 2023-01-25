package carsharing;

public class Main {

    public static void main(String[] args) {
        String tmp = null;
        if (args.length == 2) {
            tmp = args[1];
        }
        DBRep.initRepo(tmp);

        Menu.start();
    }
}