import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the absolute path at which the" +
                "'add_trade.mcfunction' file resides");
        String path = reader.readLine();
        System.out.println("Is the file residing at: '"+path+"'? (Y/N) q for quitting");
        String confirm = reader.readLine().toLowerCase();
        while (!(confirm.equals("y") || confirm.equals("yes"))){
            if(confirm.equals("q")|| confirm.equals("quit")){
                System.exit(0);
            } else{
                System.out.println("Enter the path again:");
                path = reader.readLine();
            }
            System.out.println("Is the file residing at: '"+path+"'? (Y/N) q for quitting");
            confirm = reader.readLine().toLowerCase();
        }

        AddTradeFile tradeFile = new AddTradeFile(path);

        System.out.println("""
                What would you like to do?
                'a' or 'add' to add a new trade
                'q' or 'quit' to exit""");
        String action = reader.readLine();

        tradeFile.addNewTrade("HEADNAME",2,"TEXTURE");

        tradeFile.close();

    }
}