package PosObjects;
import java.util.ArrayList;
/**
 * @author (James Bacon)
 * @version (Created 08/07/2008)
 */
public class PosControlTower extends PosStructure
{
    private ArrayList<PosFuel>  fuelList;
    private ArrayList<PosBonus> towerBonus;
    private double powerGrid, cpu, maxStructureDistance, maxMoonAnchorDistance,
                   shieldRadius, normalFuelCapacity, strontiumFuelCapacity,
                   controlTowerPeriod, activationProximity;
    
    public PosControlTower(String name, double powerGrid, double cpu, double maxStructureDistance, 
                           double maxMoonAnchorDistance, double shieldRadius, double normalFuelCapacity, 
                           double strontiumFuelCapacity, ArrayList<PosBonus> towerBonus,
                           double controlTowerPeriod, ArrayList<PosFuel> fuelList,
                           double activationProximity, boolean factionModule, double shieldCapacity,
                           double shieldRechargeRate, double shieldEmResistance, double shieldExplosiveResistance, double shieldKineticResistance, 
                           double shieldThermalResistance, double armorHitpoints, double armorEmResitsance, double armorExplosiveResistance, 
                           double armorKineticResistance, double armorThermalResistance, double strutureHitpoints, double strutureEmResitsance, 
                           double strutureExplosiveResistance, double strutureKineticResistance, double strutureThermalResistance, 
                           double signatureRadius, int sensorStrengthRadar, int sensorStrengthLadar, int sensorStrengthMagetometric, 
                           int sensorStrengthGravimetric, double anchoringDelay, double unanchoringDelay, double onliningDelay, int mass, 
                           double volume, boolean playerControlable, String description, double basePrice)
    {
        super(factionModule, shieldCapacity, shieldRechargeRate,
              shieldEmResistance, shieldExplosiveResistance, shieldKineticResistance, shieldThermalResistance, armorHitpoints, 
              armorEmResitsance, armorExplosiveResistance, armorKineticResistance, armorThermalResistance, strutureHitpoints, 
              strutureEmResitsance, strutureExplosiveResistance, strutureKineticResistance, strutureThermalResistance, 
              signatureRadius, sensorStrengthRadar, sensorStrengthLadar, sensorStrengthMagetometric, sensorStrengthGravimetric, 
              anchoringDelay, unanchoringDelay, onliningDelay, mass, volume, playerControlable, "ControlTower", name,
              description, basePrice);
        
        this.powerGrid               = powerGrid;
        this.cpu                     = cpu;
        this.maxStructureDistance    = maxStructureDistance;
        this.maxMoonAnchorDistance   = maxMoonAnchorDistance;
        this.shieldRadius            = shieldRadius;
        this.normalFuelCapacity      = normalFuelCapacity;
        this.strontiumFuelCapacity   = strontiumFuelCapacity;
        this.towerBonus              = towerBonus;
        this.controlTowerPeriod      = controlTowerPeriod;
        this.activationProximity     = activationProximity;
        this.fuelList                = fuelList;
    }
       
    public double getPowerGrid()                {return powerGrid;}    
    public double getCpu()                      {return cpu;}   
    public double getMaxStructureDistance()     {return maxStructureDistance;}
    public double getMaxMoonAnchorDistance()    {return maxMoonAnchorDistance;}    
    public double getShieldRadius()             {return shieldRadius;}    
    public double getNormalFuelCapacity()       {return normalFuelCapacity;}    
    public double getStrontiumFuelCapacity()    {return strontiumFuelCapacity;}
    public double getControlTowerPeriod()       {return controlTowerPeriod;}    
    public double getActivationProximity()      {return activationProximity;}
    public ArrayList<PosBonus> getTowerBonus()  {return towerBonus;}
    public ArrayList<PosFuel> getFuelList()     {return fuelList;}
}