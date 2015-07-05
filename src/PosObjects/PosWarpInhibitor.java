package PosObjects;

/**
 * @author (James Bacon)
 * @version (Created 08/07/2008)
 */
public class PosWarpInhibitor extends PosModule
{
    private double activationDuration,
                   activationProximity,
                   maxLockedTargets,
                   scanResolution,
                   warpScrambleRange,
                   warpScrambleStrength;
    
    public PosWarpInhibitor(double requiredPowerGrid, double requiredCPU, double activationDuration, double activationProximity, double maxLockedTargets,
                            double scanResolution, double warpScrambleRange, double warpScrambleStrength, boolean factionModule, double shieldCapacity, 
                            double shieldRechargeRate, double shieldEmResistance, double shieldExplosiveResistance, double shieldKineticResistance, 
                            double shieldThermalResistance, double armorHitpoints, double armorEmResitsance, double armorExplosiveResistance, 
                            double armorKineticResistance, double armorThermalResistance, double strutureHitpoints, double strutureEmResitsance, 
                            double strutureExplosiveResistance, double strutureKineticResistance, double strutureThermalResistance, double signatureRadius, 
                            int sensorStrengthRadar, int sensorStrengthLadar, int sensorStrengthMagetometric, int sensorStrengthGravimetric, 
                            double anchoringDelay, double unanchoringDelay, double onliningDelay, int mass, double volume, boolean playerControlable, 
                            String name, String description, double basePrice)
    {
        super(factionModule, shieldCapacity, shieldRechargeRate, shieldEmResistance, shieldExplosiveResistance, 
              shieldKineticResistance, shieldThermalResistance, armorHitpoints, armorEmResitsance, armorExplosiveResistance, 
              armorKineticResistance, armorThermalResistance, strutureHitpoints, strutureEmResitsance, strutureExplosiveResistance, 
              strutureKineticResistance, strutureThermalResistance, signatureRadius, sensorStrengthRadar, sensorStrengthLadar, 
              sensorStrengthMagetometric, sensorStrengthGravimetric, anchoringDelay, unanchoringDelay, onliningDelay, mass, 
              volume, playerControlable, "WarpInhibitor", name, description, basePrice, requiredPowerGrid, requiredCPU);
              
        this.activationDuration = activationDuration;
        this.activationProximity = activationProximity;
        this.maxLockedTargets = maxLockedTargets;
        this.scanResolution = scanResolution;
        this.warpScrambleRange = warpScrambleRange;
        this.warpScrambleStrength = warpScrambleStrength;
    }
  
    public double getActivationDuration()          {return activationDuration;}    
    public double getActivationProximity()         {return activationProximity;}    
    public double getMaxLockedTargets()            {return maxLockedTargets;}    
    public double getScanResolution()              {return scanResolution;}    
    public double getWarpScrambleRange()           {return warpScrambleRange;}    
    public double getWarpScrambleStrength()        {return warpScrambleStrength;}    
}