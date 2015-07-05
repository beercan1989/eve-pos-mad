package PosObjects;

/**
 * @author (James Bacon)
 * @version (Created 08/07/2008)
 */
public class PosAmmoCrystal extends PosAmmo
{
    private double rangeBonus,
                   capacitorBonus,
                   volatility,
                   volatilityDamage,
                   crystalDamage;
    
    public PosAmmoCrystal(double rangeBonus, double capacitorBonus, double volatility, double volatilityDamage, double crystalDamage, 
                          String chargeSize, boolean faction, String name, double emDamage, double explosiveDamage, 
                          double kineticDamage, double thermalDamage, int mass, double volume, String description, double basePrice)
    {
        super("AmmoCrystal", chargeSize, faction, name, emDamage, explosiveDamage, kineticDamage, thermalDamage, mass, volume, description, basePrice);
        
        this.rangeBonus = rangeBonus;
        this.capacitorBonus = capacitorBonus;
    }
    
    public double getRangeBonus()          {return rangeBonus;}    
    public double getCapacitorBonus()      {return capacitorBonus;}    
    public double getVolatility()          {return volatility;}    
    public double getVolatilityDamage()    {return volatilityDamage;}    
    public double getCrystalDamage()       {return crystalDamage;}    
}