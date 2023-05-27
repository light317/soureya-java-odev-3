import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {

    static List<Character> AvailableSymbols = new ArrayList<Character>(Arrays.asList('A','~','X','&','<','>','?','#','@','%','$','0','5'));
    static int NumberOfRounds = 0;
    static List<Colony> Colonies = new ArrayList<Colony>();

    public void Game(List<Integer> colonyPopulations){

    }

    public static void main(String[] args)  
    {
        if (args.length == 0)
        {
            System.out.println("Usage programName colonyPopulation colonyPopulation colonyPopulation ... ");
        }else
        {
            //Generate a colony for each given population
            for(int i = 0; i < args.length; i++){
                Colonies.add(GenerateColony(GetPopulation(args[i])));
            }
            
			//Keep the simulation going until only one colony is alive
            while(GetNumberOfLivingColonies() > 1){

                //Conduct one round of battle, where everyone fights everyone once.
                Battle();

				//Grow the population of the survivors
				GrowColonies();

                NumberOfRounds++;
            }

			//Show who won.
            PrintState();
        }
    }

    private static void Battle(){
        for(int i = 0; i < Colonies.size();i++){
            for(int j = i+1; j < Colonies.size();j++){

                Colony firstColony = Colonies.get(i);
                Colony secondColony = Colonies.get(j);

                if(!firstColony.IsAlive() || !secondColony.IsAlive())
                    continue;

                Fight(firstColony,secondColony);
            }
        }
    }

    private static void Fight(Colony firstColony, Colony secondColony)
    {
        int powerDifference = firstColony.WarTactic.GetStrength() - secondColony.WarTactic.GetStrength();

            //  less than 0 meaning second colony is stronger
            if(powerDifference < 0){
                CalculateFightEffect(secondColony, firstColony,powerDifference);
            }
            else if(powerDifference > 0){
                //first colony wins
                CalculateFightEffect(firstColony, secondColony,powerDifference);
            }
            else
			{
				// Equal power, the one with higher population wins

				if(firstColony.GetPopulation() > secondColony.GetPopulation())
				{
                    CalculateFightEffect(firstColony, secondColony, powerDifference);
  				}
				else if (firstColony.GetPopulation() < secondColony.GetPopulation())
				{
                    CalculateFightEffect(secondColony, firstColony, powerDifference);
				}
				else{
                    //random winner
                	System.out.println("It is a draw, randomly chosen winner.");

                    Random rand = new Random();
                    int randomIndex = rand.nextInt(100);

                	if(randomIndex > 50 ){
                    
                    CalculateFightEffect(firstColony, secondColony, powerDifference);
                    }
					else{
                        
                            CalculateFightEffect(secondColony, firstColony, powerDifference);
                    	}
					}
                }
    }

    private static void CalculateFightEffect(Colony winner, Colony loser, int powerDifference){
        winner.IncrementWins();
        loser.IncrementLosses();

        winner.IncreasePopulation(10);
        loser.DecreasePopulation(10);

        if(ShouldColonyDie(loser)){
            loser.Kill();
        }
    }

    private static int GetPowerDifferancePercentage(int powerDifference){
        return (int)((powerDifference/1000)*100);
    }

    private static boolean ShouldColonyDie(Colony colony){
        return colony.GetFoodAmount() <= 0 || colony.GetPopulation() <= 0;
    }

	private static void GrowColonies(){
		for(Colony colony : Colonies){
			if(colony.IsAlive()){
				colony.Update();
			}
		}
	}

    private static Colony GenerateColony(int population)
    {
        Colony colony = new Colony(population,GetSymbol(),GetRandomProduction(),GetRandomTactic());
        return colony;
    }

    private static Character GetSymbol(){
        Random rand = new Random();
        int randomIndex = rand.nextInt(AvailableSymbols.size());
        Character symbol = AvailableSymbols.get(randomIndex);
        AvailableSymbols.remove(randomIndex);
        return symbol;
    }

    private static int GetPopulation(String input)
    {
        return Integer.parseInt(input);
    }

    private static Production GetRandomProduction(){
        Random rand = new Random();
        int randomIndex = rand.nextInt(3);

        if(randomIndex == 0){
            return new ProductionA();
        }else if(randomIndex == 1){
            return new ProductionB();

        }else{
            return new ProductionC();
        }
    }

    private static Tactic GetRandomTactic(){
        Random rand = new Random();
        int randomIndex = rand.nextInt(3);

        if(randomIndex == 0){
            return new TacticA();
        }else if(randomIndex == 1){
            return new TacticB();
        }else{
            return new TacticC();
        }
    }

    private static void PrintState()
    {
        System.out.println("-----------------------------------------------------------------------");

        System.out.println("Number of rounds: " + NumberOfRounds+"\n");
        System.out.println("Colony      Population      Food Store      Wins    Losses\n");
        for(Colony colony : Colonies){
            if(colony.IsAlive()){
                System.out.println("    " + colony.GetSymbol() + "		" + colony.GetPopulation() + "          " + colony.GetFoodAmount() + "              " + colony.GetWins() + 
                "       " + colony.GetLosses());
                }
            else{
                System.out.println("    " + colony.GetSymbol() + "          " + "--" + "            " + "--" + "             " + "--" + 
                "         " + "--");
                }
            }
       

        System.out.println("------------------------------------------------------------------");
    }

    private static int GetNumberOfLivingColonies(){
        int count = 0;
        for (Colony colony : Colonies) {
            if(colony.IsAlive()){
                count++;
            }
        }
        return count;
    }

}