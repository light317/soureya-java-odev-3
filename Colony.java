public class Colony {
    private int Population;
    private Character Symbol;
    private int FoodAmount;
    private int Wins;
    private int Losses;
    private boolean IsAlive;
    Production ProductionStrategy;
    Tactic WarTactic;

    public Colony(int population, Character symbol, Production productionStrategy, Tactic warTactic){
        Population = population;
        FoodAmount = population*population;
        Symbol = symbol;
        Wins = 0;
        Losses = 0;
        IsAlive = true;
        ProductionStrategy = productionStrategy;
        WarTactic = warTactic;
    }

    public int GetWins(){
        return Wins;
    }

    public int GetLosses(){
        return Losses;
    }

    public Character GetSymbol(){
        return Symbol;
    }

    public int GetPopulation(){
        return Population;
    }

    public int GetFoodAmount(){
        return FoodAmount;
    }

    public void IncrementWins(){
        Wins++;
    }

    public void IncrementLosses(){
        Losses++;
    }

    public void IncreasePopulation(int amountToIncrease){
        Population += amountToIncrease;
    }

    public void DecreasePopulation(int amountToDecrease){

        Population -= amountToDecrease;
        if(Population < 0){
            Population = 0;
        }
    }

    public void IncreaseFood(int amountToIncrease){
        FoodAmount += amountToIncrease;
    }

    public void DecreaseFood(int amountToDecrease){

        FoodAmount -= amountToDecrease;
        if(FoodAmount < 0){
            FoodAmount = 0;
        }
    }

    public void Update(){
        int populationGrowth = (int)(Population * (20/100));
        int foodLoss = populationGrowth * 2;
        IncreasePopulation(populationGrowth);
        DecreaseFood(foodLoss);
    }

    public int ReduceFoodByPercentage(int percentage){
        System.out.println()
        int amountToBeReduced = (int)(FoodAmount * (float)(percentage/100));
        DecreaseFood(amountToBeReduced);

        return amountToBeReduced;
    }

    public int ReducePopulationByPercentage(int percentage){
        int amountToBeReduced = (int)(Population * (float)(percentage/100));
        DecreasePopulation(amountToBeReduced);

        return amountToBeReduced;
    }

    public boolean IsAlive()
    {
        return IsAlive;
    }

    public void Kill(){
        IsAlive = false;
    }
}
