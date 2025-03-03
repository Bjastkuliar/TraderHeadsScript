import java.io.*;
import java.util.Arrays;
import java.util.List;

public class AddTradeFile{

    List<String> rarityRanks = List.of(
            "epic",
            "rare",
            "uncommon",
            "common"
    );

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

    private void addNewTrade (
            String macro,
            int entryNumber,
            int maxTradeUsages,
            int emeraldPrice,
            int tradedAmount,
            String headName,
            String rarity,
            String texture){
        String formatted = String.format(macro,entryNumber,maxTradeUsages,emeraldPrice,tradedAmount,headName,rarity,texture);
        try {
            writer.newLine();
            writer.write(formatted);
            writer.flush();
        } catch (IOException e) {
            System.err.println("Failed to write head '"+headName+"' to file:\n");
            e.printStackTrace();
        }
    }

    public void addNewTrade(String headName, String texture){
        addNewTrade(macro,++entryCount,1,1,1,headName,"uncommon",texture);
    }

    public void addNewTrade(String headName, String rarity, String texture){
        addNewTrade(macro,++entryCount,1,1,1,headName,rarity,texture);
    }

    public void addNewTrade(String headName, int rarity, String texture){
        addNewTrade(macro,++entryCount,1,1,1,headName, rarityRanks.get(rarity), texture);
    }
}
