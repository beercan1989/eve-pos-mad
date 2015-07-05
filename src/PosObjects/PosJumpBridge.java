package PosObjects;

/**
 * @author (James Bacon)
 * @version (Created 08/07/2008)
 */
public class PosJumpBridge extends PosModule
{
    private double jumpDriveConsumptionAmount,
                   sovereigntyLevelRequired,
                   maximumPerSolarSystem;
    
    public PosJumpBridge(double requiredPowerGrid, double requiredCPU, double jumpDriveConsumptionAmount, double sovereigntyLevelRequired, 
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
              volume, playerControlable, "JumpBridge", name, description, basePrice, requiredPowerGrid, requiredCPU);
        
        this.jumpDriveConsumptionAmount = jumpDriveConsumptionAmount;
        this.sovereigntyLevelRequired = sovereigntyLevelRequired;
        this.maximumPerSolarSystem = maximumPerSolarSystem;
    }

    public double getJumpDriveConsumptionAmount()   {return jumpDriveConsumptionAmount;}    
    public double getSovereigntyLevelRequired()     {return sovereigntyLevelRequired;}    
    public double getMaximumPerSolarSystem()        {return maximumPerSolarSystem;}    
}