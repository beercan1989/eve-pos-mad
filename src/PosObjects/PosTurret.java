package PosObjects;

/**
 * @author (James Bacon)
 * @version (Created 08/07/2008)
 */
public class PosTurret extends PosModule
{
    private double activationProximity,
                   maxLockedTargets,
                   scanResolution,
                   rateOfFire,
                   optimumRange,
                   accuracyFalloff,
                   damageModifier,
                   trackingSpeed,
                   signatureResolution,
                   capacity,
                   chargeSize;
    
    public PosTurret(double requiredPowerGrid, double requiredCPU, double activationProximity, double maxLockedTargets, double scanResolution, double rateOfFire,
                     double optimumRange, double accuracyFalloff, double damageModifier, double trackingSpeed, double signatureResolution, double capacity,
                     double chargeSize, boolean factionModule, double shieldCapacity, double shieldRechargeRate, double shieldEmResistance, double shieldExplosiveResistance, 
                     double shieldKineticResistance, double shieldThermalResistance, double armorHitpoints, double armorEmResitsance, double armorExplosiveResistance, 
                     double armorKineticResistance, double armorThermalResistance, double strutureHitpoints, double strutureEmResitsance, double strutureExplosiveResistance, 
                     double strutureKineticResistance, double strutureThermalResistance, double signatureRadius, int sensorStrengthRadar, int sensorStrengthLadar, 
                     int sensorStrengthMagetometric, int sensorStrengthGravimetric, double anchoringDelay, double unanchoringDelay, double onliningDelay, int mass, 
                     double volume, boolean playerControlable, String name, String description, double basePrice)
    {
        super(factionModule, shieldCapacity, shieldRechargeRate, shieldEmResistance, shieldExplosiveResistance, 
              shieldKineticResistance, shieldThermalResistance, armorHitpoints, armorEmResitsance, armorExplosiveResistance, 
              armorKineticResistance, armorThermalResistance, strutureHitpoints, strutureEmResitsance, strutureExplosiveResistance, 
              strutureKineticResistance, strutureThermalResistance, signatureRadius, sensorStrengthRadar, sensorStrengthLadar, 
              sensorStrengthMagetometric, sensorStrengthGravimetric, anchoringDelay, unanchoringDelay, onliningDelay, mass, 
              volume, playerControlable, "Turret", name, description, basePrice, requiredPowerGrid, requiredCPU);
              
        this.activationProximity = activationProximity;
        this.maxLockedTargets = maxLockedTargets;
        this.scanResolution = scanResolution;
        this.rateOfFire = rateOfFire;
        this.optimumRange = optimumRange;
        this.accuracyFalloff = accuracyFalloff;
        this.damageModifier = damageModifier;
        this.trackingSpeed = trackingSpeed;
        this.signatureResolution = signatureResolution;
        this.capacity = capacity;
        this.chargeSize = chargeSize;
    }
        
    public double getActivationProximity()         {return activationProximity;}    
    public double getMaxLockedTargets()            {return maxLockedTargets;}    
    public double getScanResolution()              {return scanResolution;}    
    public double getRateOfFire()                  {return rateOfFire;}    
    public double getOptimumRange()                {return optimumRange;}    
    public double getAccuracyFalloff()             {return accuracyFalloff;}    
    public double getDamageModifier()              {return damageModifier;}    
    public double getTrackingSpeed()               {return trackingSpeed;}    
    public double getSignatureResolution()         {return signatureResolution;}    
    public double getCapacity()                    {return capacity;}    
    public double getChargeSize()                  {return chargeSize;}    
}