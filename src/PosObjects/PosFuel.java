package PosObjects;

/**
 * @author (James Bacon)
 * @version (Created 08/07/2008)
 */
public class PosFuel extends Item
{
    private String name;
    private double amount;
    
    public PosFuel(String name, int mass, double volume, String description, double basePrice, double amount)
    {   
        super(mass, volume, "Fuel", description, basePrice);
        this.name = name;
        this.amount = amount;
    }
    
    public String getName()  { return name;   }
    public double getAmount(){ return amount; }
}