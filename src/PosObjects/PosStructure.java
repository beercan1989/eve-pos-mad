package PosObjects;

/**
 * @author (James Bacon)
 * @version (Created 08/07/2008)
 */
public abstract class PosStructure extends Item
{
    private String name;    
    
    private boolean factionModule,
                    playerControlable; 
                    
    private int sensorStrengthRadar,
                sensorStrengthLadar,
                sensorStrengthMagetometric,
                sensorStrengthGravimetric;
                
    private double shieldCapacity,
                   shieldRechargeRate,
                   shieldEmResistance,
                   shieldExplosiveResistance,
                   shieldKineticResistance,
                   shieldThermalResistance,
                   armorHitpoints,
                   armorEmResitsance,
                   armorExplosiveResistance,
                   armorKineticResistance,
                   armorThermalResistance,
                   strutureHitpoints,
                   strutureEmResitsance,
                   strutureExplosiveResistance,
                   strutureKineticResistance,
                   strutureThermalResistance,
                   signatureRadius,
                   anchoringDelay,
                   unanchoringDelay,
                   onliningDelay;
    
    PosStructure(boolean factionModule, double shieldCapacity, double shieldRechargeRate,
                 double shieldEmResistance, double shieldExplosiveResistance, double shieldKineticResistance,
                 double shieldThermalResistance, double armorHitpoints, double armorEmResitsance, double armorExplosiveResistance,
                 double armorKineticResistance, double armorThermalResistance, double strutureHitpoints, double strutureEmResitsance,
                 double strutureExplosiveResistance, double strutureKineticResistance, double strutureThermalResistance, double signatureRadius,
                 int sensorStrengthRadar, int sensorStrengthLadar, int sensorStrengthMagetometric, int sensorStrengthGravimetric, double anchoringDelay,
                 double unanchoringDelay, double onliningDelay, int mass, double volume, boolean playerControlable, String type, String name,
                 String description, double basePrice)
    {
        super(mass, volume, type, description, basePrice);
        
        this.factionModule                  = factionModule;
        this.shieldCapacity                 = shieldCapacity;
        this.shieldRechargeRate             = shieldRechargeRate;
        this.shieldEmResistance             = shieldEmResistance;
        this.shieldExplosiveResistance      = shieldExplosiveResistance;
        this.shieldKineticResistance        = shieldKineticResistance;
        this.shieldThermalResistance        = shieldThermalResistance;
        this.armorHitpoints                 = armorHitpoints;
        this.armorEmResitsance              = armorEmResitsance;
        this.armorExplosiveResistance       = armorExplosiveResistance;
        this.armorKineticResistance         = armorKineticResistance;
        this.armorThermalResistance         = armorThermalResistance;
        this.strutureHitpoints              = strutureHitpoints;
        this.strutureEmResitsance           = strutureEmResitsance;
        this.strutureExplosiveResistance    = strutureExplosiveResistance;
        this.strutureKineticResistance      = strutureKineticResistance;
        this.strutureThermalResistance      = strutureThermalResistance;
        this.signatureRadius                = signatureRadius;
        this.sensorStrengthRadar            = sensorStrengthRadar;
        this.sensorStrengthLadar            = sensorStrengthLadar;
        this.sensorStrengthMagetometric     = sensorStrengthMagetometric;
        this.sensorStrengthGravimetric      = sensorStrengthGravimetric;
        this.anchoringDelay                 = anchoringDelay;
        this.unanchoringDelay               = unanchoringDelay;
        this.onliningDelay                  = onliningDelay;
        this.playerControlable              = playerControlable;
        this.name                           = name;
    }
    
    public boolean getFactionModule()              {return factionModule;}    
    public double getShieldCapacity()              {return shieldCapacity;}   
    public double getShieldRechargeRate()          {return shieldRechargeRate;}    
    public double getShieldEmResistance()          {return shieldEmResistance;}    
    public double getShieldExplosiveResistance()   {return shieldExplosiveResistance;}    
    public double getShieldKineticResistance()     {return shieldKineticResistance;}    
    public double getShieldThermalResistance()     {return shieldThermalResistance;}    
    public double getArmorHitpoints()              {return armorHitpoints;}    
    public double getArmorEmResistance()           {return armorEmResitsance;}    
    public double getArmorExplosiveResistance()    {return armorExplosiveResistance;}    
    public double getArmorKineticResistance()      {return armorKineticResistance;}    
    public double getArmorThermalResistance()      {return armorThermalResistance;}    
    public double getStructureHitpoints()          {return strutureHitpoints;}    
    public double getStructureEmResistance()       {return strutureEmResitsance;}    
    public double getStructureExplosiveResistance(){return strutureExplosiveResistance;}    
    public double getStructureKineticResistance()  {return strutureKineticResistance;}    
    public double getStructureThermalResistance()  {return strutureThermalResistance;}    
    public double getSignatureRadius()             {return signatureRadius;}    
    public int getSensorStrengthRadar()            {return sensorStrengthRadar;}    
    public int getSensorStrengthLadar()            {return sensorStrengthLadar;}    
    public int getSensorStrengthMagetometric()     {return sensorStrengthMagetometric;}    
    public int getSensorStrengthGravimetric()      {return sensorStrengthGravimetric;}    
    public double getAnchoringDelay()              {return anchoringDelay;}    
    public double getUnanchoringDelay()            {return unanchoringDelay;}    
    public double getOnliningDelay()               {return onliningDelay;}        
    public boolean getPlayerControlable()          {return playerControlable;}    
    public String getName()                        {return name;}
}