package PosObjects;

/**
 * @author (James Bacon)
 * @version (Created 08/07/2008)
 */
public class PosAmmoMissile extends PosAmmo
{
    private double maximumFlightTime,
                   maximumVelocity,
                   explosionVelocity,
                   explosionRadius;
    
    public PosAmmoMissile(double maximumFlightTime, double maximumVelocity, double explosionVelocity, double explosionRadius, 
                          String chargeSize, boolean faction, String name, double emDamage, double explosiveDamage, double kineticDamage,
                          double thermalDamage, int mass, double volume, String description, double basePrice)
    {
        super("AmmoMissile", chargeSize, faction, name, emDamage, explosiveDamage, kineticDamage, thermalDamage, mass, volume, description, basePrice);
        
        this.maximumFlightTime = maximumFlightTime;
        this.maximumVelocity = maximumVelocity;
        this.explosionVelocity = explosionVelocity;
        this.explosionRadius = explosionRadius;
    }
    
    public double getMaximumFlightTime()   {return maximumFlightTime;}    
    public double getMaximumVelocity()     {return maximumVelocity;}    
    public double getExplosionVelocity()   {return explosionVelocity;}    
    public double getExplosionRadius()     {return explosionRadius;}    
}