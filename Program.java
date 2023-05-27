import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args)  
    {
        List<Integer> populations = new ArrayList<Integer>();

        if (args.length == 0)
        {
            System.out.println("Usage programName colonyPopulation colonyPopulation colonyPopulation ... ");
        }else{
            for(int i = 0; i < args.length; i++){
                populations.add(GetPopulation(args[i]));
            }
            Game game = new Game(populations);
            game.Run();
        }
    }
    private static int GetPopulation(String input)
    {
        return Integer.parseInt(input);
    }
    

}
