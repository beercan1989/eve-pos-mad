package PosObjects;

/**
 * @author (James Bacon)
 * @version (Created 08/07/2008)
 */
public class PosAmmoProjectile extends PosAmmo
{
    private double rangeBonus, trackingBonus;
    
    public PosAmmoProjectile(double rangeBonus, String chargeSize, boolean faction, String name, double emDamage, 
                             double explosiveDamage, double kineticDamage, double thermalDamage, int mass, 
                             double volume, String description, double basePrice, double trackingBonus)
    {
        super("AmmoProjectile", chargeSize, faction, name, emDamage, explosiveDamage, kineticDamage, thermalDamage, mass, volume, description, basePrice);
        
        this.rangeBonus    = rangeBonus;
        this.trackingBonus = trackingBonus;
    }
    
    public double getRangeBonus()          {return rangeBonus;}
    public double getTrackingBonus()          {return trackingBonus;}
}