package PosObjects;

/**
 * @author (James Bacon)
 * @version (Created 08/07/2008)
 */
public abstract class PosAmmo extends Item
{
    private boolean faction;
    
    private String chargeSize,
                   name;
                   
    private double emDamage,
                   explosiveDamage,
                   kineticDamage,
                   thermalDamage;
    
    public PosAmmo(String type, String chargeSize, boolean faction, String name, double emDamage, double explosiveDamage, double kineticDamage,
                   double thermalDamage, int mass, double volume, String description, double basePrice)
    {
        super(mass, volume, type, description, basePrice);
        this.chargeSize = chargeSize;
        this.faction = faction;
        this.name = name;
        this.emDamage = emDamage;
        this.explosiveDamage = explosiveDamage;
        this.kineticDamage = kineticDamage;
        this.thermalDamage = thermalDamage;
    }

    public String getChargeSize()      {return chargeSize;}    
    public boolean getFaction()        {return faction;}    
    public String getName()            {return name;}    
    public double getEmDamage()        {return emDamage;}    
    public double getExplosiveDamage() {return explosiveDamage;}    
    public double getKineticDamage()   {return kineticDamage;}    
    public double getThermalDamage()   {return thermalDamage;}    
}