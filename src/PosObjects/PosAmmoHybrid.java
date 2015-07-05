package PosObjects;

/**
 * @author (James Bacon)
 * @version (Created 08/07/2008)
 */
public class PosAmmoHybrid extends PosAmmo
{
    private double rangeBonus,
                   capacitorBonus;
    
    public PosAmmoHybrid(double rangeBonus, double capacitorBonus, String chargeSize, boolean faction, String name, 
                         double emDamage, double explosiveDamage, double kineticDamage, double thermalDamage, int mass, 
                         double volume, String description, double basePrice)
    {
        super("AmmoHybrid", chargeSize, faction, name, emDamage, explosiveDamage, kineticDamage, thermalDamage, mass, volume, description, basePrice);
        
        this.rangeBonus = rangeBonus;
        this.capacitorBonus = capacitorBonus;
    }
    
    public double getRangeBonus()          {return rangeBonus;}    
    public double getCapacitorBonus()      {return capacitorBonus;}    
}