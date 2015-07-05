package PosObjects;

/**
 * @author (James Bacon)
 * @version (Created 08/07/2008)
 */
public class PosSystemScanner extends PosModule
{
    private double activationDuration,
                   scanRange,
                   minimumScanDeviation,
                   maximumScanDeviation,
                   sovereigntyLevelRequired,
                   maximumPerSolarSystem;

    public PosSystemScanner(double requiredPowerGrid, double requiredCPU, double activationDuration, double scanRange, 
                            double minimumScanDeviation, double maximumScanDeviation, double sovereigntyLevelRequired, 
                            double maximumPerSolarSystem, boolean factionModule, double shieldCapacity, double shieldRechargeRate, 
                            double shieldEmResistance, double shieldExplosiveResistance, double shieldKineticResistance, 
                            double shieldThermalResistance, double armorHitpoints, double armorEmResitsance, double armorExplosiveResistance, 
                            double armorKineticResistance, double armorThermalResistance, double strutureHitpoints, double strutureEmResitsance, 
                            double strutureExplosiveResistance, double strutureKineticResistance, double strutureThermalResistance, 
                            double signatureRadius, int sensorStrengthRadar, int sensorStrengthLadar, int sensorStrengthMagetometric, 
                            int sensorStrengthGravimetric, double anchoringDelay, double unanchoringDelay, double onliningDelay, int mass, 
                            double volume, boolean playerControlable, String name, String description, double basePrice)
    {
        super(factionModule, shieldCapacity, shieldRechargeRate, shieldEmResistance, shieldExplosiveResistance, 
              shieldKineticResistance, shieldThermalResistance, armorHitpoints, armorEmResitsance, armorExplosiveResistance, 
              armorKineticResistance, armorThermalResistance, strutureHitpoints, strutureEmResitsance, strutureExplosiveResistance, 
              strutureKineticResistance, strutureThermalResistance, signatureRadius, sensorStrengthRadar, sensorStrengthLadar, 
              sensorStrengthMagetometric, sensorStrengthGravimetric, anchoringDelay, unanchoringDelay, onliningDelay, mass, 
              volume, playerControlable, "SystemScanner", name, description, basePrice, requiredPowerGrid, requiredCPU);
        
        this.activationDuration = activationDuration;
        this.scanRange = scanRange;
        this.minimumScanDeviation = minimumScanDeviation;
        this.maximumScanDeviation = maximumScanDeviation;
        this.sovereigntyLevelRequired = sovereigntyLevelRequired;
        this.maximumPerSolarSystem = maximumPerSolarSystem;
    }
      
    public double getActivationDuration()          {return activationDuration;}        
    public double getScanRange()                   {return scanRange;}        
    public double getMinimumScanDeviation()        {return minimumScanDeviation;}        
    public double getMaximumScanDeviation()        {return maximumScanDeviation;}    
    public double getSovereigntyLevelRequired()    {return sovereigntyLevelRequired;}    
    public double getMaximumPerSolarSystem()       {return maximumPerSolarSystem;}    
}