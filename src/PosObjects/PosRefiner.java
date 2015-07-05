package PosObjects;

/**
 * @author (James Bacon)
 * @version (Created 08/07/2008)
 */
public class PosRefiner extends PosModule
{
    private double storageCapacity,
                   maxOperationalDistance,
                   maxConcurrentOperationalUsers,
                   refiningYield,
                   operationDuration;
    
    public PosRefiner(double requiredPowerGrid, double requiredCPU, double storageCapacity, double maxOperationalDistance,
                               double maxConcurrentOperationalUsers, double refiningYield, double operationDuration, boolean factionModule, 
                               double shieldCapacity, double shieldRechargeRate, double shieldEmResistance, double shieldExplosiveResistance, 
                               double shieldKineticResistance, double shieldThermalResistance, double armorHitpoints, double armorEmResitsance, 
                               double armorExplosiveResistance, double armorKineticResistance, double armorThermalResistance, 
                               double strutureHitpoints, double strutureEmResitsance, double strutureExplosiveResistance,
                               double strutureKineticResistance, double strutureThermalResistance, double signatureRadius, int sensorStrengthRadar, 
                               int sensorStrengthLadar, int sensorStrengthMagetometric,int sensorStrengthGravimetric, double anchoringDelay, 
                               double unanchoringDelay, double onliningDelay, int mass, double volume, boolean playerControlable, 
                               String name, String description, double basePrice)
    {
        super(factionModule, shieldCapacity, shieldRechargeRate, shieldEmResistance, shieldExplosiveResistance, 
              shieldKineticResistance, shieldThermalResistance, armorHitpoints, armorEmResitsance, armorExplosiveResistance, 
              armorKineticResistance, armorThermalResistance, strutureHitpoints, strutureEmResitsance, strutureExplosiveResistance, 
              strutureKineticResistance, strutureThermalResistance, signatureRadius, sensorStrengthRadar, sensorStrengthLadar, 
              sensorStrengthMagetometric, sensorStrengthGravimetric, anchoringDelay, unanchoringDelay, onliningDelay, mass, 
              volume, playerControlable, "Refiner", name, description, basePrice, requiredPowerGrid, requiredCPU);
              
        this.storageCapacity = storageCapacity;
        this.maxOperationalDistance = maxOperationalDistance;
        this.maxConcurrentOperationalUsers = maxConcurrentOperationalUsers;
        this.refiningYield = refiningYield;
        this.operationDuration = operationDuration;
    }
      
    public double getStorageCapacity()              {return storageCapacity;}    
    public double getMaxOperationalDistance()       {return maxOperationalDistance;}    
    public double getMaxConcurrentOperationalUsers(){return maxConcurrentOperationalUsers;}    
    public double getRefiningYield()                {return refiningYield;}    
    public double getOperationDuration()            {return operationDuration;}       
}