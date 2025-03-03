import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the absolute path at which the" +
                "'add_trade.mcfunction' file resides");
        String path = reader.readLine();
        if(path.equals("h")||path.equals("here")) path = System.getProperty("user.dir");
        System.out.println("Is the file residing at: '" + path + "'? (Y/N) q for quitting");
        String confirm = reader.readLine().toLowerCase();
        while (!(confirm.equals("y") || confirm.equals("yes"))) {
            if (confirm.equals("q") || confirm.equals("quit")) {
                System.exit(0);
            } else {
                System.out.println("Enter the path again:");
                path = reader.readLine();
            }
            System.out.println("Is the file residing at: '" + path + "'? (Y/N) q for quitting");
            confirm = reader.readLine().toLowerCase();
        }

        AddTradeFile tradeFile = new AddTradeFile(path);

        String action = "";

        while (!(action.equals("q") || action.equals("quit"))) {
            System.out.println("""
                    What would you like to do?
                    'a' or 'add' to add a new trade
                    'q' or 'quit' to exit""");
            action = reader.readLine();

            while (action.equals("a") || action.equals("add")) {
                System.out.println("Quick-add: insert the new head-name (case sensitive) (q to quit)");
                String name = reader.readLine();
                if(name.equals("q")||name.equals("quit")) {
                    action = "quit";
                } else {
                    System.out.println("Input the texture string (case sensitive)");
                    String texture = reader.readLine();
                    tradeFile.addNewTrade(name, texture);
                }
            }

            tradeFile.close();

        }
    }
}