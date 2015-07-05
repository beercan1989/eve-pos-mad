package PosObjects;

/**
 * @author (James Bacon)
 * @version (Created 08/07/2008)
 */
public class PosShieldHardner extends PosModule
{
    private double thermalResistanceBonus,
                   kineticResistanceBonus,
                   explosiveResistanceBonus,
                   emResistanceBonus;
    
    public PosShieldHardner(double requiredPowerGrid, double requiredCPU, double thermalResistanceBonus, 
                            double kineticResistanceBonus, double explosiveResistanceBonus, double emResistanceBonus, boolean factionModule, double shieldCapacity, 
                            double shieldRechargeRate, double shieldEmResistance, double shieldExplosiveResistance, double shieldKineticResistance, 
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
              volume, playerControlable, "ShieldHardner", name, description, basePrice, requiredPowerGrid, requiredCPU);
        
        this.thermalResistanceBonus = thermalResistanceBonus;
        this.kineticResistanceBonus = kineticResistanceBonus;
        this.explosiveResistanceBonus = explosiveResistanceBonus;
        this.emResistanceBonus = emResistanceBonus;
    }
  
    public double getThermalResistanceBonus()      {return thermalResistanceBonus;}    
    public double getKineticResistanceBonus()      {return kineticResistanceBonus;}    
    public double getExplosiveResistanceBonus()    {return explosiveResistanceBonus;}    
    public double getEmResistanceBonus()           {return emResistanceBonus;}        
}