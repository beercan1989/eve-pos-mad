package PosObjects;

/**
 * @author (James Bacon)
 * @version (Created 08/07/2008)
 */
public class PosMissile extends PosModule
{
    private double activationProximity,
                   maxLockedTargets,
                   scanResolution,
                   rateOfFire,
                   damageBonus,
                   velocityBonus,
                   flightTimeBonus,
                   explosionVelocityBonus,
                   explosionRadiusBonus,
                   capacity,
                   chargeType;
    
    public PosMissile(double requiredPowerGrid, double requiredCPU, double activationProximity, double maxLockedTargets, double scanResolution, double rateOfFire,
                      double damageBonus, double velocityBonus, double flightTimeBonus, double explosionVelocityBonus, double explosionRadiusBonus, double capacity, double chargeType,
                      boolean factionModule, double shieldCapacity, double shieldRechargeRate, double shieldEmResistance, double shieldExplosiveResistance, 
                      double shieldKineticResistance, double shieldThermalResistance, double armorHitpoints, double armorEmResitsance, 
                      double armorExplosiveResistance, double armorKineticResistance, double armorThermalResistance, double strutureHitpoints, 
                      double strutureEmResitsance, double strutureExplosiveResistance, double strutureKineticResistance, double strutureThermalResistance, 
                      double signatureRadius, int sensorStrengthRadar, int sensorStrengthLadar, int sensorStrengthMagetometric, int sensorStrengthGravimetric, 
                      double anchoringDelay, double unanchoringDelay, double onliningDelay, int mass, double volume, boolean playerControlable, String name,
                      String description, double basePrice)
    {
        super(factionModule, shieldCapacity, shieldRechargeRate, shieldEmResistance, shieldExplosiveResistance, 
              shieldKineticResistance, shieldThermalResistance, armorHitpoints, armorEmResitsance, armorExplosiveResistance, 
              armorKineticResistance, armorThermalResistance, strutureHitpoints, strutureEmResitsance, strutureExplosiveResistance, 
              strutureKineticResistance, strutureThermalResistance, signatureRadius, sensorStrengthRadar, sensorStrengthLadar, 
              sensorStrengthMagetometric, sensorStrengthGravimetric, anchoringDelay, unanchoringDelay, onliningDelay, mass, 
              volume, playerControlable, "Missile", name, description, basePrice, requiredPowerGrid, requiredCPU);
        
        this.activationProximity = activationProximity;
        this.maxLockedTargets = maxLockedTargets;
        this.scanResolution = scanResolution;
        this.rateOfFire = rateOfFire;
        this.damageBonus = damageBonus;
        this.velocityBonus = velocityBonus;
        this.flightTimeBonus = flightTimeBonus;
        this.explosionVelocityBonus = explosionVelocityBonus;
        this.explosionRadiusBonus = explosionRadiusBonus;
        this.capacity = capacity;
        this.chargeType = chargeType;
    }
        
    public double getActivationProximity()         {return activationProximity;}    
    public double getMaxLockedTargets()            {return maxLockedTargets;}    
    public double getScanResolution()              {return scanResolution;}    
    public double getRateOfFire()                  {return rateOfFire;}    
    public double getDamageBonus()                 {return damageBonus;}    
    public double getVelocityBonus()               {return velocityBonus;}    
    public double getFlightTimeBonus()             {return flightTimeBonus;}    
    public double getExplosionVelocityBonus()      {return explosionVelocityBonus;}    
    public double getCapacity()                    {return capacity;}    
    public double getChargeType()                  {return chargeType;}
    public double getExplosionRadiusBonus()        {return explosionRadiusBonus;}
}
