package PosObjects;

import java.text.DecimalFormat;

/**
 * @author (James Bacon)
 * @version (Created 17/04/2011)
 */
public class PosModule extends PosStructure
{
    private DecimalFormat formatter = new DecimalFormat("###,###,###,###,###");
    private double requiredPowerGrid,
                   requiredCPU;

    PosModule(boolean factionModule, double shieldCapacity, double shieldRechargeRate,
                 double shieldEmResistance, double shieldExplosiveResistance, double shieldKineticResistance,
                 double shieldThermalResistance, double armorHitpoints, double armorEmResitsance, double armorExplosiveResistance,
                 double armorKineticResistance, double armorThermalResistance, double strutureHitpoints, double strutureEmResitsance,
                 double strutureExplosiveResistance, double strutureKineticResistance, double strutureThermalResistance, double signatureRadius,
                 int sensorStrengthRadar, int sensorStrengthLadar, int sensorStrengthMagetometric, int sensorStrengthGravimetric, double anchoringDelay,
                 double unanchoringDelay, double onliningDelay, int mass, double volume, boolean playerControlable, String type, String name,
                 String description, double basePrice, double requiredPowerGrid, double requiredCPU)
    {
        super(factionModule, shieldCapacity, shieldRechargeRate, shieldEmResistance, shieldExplosiveResistance,
              shieldKineticResistance, shieldThermalResistance, armorHitpoints, armorEmResitsance, armorExplosiveResistance,
              armorKineticResistance, armorThermalResistance, strutureHitpoints, strutureEmResitsance, strutureExplosiveResistance,
              strutureKineticResistance, strutureThermalResistance, signatureRadius, sensorStrengthRadar, sensorStrengthLadar,
              sensorStrengthMagetometric, sensorStrengthGravimetric, anchoringDelay, unanchoringDelay, onliningDelay, mass,
              volume, playerControlable, type, name, description, basePrice);

        this.requiredPowerGrid = requiredPowerGrid;
        this.requiredCPU = requiredCPU;
    }

    public double getRequiredPowerGrid() {return requiredPowerGrid;}
    public double getRequiredCPU()       {return requiredCPU;}

    private String requiredCPU()
    {
        return formatter.format(requiredCPU);
    }
    private String requiredPowerGrid()
    {
        return formatter.format(requiredPowerGrid);
    }


    @Override public String toString()   {return super.getName()+" - CPU: "+requiredCPU()+"tf - Power: "+requiredPowerGrid()+"mw";}
}
