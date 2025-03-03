import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                System.out.println("""
                        Select the type of addition you would like to perform:
                        - 'fast' (specify name and texture) [default values for price, quantity & max uses = 1, rarity set to 'uncommon']
                        - 'uncommon' for fast mode with different pricing ( 1 head x 3 emeralds x 6 uses)
                        - 'rare' for fast mode with different pricing ( 1 head x 5 emeralds x 4 uses, rarity set to rare)
                        - 'personalised' (specify name, texture quantity and price)
                        - 'complete' (name, texture, quantity, price, rarity, trade usages)
                        This will enter a loop in which you will be continuously prompted to add entries,
                        digit 'exit' to return to this menu
                        'quit' to close the app""");
                String selection = reader.readLine().toLowerCase();

                if(selection.startsWith("q")){
                    action = "quit";
                    break;
                } else {
                    while (!selection.startsWith("e")){
                        System.out.print("Inserting a new head with ");
                        Map<String, String> preferences = getPreferences();
                        if (selection.startsWith("f")){
                            System.out.println("fast mode");
                            String name = null,texture = null;
                            for(String preference: preferences.keySet()){
                                if(preference.equals("usage")||
                                        preference.equals("rarity")||
                                        preference.equals("price")||
                                        preference.equals("quantity")) continue;
                                System.out.println("Add-loop: " + preference + '\n'+
                                        preferences.get(preference)+ '\n'+
                                        "('exit' to return to preferences selection)");
                                String stringValue = reader.readLine();
                                if(stringValue.equals("exit")){
                                    selection = "exit";
                                    break;
                                }

                                switch (preference){
                                    case "name":
                                        name = stringValue;
                                        break;
                                    case "texture":
                                        texture = stringValue;
                                        break;
                                }
                            }

                            tradeFile.addNewTrade(
                                    1,
                                    1,
                                    1,
                                    name,
                                    "rare",
                                    texture
                            );
                        }
                        else if(selection.startsWith("c")) {
                            System.out.println("complete mode");
                            int price = 1, quantity = 1, usages = 1;
                            String name = null,texture = null,rarity = null;
                            for(String preference: preferences.keySet()){
                                System.out.println("Add-loop: " + preference + '\n'+
                                        preferences.get(preference)+ '\n'+
                                        "('exit' to return to preferences selection)");
                                String stringValue = reader.readLine();
                                if(stringValue.equals("exit")){
                                    selection = "exit";
                                    break;
                                }
                                int value = 1;
                                if(stringValue.matches("\\d+")){
                                    value = Integer.parseInt(stringValue);
                                }

                                switch (preference){
                                    case "name":
                                        name = stringValue;
                                        break;
                                    case "texture":
                                        texture = stringValue;
                                        break;
                                    case "rarity":
                                        rarity = stringValue;
                                        break;
                                    case "price":
                                        price = value;
                                        break;
                                    case "quantity":
                                        quantity = value;
                                        break;
                                    case "usages":
                                        usages = value;
                                        break;
                                }
                            }

                            tradeFile.addNewTrade(
                                    usages,
                                    price,
                                    quantity,
                                    name,
                                    rarity,
                                    texture
                            );
                        }
                        else if (selection.startsWith("p")){
                            System.out.println("personalised mode");
                            int price = 1, quantity = 1, usages = 1;
                            String name = null,texture = null;
                            for(String preference: preferences.keySet()){
                                if(preference.equals("usage")||preference.equals("rarity")) continue;
                                System.out.println("Add-loop: " + preference + '\n'+
                                        preferences.get(preference)+ '\n'+
                                        "('exit' to return to preferences selection)");
                                String stringValue = reader.readLine();
                                if(stringValue.equals("exit")){
                                    selection = "exit";
                                    break;
                                }
                                int value = 1;
                                if(stringValue.matches("\\d+")){
                                    value = Integer.parseInt(stringValue);
                                }

                                switch (preference){
                                    case "name":
                                        name = stringValue;
                                        break;
                                    case "texture":
                                        texture = stringValue;
                                        break;
                                    case "price":
                                        price = value;
                                        break;
                                    case "quantity":
                                        quantity = value;
                                        break;
                                }
                            }

                            tradeFile.addNewTrade(
                                    usages,
                                    price,
                                    quantity,
                                    name,
                                    AddTradeFile.DEFAULT_RARITY,
                                    texture
                            );
                        }
                        else if (selection.startsWith("r")){
                            System.out.println("rare mode");
                            String name = null,texture = null;
                            for(String preference: preferences.keySet()){
                                if(preference.equals("usage")||
                                        preference.equals("rarity")||
                                preference.equals("price")||
                                preference.equals("quantity")) continue;
                                System.out.println("Add-loop: " + preference + '\n'+
                                        preferences.get(preference)+ '\n'+
                                        "('exit' to return to preferences selection)");
                                String stringValue = reader.readLine();
                                if(stringValue.equals("exit")){
                                    selection = "exit";
                                    break;
                                }

                                switch (preference){
                                    case "name":
                                        name = stringValue;
                                        break;
                                    case "texture":
                                        texture = stringValue;
                                        break;
                                }
                            }

                            tradeFile.addNewTrade(
                                    4,
                                    5,
                                    1,
                                    name,
                                    "rare",
                                    texture
                            );
                        }
                        else if (selection.startsWith("u")){
                            System.out.println("uncommon mode");
                            String name = null,texture = null;
                            for(String preference: preferences.keySet()){
                                if(preference.equals("usage")||
                                        preference.equals("rarity")||
                                        preference.equals("price")||
                                        preference.equals("quantity")) continue;
                                System.out.println("Add-loop: " + preference + '\n'+
                                        preferences.get(preference)+ '\n'+
                                        "('exit' to return to preferences selection)");
                                String stringValue = reader.readLine();
                                if(stringValue.equals("exit")){
                                    selection = "exit";
                                    break;
                                }

                                switch (preference){
                                    case "name":
                                        name = stringValue;
                                        break;
                                    case "texture":
                                        texture = stringValue;
                                        break;
                                }
                            }

                            tradeFile.addNewTrade(
                                    6,
                                    3,
                                    1,
                                    name,
                                    AddTradeFile.DEFAULT_RARITY,
                                    texture
                            );
                        }

                    }
                }



                /*System.out.println("""
                        What would you like to customise from the available properties?
                        Name and texture are mandatory.
                        - usages
                        - price
                        - quantity
                        - rarity
                        Leave empty for quick addition, quit for quitting.""");
                String preferenceString = reader.readLine().toLowerCase();
                Map<String, String> preferences = getPreferences(preferenceString);
                while (!action.equals("exit")){
                    Map<String,String> dataMap = new HashMap<>();
                    for(String preference : preferences.keySet()){
                        System.out.println("Add-loop: " + preference + '\n'+
                                preferences.get(preference)+ '\n'+
                                "(exit to return to preferences selection)");
                        String propertyValue = reader.readLine();
                        if(propertyValue.equals("e")||propertyValue.equals("exit")){
                            action = "exit";
                            break;
                        } else {
                            dataMap.put(preference,propertyValue);
                        }
                    }

                    //addition code depending on what properties have been specified.
                    tradeFile.addNewTrade(dataMap);
                }*/
            }

            tradeFile.close();

        }
    }

    private static Map<String, String> getPreferences(String preferenceString) {
        Map<String,String> preferences = new HashMap<>();
        preferences.put("name","The name to be displayed for this head item.");
        preferences.put("texture", "The value used to compute the texture of the head" +
                "(a value composed of characters and digits ending with an equal '=' sign)");
        for(String preference : preferenceString.split(" ")){
            if(preference.equals("usages")) preferences.put("usage", "How many times this trade can be used from a single trader.");
            if(preference.equals("price")) preferences.put("price", "How many emeralds this trade should cost.");
            if(preference.equals("quantity")) preferences.put("quantity", "How many items the trade will give per transaction.");
            if(preference.equals("rarity")) preferences.put("rarity", "How should the head be displayed (see https://minecraft.wiki/w/Rarity)");
        }
        return preferences;
    }

    private static Map<String, String> getPreferences() {
        Map<String,String> preferences = new HashMap<>();
        preferences.put("name","The name to be displayed for this head item.");
        preferences.put("texture", "The value used to compute the texture of the head" +
                "(a value composed of characters and digits ending with an equal '=' sign)");
        preferences.put("usage", "How many times this trade can be used from a single trader.");
        preferences.put("price", "How many emeralds this trade should cost.");
        preferences.put("quantity", "How many items the trade will give per transaction.");
        preferences.put("rarity", "How should the head be displayed (see https://minecraft.wiki/w/Rarity)");
        return preferences;
    }
}