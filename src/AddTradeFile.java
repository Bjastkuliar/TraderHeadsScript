import java.io.*;
import java.util.*;

public class AddTradeFile{

    List<String> rarityRanks = List.of(
            "common",
            "uncommon",
            "rare",
            "epic"
    );

    public static final String DEFAULT_RARITY = "uncommon";
    private final BufferedReader reader;
    private final BufferedWriter writer;
    public static final String fileName = "add_trade.mcfunction";
    private static int entryCount;
    private static String lastEntryName;
    private static final String macro = "execute if score @s wt_tradeIndex matches %d run data modify entity @s Offers.Recipes prepend value {rewardExp:0b,maxUses:%d,buy:{id:\"minecraft:emerald\",count:%d},sell:{id:\"minecraft:player_head\",count:%d,components:{\"minecraft:item_name\":'\"%s\"',\"minecraft:rarity\":\"%s\",\"minecraft:profile\":{properties:[{name:\"textures\",value:\"%s\"}]}}}}";
    public AddTradeFile(String path) throws IOException {
        if(path != null && !path.isBlank()) {
            if(!path.endsWith(fileName)){
                path = path+'\\'+fileName;
                System.out.println("appended filename: "+path+'\n');
            }
            reader = new BufferedReader(new FileReader(path));
            writer = new BufferedWriter(new FileWriter(path,true));
            if(reader.ready()){
                String currentLine,lastLine = null;
                while ((currentLine = reader.readLine())!=null){
                    if(!currentLine.isBlank()&&currentLine.startsWith("execute if")){
                        lastLine = currentLine;
                    }
                }
                if(lastLine!=null&&!lastLine.isBlank())
                    processLastLine(lastLine);
                else
                    System.out.println("last line is null");
            }
            reader.close();
        }else
            throw new NullPointerException("File path cannot be empty!");
    }

    private static void processLastLine(String lastFileLine){
        String[] words = lastFileLine.split(" ");
        lastEntryName = lastFileLine.split("'")[1];
        entryCount = Integer.parseInt(words[6]);
    }

    public void close() throws IOException {
        reader.close();
    }

    /**
     * Writes the provided string to the file.
     * @param formatted The formatted input for the file to be written.
     * @return whether the operation was successful.
     */
    private boolean writeNewTrade (String formatted){
        try {
            writer.newLine();
            writer.write(formatted);
            writer.flush();
            return true;
        } catch (IOException e) {
            System.err.println("Failed to write head '"+entryCount+"' to file:\n");
            e.printStackTrace();
        }
        return false;
    }
    
    public void addNewTrade(int maxTradeUsages,
                             int emeraldPrice,
                             int tradedAmount,
                             String headName,
                             String rarity,
                             String texture){
        System.out.println(String.format(AddTradeFile.macro,++entryCount,maxTradeUsages,emeraldPrice,tradedAmount,headName,rarity,texture));
        //writeNewTrade(String.format(AddTradeFile.macro,++entryCount,maxTradeUsages,emeraldPrice,tradedAmount,headName,rarity,texture));
    }

    public void addNewTrade(String headName, String texture){
        addNewTrade(1,1,1,headName,"uncommon",texture);
    }

    public void addNewTrade(String headName, String rarity, String texture){
        addNewTrade(1,1,1,headName,rarity,texture);
    }

    public void addNewTrade(String headName, int rarity, String texture){
        addNewTrade(1,1,1,headName, rarityRanks.get(rarity), texture);
    }

    public void addNewTrade(Map<String, String> dataMap) {
        String name = dataMap.get("name"),
                texture = dataMap.get("texture"),
                rarity,
        priceString = dataMap.get("price"),
        qtyString = dataMap.get("quantity"),
        rarityString = dataMap.get("rarity"),
        usagesString = dataMap.get("usages");

        int price,quantity,usages;

        if(rarityString!=null&&!rarityString.isBlank()){
            if(rarityString.matches("\\d+")){
                try{
                    rarity = rarityRanks.get(Integer.parseInt(rarityString));
                } catch (IndexOutOfBoundsException ignored){
                    rarity = DEFAULT_RARITY;
                }
            } else if(!rarityRanks.contains(rarityString)){
                rarity = DEFAULT_RARITY;
            } else {
                rarity = rarityString;
            }
        } else {
            rarity = DEFAULT_RARITY;
        }

        if(usagesString!=null&&!usagesString.isBlank()){
            if(usagesString.matches("\\d+")){
                usages = Integer.parseInt(usagesString);
            } else {
                usages = 1;
            }
        } else {
            usages = 1;
        }

        if(priceString!=null&&!priceString.isBlank()){
            if(priceString.matches("\\d+")){
                price = Integer.parseInt(priceString);
            } else {
                price = 1;
            }
        } else {
            price = 1;
        }

        if(qtyString!=null&&!qtyString.isBlank()){
            if(qtyString.matches("\\d+")){
                quantity = Integer.parseInt(qtyString);
            } else {
                quantity = 1;
            }
        } else {
            quantity = 1;
        }

        addNewTrade(
                usages,
                price,
                quantity,
                name,
                rarity,
                texture
        );
    }
}
