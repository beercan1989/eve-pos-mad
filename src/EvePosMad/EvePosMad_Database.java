package eveposmad;
import java.sql.*;
import java.util.ArrayList;
import com.beimin.eveapi.corporation.starbase.list.*;
import com.beimin.eveapi.corporation.starbase.detail.*;
import PosObjects.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Set;
import java.util.Date;
/**
 * @author James Bacon
 */
public class EvePosMad_Database{
    private String dbLocation, dbEve, dbDesign, dbMonitor;
    public static final int AMMO_CRYSTAL=1, AMMO_HYBRID=2, AMMO_MISSILE=3,
    AMMO_PROJECTILE=4, STRUCTURE_ASSEMBLY_ARRAY=5,STRUCTURE_CORP_STORAGE=6,
    STRUCTURE_CYNO_TOOL=7, STRUCTURE_ENERGY_NEUT=8, STRUCTURE_JAMMER=9,
    STRUCTURE_JUMP_BRIDGE=10, STRUCTURE_MISSILE_BATTERY=11, STRUCTURE_MOBILE_LAB=12,
    STRUCTURE_MOON_MINER=13, STRUCTURE_REACTOR=14, STRUCTURE_REFINER=15,
    STRUCTURE_SENSOR_DAMP=16, STRUCTURE_SHIELD_HARDNER=17, STRUCTURE_SILO=18,
    STRUCTURE_STASIS_WEB=19, STRUCTURE_SCANNER=20, STRUCTURE_TURRET_BATTERY=21,
    STRUCTURE_WARP_INHIBITOR=22;

    public EvePosMad_Database(){
        dbLocation="src/eveposmad/resources/database/";
        //dbLocation="";
        dbEve="incursion101.sqlite3";
        dbDesign="design.sqlite3";
        dbMonitor="monitor.sqlite3";
    }

    public PosControlTower getControlTower(String controlTowerName){
        PosControlTower controlTower = null;
        ArrayList<PosFuel>  fuelList   = new ArrayList<PosFuel>();
        ArrayList<PosBonus> towerBouns = new ArrayList<PosBonus>();
        boolean factionModule=false, playerControlable=false;
        String  name="", description="";

        int     sensorStrengthRadar=0, sensorStrengthLadar=0, mass=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;

        double  powerGrid=0, cpu=0, maxStructureDistance=0, maxMoonAnchorDistance=0,
                shieldRadius=0, normalFuelCapacity=0, strontiumFuelCapacity=0,
                controlTowerPeriod=0, activationProximity=0, shieldCapacity=0,
                shieldRechargeRate=0, shieldEmResistance=0, shieldExplosiveResistance=0,
                shieldKineticResistance=0, shieldThermalResistance=0, armorHitpoints=0,
                armorEmResitsance=0, armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0, strutureEmResitsance=0,
                strutureExplosiveResistance=0, strutureKineticResistance=0,
                strutureThermalResistance=0, signatureRadius=0, anchoringDelay=0,
                unanchoringDelay=0, onliningDelay=0, volume=0, basePrice=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(controlTowerName));

            while (sqlResult.next())
            {
                try
                {
                    typeID = sqlResult.getString("typeID");
                    name = sqlResult.getString("typeName");
                    mass = sqlResult.getInt("mass");
                    volume = sqlResult.getDouble("volume");
                    normalFuelCapacity = sqlResult.getDouble("capacity");
                    description = sqlResult.getString("description");
                    basePrice = sqlResult.getDouble("basePrice");
                    switch (sqlResult.getInt("typeID"))
                    {
                        case 12235:
                        case 20059:
                        case 20060:
                        case 16213:
                        case 20061:
                        case 20062:
                        case 12236:
                        case 20063:
                        case 20064:
                        case 16214:
                        case 20065:
                        case 20066: break;
                        default:    factionModule=true;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 11:  powerGrid = currentSRV; break;
                        case 48:  cpu = currentSRV; break;
                        case 154: activationProximity = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 271: shieldEmResistance = currentSRV; break;
                        case 272: shieldExplosiveResistance = currentSRV; break;
                        case 273: shieldKineticResistance = currentSRV; break;
                        case 274: shieldThermalResistance = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 650: maxStructureDistance = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                        case 680: shieldRadius = currentSRV; break;
                        case 711: maxMoonAnchorDistance = currentSRV; break;
                        case 722: controlTowerPeriod = currentSRV; break;
                        case 728:
                        case 750:
                        case 751:
                        case 752:
                        case 753:
                        case 754:
                        case 755:
                        case 756:
                        case 757:
                        case 760:
                        case 761:
                        case 762:
                        case 764:
                        case 766:
                        case 770:
                        case 792:  towerBouns.add
                                   (
                                        new PosBonus
                                        (
                                            sqlResult.getString("displayName"),
                                            currentSRV
                                        )
                                   );
                                   break;
                        case 974:  strutureEmResitsance = currentSRV; break;
                        case 975:  strutureExplosiveResistance = currentSRV; break;
                        case 976:  strutureKineticResistance = currentSRV; break;
                        case 977:  strutureThermalResistance = currentSRV; break;
                        case 1233: strontiumFuelCapacity = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();

            //#### Get Tower Fuel

            sqlResult = sqlStatement.executeQuery
            (
                "SELECT typeID, typeName, quantity, basePrice, volume, mass, "+
                "description FROM invcontroltowerresources INNER JOIN invtypes "+
                "ON invcontroltowerresources.resourceTypeID=invtypes.typeID "+
                "WHERE controlTowerTypeID = \""+typeID+"\";"
            );
            while (sqlResult.next())
            {
                try
                {
                    String fuelName, fuelDescription;
                    int fuelMass;
                    double fuelVolume, fuelBasePrice, fuelAmount;
                    switch (sqlResult.getInt("typeID"))
                    {
                        case 24592:
                        case 24593:
                        case 24594:
                        case 24595:
                        case 24596:
                        case 24597: break;
                        default:
                        {
                            fuelName = sqlResult.getString("typeName");
                            fuelMass = sqlResult.getInt("mass");
                            fuelVolume = sqlResult.getDouble("volume");
                            fuelDescription = sqlResult.getString("description");
                            fuelBasePrice = sqlResult.getDouble("basePrice");
                            fuelAmount = sqlResult.getDouble("quantity");
                            fuelList.add(new PosFuel
                            (
                                fuelName,fuelMass,fuelVolume,fuelDescription,
                                fuelBasePrice,fuelAmount
                            ));
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            // Manually Add In Starbase Charter
            fuelList.add(new PosFuel("Starbase Charter",1,0.1,"An electronic "+
                    "charter code issued by an Empire which permits the bearer "+
                    "to use a starbase around a moon in that Empire's sovereign "+
                    "space for 1 hour. The code is stored on tamperproof chips "+
                    "which must be inserted into the starbase control tower.",
                    50000,1));

            sqlResult.close();

            database.close();
            controlTower = new PosControlTower
            (
                name, powerGrid, cpu, maxStructureDistance, maxMoonAnchorDistance,
                shieldRadius, normalFuelCapacity, strontiumFuelCapacity, towerBouns,
                controlTowerPeriod, fuelList, activationProximity, factionModule,
                shieldCapacity, shieldRechargeRate, shieldEmResistance,
                shieldExplosiveResistance, shieldKineticResistance, shieldThermalResistance,
                armorHitpoints, armorEmResitsance, armorExplosiveResistance,
                armorKineticResistance, armorThermalResistance, strutureHitpoints,
                strutureEmResitsance, strutureExplosiveResistance, strutureKineticResistance,
                strutureThermalResistance, signatureRadius, sensorStrengthRadar,
                sensorStrengthLadar, sensorStrengthMagetometric, sensorStrengthGravimetric,
                anchoringDelay, unanchoringDelay, onliningDelay, mass, volume,
                playerControlable, description, basePrice
            );
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return controlTower;
    }

    public PosStructure getPosStructure(String structureName, int structureTpe){
        switch (structureTpe)
        {
            case 5:  return getAssemblyArray(structureName);
            case 6:  return getCorporationStorage(structureName);
            case 7:  return getCynosuralTool(structureName);
            case 8:  return getEnergyNeutralizer(structureName);
            case 9:  return getJammer(structureName);
            case 10: return getJumpBridge(structureName);
            case 11: return getMissileBattery(structureName);
            case 12: return getMobileLaboratory(structureName);
            case 13: return getMoonMiner(structureName);
            case 14: return getReactor(structureName);
            case 15: return getRefiner(structureName);
            case 16: return getSensorDampener(structureName);
            case 17: return getShieldHardner(structureName);
            case 18: return getSilo(structureName);
            case 19: return getStasisWebification(structureName);
            case 20: return getSystemScanner(structureName);
            case 21: return getTurretBattery(structureName);
            case 22: return getWarpInhibitor(structureName);
            default: System.out.println("HERE: 0"); return null;
        }
    }
    private PosAssemblyArray getAssemblyArray(String structureName){
        PosAssemblyArray assemblyArray;
        String  name="", description="";
        boolean factionModule=false, playerControlable=false,
                isSovereigntyRequired=false;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, storageCapacity=0,
                maxOperationalDistance=0, maxConcurrentOperationalUsers=0,
                manufacturingSlots=0, baseTimeModifier=0, baseMaterialModifier=0,
                shieldCapacity=0, shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
                storageCapacity = sqlResult.getDouble("capacity");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                        case 715: maxOperationalDistance = currentSRV; break;
                        case 716: maxConcurrentOperationalUsers = currentSRV; break;
                        case 1595: isSovereigntyRequired = true; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }

            for (String descriptionLine : description.toLowerCase().split("\n"))
            {
                int subSI = descriptionLine.indexOf("manufacturing slot");
                if(subSI>=0)
                {
                    manufacturingSlots=Double.parseDouble
                            (descriptionLine.split(" ")[0]);
                    continue;
                }

                subSI = descriptionLine.indexOf("base time");
                if(subSI>=0)
                {
                    baseTimeModifier=Double.parseDouble
                            (descriptionLine.split(" ")[3]);
                    continue;
                }

                subSI = descriptionLine.indexOf("base material");
                if(subSI>=0)
                {
                    baseMaterialModifier=Double.parseDouble
                            (descriptionLine.split(" ")[3]);
                    continue;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        assemblyArray = new PosAssemblyArray
            (
                requiredPowerGrid, requiredCPU, isSovereigntyRequired,
                storageCapacity, maxOperationalDistance,
                maxConcurrentOperationalUsers, manufacturingSlots,
                baseTimeModifier, baseMaterialModifier, factionModule,
                shieldCapacity, shieldRechargeRate, shieldEmResistance,
                shieldExplosiveResistance, shieldKineticResistance,
                shieldThermalResistance, armorHitpoints, armorEmResitsance,
                armorExplosiveResistance, armorKineticResistance,
                armorThermalResistance, strutureHitpoints, strutureEmResitsance,
                strutureExplosiveResistance, strutureKineticResistance,
                strutureThermalResistance, signatureRadius, sensorStrengthRadar,
                sensorStrengthLadar, sensorStrengthMagetometric,
                sensorStrengthGravimetric, anchoringDelay, unanchoringDelay,
                onliningDelay, mass, volume, playerControlable, name,
                description, basePrice
            );
        return assemblyArray;
    }
    private PosCorporationStorage getCorporationStorage(String structureName){
        PosCorporationStorage corporationStorage;
        String  name="", description="";
        boolean factionModule=false, playerControlable=false,
                isSovereigntyRequired=false;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, storageCapacity=0,
                maxOperationalDistance=0, maxConcurrentOperationalUsers=0,
                shieldCapacity=0, shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
                storageCapacity = sqlResult.getDouble("capacity");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                        case 715: maxOperationalDistance = currentSRV; break;
                        case 716: maxConcurrentOperationalUsers = currentSRV; break;
                        case 1595: isSovereigntyRequired = true; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        corporationStorage = new PosCorporationStorage
            (
                requiredPowerGrid, requiredCPU, storageCapacity, isSovereigntyRequired,
                maxOperationalDistance, maxConcurrentOperationalUsers, factionModule,
                shieldCapacity, shieldRechargeRate, shieldEmResistance, shieldExplosiveResistance,
                shieldKineticResistance, shieldThermalResistance, armorHitpoints, armorEmResitsance,
                armorExplosiveResistance, armorKineticResistance, armorThermalResistance,
                strutureHitpoints, strutureEmResitsance, strutureExplosiveResistance,
                strutureKineticResistance, strutureThermalResistance, signatureRadius, sensorStrengthRadar,
                sensorStrengthLadar, sensorStrengthMagetometric, sensorStrengthGravimetric, anchoringDelay,
                unanchoringDelay, onliningDelay, mass, volume, playerControlable,
                name, description, basePrice
            );
        return corporationStorage;
    }
    private PosCynosuralTool getCynosuralTool(String structureName){
        PosCynosuralTool cynosuralTool;
        String  name="", description="";
        boolean factionModule=false, playerControlable=false;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, shieldCapacity=0,
                shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0, activationDuration=0,
                sovereigntyLevelRequired=0, maximumPerSolarSystem=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 73:  activationDuration = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                        case 1195:maximumPerSolarSystem = currentSRV; break;
                        case 1595:sovereigntyLevelRequired = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        cynosuralTool = new PosCynosuralTool
            (
                requiredPowerGrid, requiredCPU, activationDuration, sovereigntyLevelRequired,
                maximumPerSolarSystem, factionModule, shieldCapacity, shieldRechargeRate,
                shieldEmResistance, shieldExplosiveResistance, shieldKineticResistance,
                shieldThermalResistance, armorHitpoints, armorEmResitsance, armorExplosiveResistance,
                armorKineticResistance, armorThermalResistance, strutureHitpoints, strutureEmResitsance,
                strutureExplosiveResistance, strutureKineticResistance, strutureThermalResistance,
                signatureRadius, sensorStrengthRadar, sensorStrengthLadar,  sensorStrengthMagetometric,
                sensorStrengthGravimetric, anchoringDelay, unanchoringDelay, onliningDelay, mass,
                volume, playerControlable, name, description, basePrice
            );
        return cynosuralTool;
    }
    private PosEnergyNeutralizer getEnergyNeutralizer(String structureName){
        PosEnergyNeutralizer energyNeutralizer;
        String  name="", description="";
        boolean factionModule=false, playerControlable=true;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, shieldCapacity=0,
                shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0, activationProximity=0,
                maxLockedTargets=0, scanResolution=0, activationDuration=0, maxRange=0,
                energyNeutralized=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 73:  activationDuration = currentSRV; break;
                        case 97:  energyNeutralized = currentSRV; break;
                        case 98:  maxRange = currentSRV; break;
                        case 154: activationProximity = currentSRV; break;
                        case 192: maxLockedTargets = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 564: scanResolution = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        energyNeutralizer = new PosEnergyNeutralizer
            (
                requiredPowerGrid, requiredCPU, activationProximity, maxLockedTargets,
                scanResolution, activationDuration, maxRange, energyNeutralized,
                factionModule, shieldCapacity, shieldRechargeRate,
                shieldEmResistance, shieldExplosiveResistance, shieldKineticResistance,
                shieldThermalResistance, armorHitpoints, armorEmResitsance,
                armorExplosiveResistance, armorKineticResistance, armorThermalResistance,
                strutureHitpoints, strutureEmResitsance, strutureExplosiveResistance,
                strutureKineticResistance, strutureThermalResistance, signatureRadius,
                sensorStrengthRadar, sensorStrengthLadar, sensorStrengthMagetometric,
                sensorStrengthGravimetric, anchoringDelay, unanchoringDelay,
                onliningDelay, mass, volume, playerControlable, name,
                description, basePrice
            );
        return energyNeutralizer;
    }
    private PosJammer getJammer(String structureName){
        PosJammer jammer;
        String  name="", description="";
        boolean factionModule=false, playerControlable=true;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, shieldCapacity=0,
                shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0, activationProximity=0,
                maxLockedTargets=0, scanResolution=0, activationDuration=0,
                optimumRange=0, accuracyFalloff=0, gravimetricStrength=0, ladarStrength=0,
                radarStrength=0, magetometricStrength=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 54:  optimumRange = currentSRV; break;
                        case 73:  activationDuration = currentSRV; break;
                        case 154: activationProximity = currentSRV; break;
                        case 158: accuracyFalloff = currentSRV; break;
                        case 192: maxLockedTargets = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 238: gravimetricStrength = currentSRV; break;
                        case 239: ladarStrength = currentSRV; break;
                        case 240: magetometricStrength = currentSRV; break;
                        case 241: radarStrength = currentSRV; break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 564: scanResolution = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        jammer = new PosJammer
            (
                requiredPowerGrid, requiredCPU, activationDuration, activationProximity, maxLockedTargets,
                scanResolution, optimumRange, accuracyFalloff, gravimetricStrength, ladarStrength,
                radarStrength, magetometricStrength, factionModule, shieldCapacity, shieldRechargeRate,
                shieldEmResistance, shieldExplosiveResistance, shieldKineticResistance, shieldThermalResistance,
                armorHitpoints, armorEmResitsance, armorExplosiveResistance, armorKineticResistance,
                armorThermalResistance, strutureHitpoints, strutureEmResitsance, strutureExplosiveResistance,
                strutureKineticResistance, strutureThermalResistance, signatureRadius, sensorStrengthRadar,
                sensorStrengthLadar, sensorStrengthMagetometric, sensorStrengthGravimetric, anchoringDelay,
                unanchoringDelay, onliningDelay, mass, volume, playerControlable, name,
                description, basePrice
            );
        return jammer;
    }
    private PosJumpBridge getJumpBridge(String structureName){
        PosJumpBridge jumpBridge;
        String  name="", description="";
        boolean factionModule=false, playerControlable=false;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, shieldCapacity=0,
                shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0, jumpDriveConsumptionAmount=0,
                sovereigntyLevelRequired=0, maximumPerSolarSystem=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                        case 868: jumpDriveConsumptionAmount = currentSRV; break;
                        case 1195:maximumPerSolarSystem = currentSRV; break;
                        case 1595:sovereigntyLevelRequired = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        jumpBridge = new PosJumpBridge
            (
                requiredPowerGrid, requiredCPU, jumpDriveConsumptionAmount, sovereigntyLevelRequired,
                maximumPerSolarSystem, factionModule, shieldCapacity, shieldRechargeRate,
                shieldEmResistance, shieldExplosiveResistance, shieldKineticResistance,
                shieldThermalResistance, armorHitpoints, armorEmResitsance, armorExplosiveResistance,
                armorKineticResistance, armorThermalResistance, strutureHitpoints, strutureEmResitsance,
                strutureExplosiveResistance, strutureKineticResistance, strutureThermalResistance,
                signatureRadius, sensorStrengthRadar, sensorStrengthLadar, sensorStrengthMagetometric,
                sensorStrengthGravimetric, anchoringDelay, unanchoringDelay,  onliningDelay, mass,
                volume, playerControlable, name, description, basePrice
            );
        return jumpBridge;
    }
    private PosMissile getMissileBattery(String structureName){
        PosMissile missile;
        String  name="", description="";
        boolean factionModule=false, playerControlable=true;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, shieldCapacity=0,
                shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0, activationProximity=0,
                maxLockedTargets=0, scanResolution=0, rateOfFire=0, damageBonus=0,
                velocityBonus=0, flightTimeBonus=0, explosionVelocityBonus=0,
                explosionRadiusBonus=0, capacity=0, chargeType=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 154: activationProximity = currentSRV; break;
                        case 192: maxLockedTargets = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 212: damageBonus = currentSRV; break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 506: rateOfFire = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 564: scanResolution = currentSRV; break;
                        case 604: chargeType = currentSRV; break;
                        case 645: velocityBonus = currentSRV; break;
                        case 646: flightTimeBonus = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                        case 771: capacity = currentSRV; break;
                        case 858: explosionRadiusBonus = currentSRV; break;
                        case 859: explosionVelocityBonus = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        missile = new PosMissile
            (
                requiredPowerGrid, requiredCPU, activationProximity, maxLockedTargets,
                scanResolution, rateOfFire, damageBonus, velocityBonus, flightTimeBonus,
                explosionVelocityBonus, explosionRadiusBonus, capacity, chargeType,
                factionModule, shieldCapacity, shieldRechargeRate, shieldEmResistance, shieldExplosiveResistance,
                shieldKineticResistance, shieldThermalResistance, armorHitpoints, armorEmResitsance,
                armorExplosiveResistance, armorKineticResistance, armorThermalResistance, strutureHitpoints,
                strutureEmResitsance, strutureExplosiveResistance, strutureKineticResistance, strutureThermalResistance,
                signatureRadius, sensorStrengthRadar, sensorStrengthLadar, sensorStrengthMagetometric, sensorStrengthGravimetric,
                anchoringDelay, unanchoringDelay, onliningDelay, mass, volume, playerControlable, name,
                description, basePrice
            );
        return missile;
    }
    private PosMobileLaboratory getMobileLaboratory(String structureName){
        PosMobileLaboratory mobileLaboratory;
        String  name="", description="";
        boolean factionModule=false, playerControlable=false;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, shieldCapacity=0,
                shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0, storageCapacity=0,
                maxOperationalDistance=0, maxConcurrentOperationalUsers=0, copySlots=0,
                materialEfficiencySlots=0, productionEfficiencySlots=0, inventionSlots=0;
                //Notes: Lab slot values are not populated, due to not been in data dump.
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
                storageCapacity = sqlResult.getDouble("capacity");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                        case 715: maxOperationalDistance = currentSRV; break;
                        case 716: maxConcurrentOperationalUsers = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        mobileLaboratory = new PosMobileLaboratory
            (
                requiredPowerGrid, requiredCPU, storageCapacity, maxOperationalDistance,
                maxConcurrentOperationalUsers, copySlots, materialEfficiencySlots, productionEfficiencySlots,
                inventionSlots, factionModule, shieldCapacity, shieldRechargeRate, shieldEmResistance,
                shieldExplosiveResistance, shieldKineticResistance, shieldThermalResistance, armorHitpoints,
                armorEmResitsance, armorExplosiveResistance, armorKineticResistance, armorThermalResistance,
                strutureHitpoints, strutureEmResitsance, strutureExplosiveResistance, strutureKineticResistance,
                strutureThermalResistance, signatureRadius, sensorStrengthRadar, sensorStrengthLadar,
                sensorStrengthMagetometric, sensorStrengthGravimetric, anchoringDelay, unanchoringDelay,
                onliningDelay, mass, volume, playerControlable, name, description, basePrice
            );
        return mobileLaboratory;
    }
    private PosMoonMiner getMoonMiner(String structureName){
        PosMoonMiner moonMiner;
        String  name="", description="";
        boolean factionModule=false, playerControlable=false;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, shieldCapacity=0,
                shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0, storageCapacity=0,
                harvesterQuality=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
                storageCapacity = sqlResult.getDouble("capacity");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                        case 710: harvesterQuality = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        moonMiner = new PosMoonMiner
            (
                requiredPowerGrid, requiredCPU, storageCapacity, harvesterQuality, factionModule,
                shieldCapacity, shieldRechargeRate, shieldEmResistance, shieldExplosiveResistance,
                shieldKineticResistance, shieldThermalResistance, armorHitpoints, armorEmResitsance,
                armorExplosiveResistance, armorKineticResistance, armorThermalResistance, strutureHitpoints,
                strutureEmResitsance, strutureExplosiveResistance, strutureKineticResistance,
                strutureThermalResistance, signatureRadius, sensorStrengthRadar, sensorStrengthLadar,
                sensorStrengthMagetometric,sensorStrengthGravimetric, anchoringDelay, unanchoringDelay,
                onliningDelay, mass, volume, playerControlable, name, description,
                basePrice
            );
        return moonMiner;
    }
    private PosReactor getReactor(String structureName){
        PosReactor reactor;
        String  name="", description="", reactionTypeOne="", reactionTypeTwo="";
        boolean factionModule=false, playerControlable=false;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, shieldCapacity=0,
                shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                        case 842: reactionTypeOne = getGroupName(currentSRV.intValue());
                                  break;
                        case 843: reactionTypeTwo = getGroupName(currentSRV.intValue());
                                  break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        reactor = new PosReactor
            (
                requiredPowerGrid, requiredCPU, reactionTypeOne, reactionTypeTwo, factionModule,
                shieldCapacity, shieldRechargeRate, shieldEmResistance, shieldExplosiveResistance,
                shieldKineticResistance, shieldThermalResistance, armorHitpoints, armorEmResitsance,
                armorExplosiveResistance, armorKineticResistance, armorThermalResistance, strutureHitpoints,
                strutureEmResitsance, strutureExplosiveResistance, strutureKineticResistance,
                strutureThermalResistance, signatureRadius, sensorStrengthRadar, sensorStrengthLadar,
                sensorStrengthMagetometric, sensorStrengthGravimetric, anchoringDelay, unanchoringDelay,
                onliningDelay, mass, volume, playerControlable, name, description, basePrice
            );
        return reactor;
    }
    private PosRefiner getRefiner(String structureName){
        PosRefiner refiner;
        String  name="", description="";
        boolean factionModule=false, playerControlable=false;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, shieldCapacity=0,
                shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0, storageCapacity=0,
                maxOperationalDistance=0, maxConcurrentOperationalUsers=1,
                refiningYield=0, operationDuration=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
                storageCapacity = sqlResult.getDouble("capacity");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                        case 715: maxOperationalDistance = currentSRV; break;
                        case 717: refiningYield = currentSRV; break;
                        case 719: operationDuration = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        refiner = new PosRefiner
            (
                requiredPowerGrid, requiredCPU, storageCapacity, maxOperationalDistance,
                maxConcurrentOperationalUsers, refiningYield, operationDuration, factionModule,
                shieldCapacity, shieldRechargeRate, shieldEmResistance, shieldExplosiveResistance,
                shieldKineticResistance, shieldThermalResistance, armorHitpoints, armorEmResitsance,
                armorExplosiveResistance, armorKineticResistance, armorThermalResistance,
                strutureHitpoints, strutureEmResitsance, strutureExplosiveResistance,
                strutureKineticResistance, strutureThermalResistance, signatureRadius, sensorStrengthRadar,
                sensorStrengthLadar, sensorStrengthMagetometric, sensorStrengthGravimetric, anchoringDelay,
                unanchoringDelay, onliningDelay, mass, volume, playerControlable,
                name, description, basePrice
            );
        return refiner;
    }
    private PosSensorDampener getSensorDampener(String structureName){
        PosSensorDampener sensorDampener;
        String  name="", description="";
        boolean factionModule=false, playerControlable=true;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, shieldCapacity=0,
                shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0, activationDuration=0,
                activationProximity=0, maxLockedTargets=0, scanResolution=0, optimumRange=0,
                accuracyFalloff=0, targetsScanResolutionPenalty=0, targetsTargetingPenalty=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 54:  optimumRange = currentSRV; break;
                        case 73:  activationDuration = currentSRV; break;
                        case 154: activationProximity = currentSRV; break;
                        case 158: accuracyFalloff = currentSRV; break;
                        case 192: maxLockedTargets = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 237: targetsTargetingPenalty = currentSRV; break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 564: scanResolution = currentSRV; break;
                        case 565: targetsScanResolutionPenalty = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        sensorDampener = new PosSensorDampener
            (
                requiredPowerGrid, requiredCPU, activationDuration, activationProximity, maxLockedTargets,
                scanResolution, optimumRange, accuracyFalloff, targetsScanResolutionPenalty, targetsTargetingPenalty,
                factionModule, shieldCapacity, shieldRechargeRate, shieldEmResistance, shieldExplosiveResistance,
                shieldKineticResistance, shieldThermalResistance, armorHitpoints, armorEmResitsance,
                armorExplosiveResistance, armorKineticResistance, armorThermalResistance, strutureHitpoints,
                strutureEmResitsance, strutureExplosiveResistance, strutureKineticResistance, strutureThermalResistance,
                signatureRadius, sensorStrengthRadar, sensorStrengthLadar, sensorStrengthMagetometric, sensorStrengthGravimetric,
                anchoringDelay, unanchoringDelay, onliningDelay, mass, volume, playerControlable,
                name, description, basePrice
            );
        return sensorDampener;
    }
    private PosShieldHardner getShieldHardner(String structureName){
        PosShieldHardner shieldHardner;
        String  name="", description="";
        boolean factionModule=false, playerControlable=false;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, shieldCapacity=0,
                shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0, thermalResistanceBonus=0,
                kineticResistanceBonus=0, explosiveResistanceBonus=0, emResistanceBonus=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 130: thermalResistanceBonus = currentSRV; break;
                        case 131: kineticResistanceBonus = currentSRV; break;
                        case 132: explosiveResistanceBonus = currentSRV; break;
                        case 133: emResistanceBonus = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        shieldHardner = new PosShieldHardner
            (
                requiredPowerGrid, requiredCPU, thermalResistanceBonus,
                kineticResistanceBonus, explosiveResistanceBonus, emResistanceBonus, factionModule, shieldCapacity,
                shieldRechargeRate, shieldEmResistance, shieldExplosiveResistance, shieldKineticResistance,
                shieldThermalResistance, armorHitpoints, armorEmResitsance, armorExplosiveResistance,
                armorKineticResistance, armorThermalResistance, strutureHitpoints, strutureEmResitsance,
                strutureExplosiveResistance, strutureKineticResistance, strutureThermalResistance,
                signatureRadius, sensorStrengthRadar, sensorStrengthLadar, sensorStrengthMagetometric,
                sensorStrengthGravimetric, anchoringDelay, unanchoringDelay, onliningDelay, mass,
                volume, playerControlable, name, description, basePrice
            );
        return shieldHardner;
    }
    private PosSilo getSilo(String structureName){
        PosSilo silo;
        String  name="", description="";
        boolean factionModule=false, playerControlable=false;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, shieldCapacity=0,
                shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0, storageCapacity=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
                storageCapacity = sqlResult.getDouble("capacity");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        silo = new PosSilo
            (
                requiredPowerGrid, requiredCPU, storageCapacity, factionModule, shieldCapacity,
                shieldRechargeRate, shieldEmResistance, shieldExplosiveResistance, shieldKineticResistance,
                shieldThermalResistance, armorHitpoints, armorEmResitsance, armorExplosiveResistance,
                armorKineticResistance, armorThermalResistance, strutureHitpoints, strutureEmResitsance,
                strutureExplosiveResistance, strutureKineticResistance, strutureThermalResistance,
                signatureRadius, sensorStrengthRadar, sensorStrengthLadar, sensorStrengthMagetometric,
                sensorStrengthGravimetric, anchoringDelay, unanchoringDelay, onliningDelay, mass,
                volume, playerControlable, name, description, basePrice
            );
        return silo;
    }
    private PosStasisWebification getStasisWebification(String structureName){
        PosStasisWebification stasisWebification;
        String  name="", description="";
        boolean factionModule=false, playerControlable=true;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, shieldCapacity=0,
                shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0, activationDuration=0,
                activationProximity=0, maxLockedTargets=0, scanResolution=0,
                optimumWebificationRange=0, webificationStrength=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 20:  webificationStrength = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 54:  optimumWebificationRange = currentSRV; break;
                        case 73:  activationDuration = currentSRV; break;
                        case 154: activationProximity = currentSRV; break;
                        case 192: maxLockedTargets = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 564: scanResolution = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        stasisWebification = new PosStasisWebification
            (
                requiredPowerGrid, requiredCPU, activationDuration, activationProximity, maxLockedTargets,
                scanResolution, optimumWebificationRange, webificationStrength, factionModule,
                shieldCapacity, shieldRechargeRate, shieldEmResistance, shieldExplosiveResistance,
                shieldKineticResistance,  shieldThermalResistance, armorHitpoints, armorEmResitsance,
                armorExplosiveResistance, armorKineticResistance, armorThermalResistance, strutureHitpoints,
                strutureEmResitsance, strutureExplosiveResistance, strutureKineticResistance,
                strutureThermalResistance, signatureRadius, sensorStrengthRadar, sensorStrengthLadar,
                sensorStrengthMagetometric, sensorStrengthGravimetric, anchoringDelay, unanchoringDelay,
                onliningDelay, mass, volume, playerControlable, name, description, basePrice
            );
        return stasisWebification;
    }
    private PosSystemScanner getSystemScanner(String structureName){
        PosSystemScanner systemScanner;
        String  name="", description="";
        boolean factionModule=false, playerControlable=false;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, shieldCapacity=0,
                shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0, activationDuration=0,
                sovereigntyLevelRequired=0, maximumPerSolarSystem=0, scanRange=0,
                minimumScanDeviation=0, maximumScanDeviation=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 73:  activationDuration = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                        case 765: scanRange = currentSRV; break;
                        case 787: minimumScanDeviation = currentSRV; break;
                        case 788: maximumScanDeviation = currentSRV; break;
                        case 1195:maximumPerSolarSystem = currentSRV; break;
                        case 1595:sovereigntyLevelRequired = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        systemScanner = new PosSystemScanner
            (
                requiredPowerGrid, requiredCPU, activationDuration, scanRange,
                minimumScanDeviation, maximumScanDeviation, sovereigntyLevelRequired,
                maximumPerSolarSystem, factionModule, shieldCapacity, shieldRechargeRate,
                shieldEmResistance, shieldExplosiveResistance, shieldKineticResistance,
                shieldThermalResistance, armorHitpoints, armorEmResitsance, armorExplosiveResistance,
                armorKineticResistance, armorThermalResistance, strutureHitpoints, strutureEmResitsance,
                strutureExplosiveResistance, strutureKineticResistance, strutureThermalResistance,
                signatureRadius, sensorStrengthRadar, sensorStrengthLadar, sensorStrengthMagetometric,
                sensorStrengthGravimetric, anchoringDelay, unanchoringDelay, onliningDelay, mass,
                volume, playerControlable, name, description, basePrice
            );
        return systemScanner;
    }
    private PosTurret getTurretBattery(String structureName){
        PosTurret turret;
        String  name="", description="";
        boolean factionModule=false, playerControlable=true;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, shieldCapacity=0,
                shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0, activationProximity=0,
                maxLockedTargets=0, scanResolution=0, optimumRange=0, accuracyFalloff=0,
                damageModifier=0, trackingSpeed=0, signatureResolution=0, capacity=0,
                chargeSize=0, rateOfFire=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 51:  rateOfFire = currentSRV; break;
                        case 54:  optimumRange = currentSRV; break;
                        case 64:  damageModifier = currentSRV; break;
                        case 128: chargeSize = currentSRV; break;
                        case 154: activationProximity = currentSRV; break;
                        case 158: accuracyFalloff = currentSRV; break;
                        case 160: trackingSpeed = currentSRV; break;
                        case 192: maxLockedTargets = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 564: scanResolution = currentSRV; break;
                        case 620: signatureResolution = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                        case 771: capacity = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        turret = new PosTurret
            (
                requiredPowerGrid, requiredCPU, activationProximity, maxLockedTargets, scanResolution, rateOfFire,
                optimumRange, accuracyFalloff, damageModifier, trackingSpeed, signatureResolution, capacity,
                chargeSize, factionModule, shieldCapacity, shieldRechargeRate, shieldEmResistance, shieldExplosiveResistance,
                shieldKineticResistance, shieldThermalResistance, armorHitpoints, armorEmResitsance, armorExplosiveResistance,
                armorKineticResistance, armorThermalResistance, strutureHitpoints, strutureEmResitsance, strutureExplosiveResistance,
                strutureKineticResistance, strutureThermalResistance, signatureRadius, sensorStrengthRadar, sensorStrengthLadar,
                sensorStrengthMagetometric, sensorStrengthGravimetric, anchoringDelay, unanchoringDelay, onliningDelay, mass,
                volume, playerControlable, name, description, basePrice
            );
        return turret;
    }
    private PosWarpInhibitor getWarpInhibitor(String structureName){
        PosWarpInhibitor warpInhibitor;
        String  name="", description="";
        boolean factionModule=false, playerControlable=true;
        int     mass=0, sensorStrengthRadar=0, sensorStrengthLadar=0,
                sensorStrengthMagetometric=0, sensorStrengthGravimetric=0;
        double  requiredPowerGrid=0, requiredCPU=0, shieldCapacity=0,
                shieldRechargeRate=0, shieldEmResistance=0,
                shieldExplosiveResistance=0, shieldKineticResistance=0,
                shieldThermalResistance=0, armorHitpoints=0, armorEmResitsance=0,
                armorExplosiveResistance=0, armorKineticResistance=0,
                armorThermalResistance=0, strutureHitpoints=0,
                strutureEmResitsance=0, strutureExplosiveResistance=0,
                strutureKineticResistance=0, strutureThermalResistance=0,
                signatureRadius=0, anchoringDelay=0, unanchoringDelay=0,
                onliningDelay=0, volume=0, basePrice=0, activationDuration=0,
                activationProximity=0, maxLockedTargets=0, scanResolution=0,
                warpScrambleRange=0, warpScrambleStrength=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(structureName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 9:   strutureHitpoints = currentSRV; break;
                        case 30:  requiredPowerGrid = currentSRV; break;
                        case 50:  requiredCPU = currentSRV; break;
                        case 73:  activationDuration = currentSRV; break;
                        case 103: warpScrambleRange = currentSRV; break;
                        case 105: warpScrambleStrength = currentSRV; break;
                        case 154: activationProximity = currentSRV; break;
                        case 192: maxLockedTargets = currentSRV; break;
                        case 208: sensorStrengthRadar = currentSRV.intValue();
                                  break;
                        case 209: sensorStrengthLadar = currentSRV.intValue();
                                  break;
                        case 210: sensorStrengthMagetometric = currentSRV.intValue();
                                  break;
                        case 211: sensorStrengthGravimetric = currentSRV.intValue();
                                  break;
                        case 263: shieldCapacity = currentSRV; break;
                        case 265: armorHitpoints = currentSRV; break;
                        case 479: shieldRechargeRate = currentSRV; break;
                        case 552: signatureRadius = currentSRV; break;
                        case 556: anchoringDelay = currentSRV; break;
                        case 564: scanResolution = currentSRV; break;
                        case 676: unanchoringDelay = currentSRV; break;
                        case 677: onliningDelay = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        warpInhibitor = new PosWarpInhibitor
            (
                requiredPowerGrid, requiredCPU, activationDuration, activationProximity, maxLockedTargets,
                scanResolution, warpScrambleRange, warpScrambleStrength, factionModule, shieldCapacity,
                shieldRechargeRate, shieldEmResistance, shieldExplosiveResistance, shieldKineticResistance,
                shieldThermalResistance, armorHitpoints, armorEmResitsance, armorExplosiveResistance,
                armorKineticResistance, armorThermalResistance, strutureHitpoints, strutureEmResitsance,
                strutureExplosiveResistance, strutureKineticResistance, strutureThermalResistance, signatureRadius,
                sensorStrengthRadar, sensorStrengthLadar, sensorStrengthMagetometric, sensorStrengthGravimetric,
                anchoringDelay, unanchoringDelay, onliningDelay, mass, volume, playerControlable,
                name, description, basePrice
            );
        return warpInhibitor;
    }

    public PosAmmo getPosAmmo(String ammoName, int ammoType){
        switch (ammoType)
        {
            case 1: return getPosAmmoCrystal(ammoName);
            case 2: return getPosAmmoHybrid(ammoName);
            case 3: return getPosAmmoMissile(ammoName);
            case 4: return getPosAmmoProjectile(ammoName);
            default: return null;
        }
    }
    private PosAmmoCrystal getPosAmmoCrystal(String ammoName){
        PosAmmoCrystal crystalAmmo;
        double  rangeBonus=0,capacitorBonus=0,volatility=0,volatilityDamage=0,
                crystalDamage=0,emDamage=0,explosiveDamage=0,kineticDamage=0,
                thermalDamage=0,volume=0,basePrice=0;
        String  chargeSize="",description="",name="";
        boolean faction=false;
        int     mass=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                   "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(ammoName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
                if(name.toLowerCase().contains("imperial")){faction=true;}
                if(name.toLowerCase().contains("blood"))   {faction=true;}
                if(name.toLowerCase().contains("sanshas")) {faction=true;}
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 114: emDamage = currentSRV; break;
                        case 116: explosiveDamage = currentSRV; break;
                        case 117: kineticDamage = currentSRV; break;
                        case 118: thermalDamage = currentSRV; break;
                        case 120: rangeBonus = currentSRV; break;
                        case 128: chargeSize = ""+sqlResult.getInt("valueInt");
                                  break;
                        case 317: capacitorBonus = currentSRV; break;
                        case 783: volatility = currentSRV; break;
                        case 784: volatilityDamage = currentSRV; break;
                        case 786: crystalDamage = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        crystalAmmo = new PosAmmoCrystal
            (
                rangeBonus, capacitorBonus, volatility, volatilityDamage,
                crystalDamage, chargeSize, faction, name, emDamage,
                explosiveDamage, kineticDamage, thermalDamage, mass,
                volume, description, basePrice
            );
        return crystalAmmo;
    }
    private PosAmmoHybrid getPosAmmoHybrid(String ammoName){
        PosAmmoHybrid hybridAmmo;
        double  rangeBonus=0, capacitorBonus=0, emDamage=0, explosiveDamage=0,
                kineticDamage=0, thermalDamage=0, volume=0, basePrice=0;
        String  chargeSize="", name="", description="";
        boolean faction=false;
        int     mass=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(ammoName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
                if(name.contains("Caldari")){faction=true;}
                if(name.contains("Federation"))   {faction=true;}
                if(name.contains("Guristas")) {faction=true;}
                if(name.contains("Shadow")) {faction=true;}
                if(name.contains("Guardian")) {faction=true;}
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 114: emDamage = currentSRV; break;
                        case 116: explosiveDamage = currentSRV; break;
                        case 117: kineticDamage = currentSRV; break;
                        case 118: thermalDamage = currentSRV; break;
                        case 120: rangeBonus = currentSRV; break;
                        case 128: chargeSize = sqlResult.getString("valueInt");
                                  break;
                        case 317: capacitorBonus = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        hybridAmmo = new PosAmmoHybrid
            (
                rangeBonus, capacitorBonus, chargeSize, faction, name,
                emDamage, explosiveDamage, kineticDamage, thermalDamage, mass,
                volume, description, basePrice
            );
        return hybridAmmo;
    }
    private PosAmmoMissile getPosAmmoMissile(String ammoName){
        PosAmmoMissile missileAmmo = null;
        double  maximumFlightTime=0,  maximumVelocity=0,  explosionVelocity=0,
                explosionRadius=0, emDamage=0, explosiveDamage=0,
                kineticDamage=0, thermalDamage=0, volume=0, basePrice=0;
        String  chargeSize="", name="", description="";
        boolean faction=false;
        int     mass=0;

        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(ammoName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
                if(name.contains("Guristas")){ faction=true;  }
                if(name.contains("Caldari")) { faction=true;  }
                if(name.contains("Cruise"))  { chargeSize="1"; }
                if(name.contains("Torpedo")) { chargeSize="2"; }
                if(name.contains("Citadel")) { chargeSize="3"; }
            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 37:  maximumVelocity = currentSRV; break;
                        case 114: emDamage = currentSRV; break;
                        case 116: explosiveDamage = currentSRV; break;
                        case 117: kineticDamage = currentSRV; break;
                        case 118: thermalDamage = currentSRV; break;
                        case 281: maximumFlightTime = currentSRV; break;
                        case 653: explosionVelocity = currentSRV; break;
                        case 654: explosionRadius = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        missileAmmo = new PosAmmoMissile
            (
                maximumFlightTime, maximumVelocity, explosionVelocity,
                explosionRadius, chargeSize, faction, name, emDamage,
                explosiveDamage, kineticDamage, thermalDamage, mass, volume,
                description, basePrice
            );
        return missileAmmo;
    }
    private PosAmmoProjectile getPosAmmoProjectile(String ammoName){
        PosAmmoProjectile projectileAmmo;
        double  rangeBonus=0, explosiveDamage=0, emDamage=0, kineticDamage=0,
                thermalDamage=0, volume=0, basePrice=0, trackingBonus=0;
        String  chargeSize="", description="", name="";
        boolean faction=false;
        int     mass=0;
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection
                (
                    "jdbc:sqlite:" + dbLocation + dbEve
                );
            Statement sqlStatement = database.createStatement();

            String typeID = "";

            ResultSet sqlResult = sqlStatement.executeQuery(this.getItemSQL(ammoName));

            while (sqlResult.next())
            {
                typeID = sqlResult.getString("typeID");
                name = sqlResult.getString("typeName");
                mass = sqlResult.getInt("mass");
                volume = sqlResult.getDouble("volume");
                description = sqlResult.getString("description");
                basePrice = sqlResult.getDouble("basePrice");
                if(name.contains("Arch"))       {faction=true;}
                if(name.contains("Domination")) {faction=true;}
                if(name.contains("Republic"))   {faction=true;}

            }
            sqlResult.close();

            //#### Get Detailed Attributes
            sqlResult = sqlStatement.executeQuery(this.getDetailSQL(typeID));

            while (sqlResult.next())
            {
                try
                {
                    Double currentSRV; //currentSqlResultValue
                    if (sqlResult.getString("valueInt")==null)
                    {
                        currentSRV = sqlResult.getDouble("valueFloat");
                    }
                    else
                    {
                        currentSRV = sqlResult.getDouble("valueInt");
                    }
                    switch (sqlResult.getInt("attributeID"))
                    {
                        case 114: emDamage = currentSRV; break;
                        case 116: explosiveDamage = currentSRV; break;
                        case 117: kineticDamage = currentSRV; break;
                        case 118: thermalDamage = currentSRV; break;
                        case 120: rangeBonus = currentSRV; break;
                        case 128: chargeSize = sqlResult.getString("valueInt");
                                  break;
                        case 244: trackingBonus = currentSRV; break;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        projectileAmmo = new PosAmmoProjectile
            (
                rangeBonus, chargeSize, faction, name, emDamage,
                explosiveDamage, kineticDamage, thermalDamage, mass,
                volume, description, basePrice, trackingBonus
            );
        return projectileAmmo;
    }

    private String getDetailSQL(String typeID){
        return "SELECT displayName, dgmattributetypes.attributeID, attributeName, "+
               "valueInt, valueFloat FROM dgmtypeattributes INNER JOIN dgmattributetypes "+
               "ON dgmtypeattributes.attributeID=dgmattributetypes.attributeID WHERE "+
               "typeID=\""+typeID+"\";";
    }
    private String getItemSQL(String structureName){
        return "SELECT * FROM invtypes WHERE typeName LIKE \""+structureName+"\";";
    }
    private String getGroupName(int groupID){
        String groupName = "";
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection("jdbc:sqlite:" + dbLocation + dbEve);
            Statement sqlStatement = database.createStatement();
            ResultSet sqlResult = sqlStatement.executeQuery("SELECT groupName FROM invgroups WHERE groupID = \""+groupID+"\";");

            while (sqlResult.next()){ groupName = sqlResult.getString("groupName"); }

            sqlResult.close();
            database.close();
        }
        catch(Exception e){}
        return groupName;
    }
    public String[] getLocationDetails(long moonID, long solarSytemID){
        long constellationID = 0L;
        long regionID = 0L;
        String[] locationNames = new String[5];
        //locationNames[0] = Moon / System Name
        //locationNames[1] = System Security Status
        //locationNames[2] = Constellation Name
        //locationNames[3] = Region Name
        //locationNames[4] = Universe

        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection("jdbc:sqlite:" + dbLocation + dbEve);
            Statement sqlStatement = database.createStatement();

            ResultSet sqlResult = sqlStatement.executeQuery("SELECT itemName FROM mapdenormalize WHERE itemID = "+moonID+";");
            while (sqlResult.next())
            {
                locationNames[0] = sqlResult.getString("itemName");
            }
            sqlResult.close();

            sqlResult = sqlStatement.executeQuery("SELECT constellationID,security FROM mapSolarSystems WHERE solarSystemID = "+solarSytemID+";");
            while (sqlResult.next())
            {
                locationNames[1] = sqlResult.getString("security");
                constellationID = sqlResult.getLong("constellationID");
            }
            sqlResult.close();

            sqlResult = sqlStatement.executeQuery("SELECT regionID,constellationName FROM mapConstellations WHERE constellationID = "+constellationID+";");
            while (sqlResult.next())
            {
                locationNames[2] = sqlResult.getString("constellationName");
                regionID = sqlResult.getLong("regionID");
            }
            sqlResult.close();

            sqlResult = sqlStatement.executeQuery("SELECT regionName FROM mapRegions WHERE regionID = "+regionID+";");
            while (sqlResult.next())
            {
                locationNames[3] = sqlResult.getString("regionName");
            }
            sqlResult.close();
            database.close();

            if(locationNames[0]!=null)
            {
                if(locationNames[0].matches("^J[0-9]{6}.*$"))
                {
                    locationNames[4] = "Wormhole Space";
                }
                else
                {
                    locationNames[4] = "Normal Space";
                }
            }
        }
        catch(Exception e){ e.printStackTrace(); return null; }
        return locationNames;
    }
    public String getTypeName(long typeID){
        String typeName = "";
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection("jdbc:sqlite:" + dbLocation + dbEve);
            Statement sqlStatement = database.createStatement();
            ResultSet sqlResult = sqlStatement.executeQuery("SELECT typeName FROM invTypes WHERE typeID = "+typeID+";");

            while (sqlResult.next()){ typeName = sqlResult.getString("typeName"); }

            sqlResult.close();
            database.close();
        }
        catch(Exception e){ e.printStackTrace(); return null; }
        return typeName;
    }
    public String getTowerIsotope(long typeID){
        String isotopeName = "";
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection("jdbc:sqlite:" + dbLocation + dbEve);
            Statement sqlStatement = database.createStatement();

            String sql = "SELECT  typeName FROM invcontroltowerresources INNER "+
           "JOIN invtypes ON invcontroltowerresources.resourceTypeID=invtypes.typeID "+
           "WHERE controlTowerTypeID = \""+typeID+"\" AND typeName LIKE \"%Isotopes\";";

            ResultSet sqlResult = sqlStatement.executeQuery(sql);

            while (sqlResult.next()){ isotopeName = sqlResult.getString("typeName"); }

            sqlResult.close();
            database.close();
        }
        catch(Exception e){ e.printStackTrace(); return null; }
        return isotopeName;
    }

    /*
     * long[0] = fuelID
     * long[1] = quanity
     */
    public ArrayList<long[]> getTowerFuelLevels(long typeID){
        ArrayList<long[]> fuelLevels = new ArrayList<long[]>();
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection("jdbc:sqlite:" + dbLocation + dbEve);
            Statement sqlStatement = database.createStatement();

            String sql = "SELECT  resourceTypeID, quantity FROM invcontroltowerresources INNER "+
           "JOIN invtypes ON invcontroltowerresources.resourceTypeID=invtypes.typeID "+
           "WHERE controlTowerTypeID = \""+typeID+"\";";

            ResultSet sqlResult = sqlStatement.executeQuery(sql);

            while (sqlResult.next())
            {
                fuelLevels.add(new long[]
                {
                    sqlResult.getLong("resourceTypeID"),
                    sqlResult.getLong("quantity")
                });
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e){ e.printStackTrace(); return null; }
        return fuelLevels;
    }

    /*
     * apiDetails[0] = apiKey
     * apiDetails[1] = userID
     * apiDetails[2] = characterID
     * apiDetails[3] = structureID (posItemID)
     * apiDetails[4] = monitorName
     */
    public boolean saveMonitorSession(String[] apiDetails,
            StarbaseDetailResponse posDetail, StarbaseListResponse posList){
        //Check vlaues are not null
        //Check values are not empty or 0 (where applicable)
        String prefix = "ERROR: Saving Monitor Session: ";
        if(apiDetails==null)
        { System.err.println(prefix + "Api Details Null"); return false; }

        if(apiDetails[0]==null || apiDetails[1]==null || apiDetails[2]==null || 
           apiDetails[3]==null || apiDetails[4]==null)
        { System.err.println(prefix + "Api Detail Contents Null"); return false; }

        if(posDetail==null)
        { System.err.println(prefix + "Starbase Detail Response Null"); return false; }
        if(posDetail.hasError())
        {System.err.println(prefix + "Starbase Detail Has An Error"); return false;}

        if(posList==null)
        { System.err.println(prefix + "Starbase List Response Null"); return false; }
        if(posList.hasError())
        {System.err.println(prefix + "Starbase List Has An Error"); return false;}

        if(posDetail.getCachedUntil()==null)
        {System.err.println(prefix + "PosDetail Cached Until Null"); return false;}
        if(posDetail.getCurrentTime()==null)
        {System.err.println(prefix + "PosDetail Current Time Null"); return false;}
        if(posDetail.getFuelMap()==null)
        {System.err.println(prefix + "Fuel Map Null"); return false;}
//        if(posDetail.getFuelMap().isEmpty())
//        {System.err.println(prefix + "Fuel Map Empty"); return false;}
        if(posDetail.getOnAggression()==null)
        {System.err.println(prefix + "On Aggression Null"); return false;}
        if(posDetail.getOnCorporationWar()==null)
        {System.err.println(prefix + "On Corp War Null"); return false;}
        if(posDetail.getOnStandingDrop()==null)
        {System.err.println(prefix + "On Standing Drop Null"); return false;}
        if(posDetail.getOnStatusDrop()==null)
        {System.err.println(prefix + "On Status Drop Null"); return false;}

        if(posList.getCachedUntil()==null)
        {System.err.println(prefix + "PosList Cached Until Null"); return false;}
        if(posList.getCurrentTime()==null)
        {System.err.println(prefix + "PosList Current Time Null"); return false;}
        if(posList.getStarbases()==null)
        {System.err.println(prefix + "Starbases Null"); return false;}
        if(posList.getStarbases().isEmpty())
        {System.err.println(prefix + "Starbases Empty"); return false;}

        //TODO: Check that database table exists
        String createTableString =
        "CREATE TABLE IF NOT EXISTS savedMonitorSessions("+
            "monitorID INTEGER PRIMARY KEY NOT NULL,"+
            "monitorName TEXT NOT NULL,"+
            "apiKey TEXT NOT NULL,"+
            "userID TEXT NOT NULL,"+
            "characterID TEXT NOT NULL,"+
            "structureID TEXT NOT NULL,"+
            "ListCachedUntil TEXT NOT NULL,"+
            "ListCurrentTime TEXT NOT NULL,"+
            "ListApiVersion TEXT NOT NULL,"+
            "ListItemID TEXT NOT NULL,"+
            "ListLocationID TEXT NOT NULL,"+
            "ListMoonID TEXT NOT NULL,"+
            "ListOnlineTime TEXT NOT NULL,"+
            "ListState TEXT NOT NULL,"+
            "ListStateTime TEXT NOT NULL,"+
            "ListTypeID TEXT NOT NULL,"+
            "DetailDeployFlags TEXT NOT NULL,"+
            "DetailOnAggression TEXT NOT NULL,"+
            "DetailOnCorpWar TEXT NOT NULL,"+
            "DetailOnStandingDrop TEXT NOT NULL,"+
            "DetailOnStatusDrop TEXT NOT NULL,"+
            "DetailUseageFlags TEXT NOT NULL,"+
            "DetailAllowAlliance TEXT NOT NULL,"+
            "DetailAllowCorp TEXT NOT NULL,"+
            "DetailFuelIsotopes TEXT,"+
            "DetailFuelEnrichedU TEXT,"+
            "DetailFuelOxygen TEXT,"+
            "DetailFuelMechParts TEXT,"+
            "DetailFuelCoolant TEXT,"+
            "DetailFuelRobotics TEXT,"+
            "DetailFuelCharter TEXT,"+
            "DetailFuelLiquidO TEXT,"+
            "DetailFuelHeavyW TEXT,"+
            "DetailFuelStrontium TEXT"+
        ");";

        try
        {
            Connection database = null;
            try
            {
                Class.forName("org.sqlite.JDBC");
                database = DriverManager.getConnection("jdbc:sqlite:" + dbLocation + dbMonitor);
                Statement sqlStatement = database.createStatement();

                //TODO: Create table if it does not exist
                int sqlResult = sqlStatement.executeUpdate(createTableString);

                //TODO: Create a row with details for this montior
                PreparedStatement addNewRow = database.prepareStatement
                ("INSERT INTO savedMonitorSessions VALUES (?,?,?,?,?,?,?,?,"+
                 "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");

                ArrayList<ApiStarbase> starbaseArray = new ArrayList<ApiStarbase>();
                starbaseArray.addAll(posList.getStarbases());
                ApiStarbase starbase = new ApiStarbase();
                for(ApiStarbase starbaseSingle : starbaseArray)
                {
                    if(Long.valueOf(apiDetails[3]).longValue() == starbaseSingle.getItemID())
                    {
                        starbase = starbaseSingle;
                        break;
                    }
                }

                addNewRow.setString(1, null); //monitorID (Primary Key)
                addNewRow.setString(2, apiDetails[4]); //monitorName
                addNewRow.setString(3, apiDetails[0]); //apiKey
                addNewRow.setString(4, apiDetails[1]); //userID
                addNewRow.setString(5, apiDetails[2]); //characterID
                addNewRow.setString(6, apiDetails[3]); //structureID
                addNewRow.setString(7, posDetail.getCachedUntil().toString()); //ListCachedUntil
                addNewRow.setString(8, posDetail.getCurrentTime().toString()); //ListCurrentTime
                addNewRow.setString(9, String.valueOf(posList.getVersion())); //ListApiVersion
                addNewRow.setString(10,String.valueOf(starbase.getItemID())); //ListItemID
                addNewRow.setString(11,String.valueOf(starbase.getLocationID())); //ListLocationID
                addNewRow.setString(12,String.valueOf(starbase.getMoonID())); //ListMoonID
                addNewRow.setString(13,starbase.getOnlineTimestamp().toString()); //ListOnlineTime
                addNewRow.setString(14,String.valueOf(starbase.getState())); //ListState
                addNewRow.setString(15,starbase.getStateTimestamp().toString()); //ListStateTime
                addNewRow.setString(16,String.valueOf(starbase.getTypeID())); //ListTypeID
                addNewRow.setString(17,String.valueOf(posDetail.getDeployFlags())); //DetailDeployFlags
                addNewRow.setString(18,String.valueOf(posDetail.getOnAggression().isEnabled())); //DetailOnAggression
                addNewRow.setString(19,String.valueOf(posDetail.getOnCorporationWar().isEnabled())); //DetailOnCorpWar
                addNewRow.setString(20,String.valueOf(posDetail.getOnStandingDrop().isEnabled())); //DetailOnStandingDrop
                addNewRow.setString(21,String.valueOf(posDetail.getOnStatusDrop().isEnabled())); //DetailOnStatusDrop
                addNewRow.setString(22,String.valueOf(posDetail.getUsageFlags())); //DetailUseageFlags
                addNewRow.setString(23,String.valueOf(posDetail.isAllowAllianceMembers())); //DetailAllowAlliance
                addNewRow.setString(24,String.valueOf(posDetail.isAllowCorporationMembers())); //DetailAllowCorp

                Set<Integer> fuelKeySet = posDetail.getFuelMap().keySet();
                for(Integer fuelKey : fuelKeySet)
                {
                    int fuelIndex = 0;
                    switch(fuelKey)
                    {
                        case 44: fuelIndex = 26; break; //DetailFuelEnrichedU
                        case 3683: fuelIndex = 27; break; //DetailFuelOxygen
                        case 3689: fuelIndex = 28; break; //DetailFuelMechParts
                        case 9832: fuelIndex = 29; break; //DetailFuelCoolant
                        case 9848: fuelIndex = 30; break; //DetailFuelRobotics
                        case 16272: fuelIndex = 33; break; //DetailFuelHeavyW
                        case 16273: fuelIndex = 32; break; //DetailFuelLiquidO
                        case 16275: fuelIndex = 34; break; //DetailFuelStrontium
                        case 16274:
                        case 17887:
                        case 17888:
                        case 17889: fuelIndex = 25; break; //DetailFuelIsotopes
                        case 24592:
                        case 24593:
                        case 24594:
                        case 24595:
                        case 24596:
                        case 24597: fuelIndex = 31; break; //DetailFuelCharter
                        default: throw new Exception("Fuel Key Miss Match");
                    }
                    addNewRow.setString(fuelIndex,posDetail.getFuelMap().get(fuelKey).toString());
                }

                addNewRow.addBatch();
                database.setAutoCommit(false);
                addNewRow.executeBatch();
                database.commit();
                database.setAutoCommit(true);
                database.close();
            }
            catch(Exception e){ database.close(); e.printStackTrace(); return false; }
        }
        catch(Exception e) { e.printStackTrace(); return false; }
        return true;
    }
    public boolean updateSavedSession(String[] apiDetails,
            StarbaseDetailResponse posDetail, StarbaseListResponse posList){
        try
        {
            Connection database = null;
            try
            {
                Class.forName("org.sqlite.JDBC");
                database = DriverManager.getConnection("jdbc:sqlite:" + dbLocation + dbMonitor);

                //TODO: Update a row with details for this montior
                PreparedStatement addNewRow = database.prepareStatement
                (
                    "UPDATE savedMonitorSessions SET monitorName = ?, "+
                    "apiKey = ?, userID = ?, characterID = ?, structureID = ?, "+
                    "ListCachedUntil = ?, ListCurrentTime = ?, ListApiVersion = ?, "+
                    "ListItemID = ?, ListLocationID = ?, ListMoonID = ?, "+
                    "ListOnlineTime = ?, ListState = ?, ListStateTime = ?, "+
                    "ListTypeID = ?, DetailDeployFlags = ?, DetailOnAggression = ?, "+
                    "DetailOnCorpWar = ?, DetailOnStandingDrop = ?, "+
                    "DetailOnStatusDrop = ?, DetailUseageFlags = ?, "+
                    "DetailAllowAlliance = ?, DetailAllowCorp = ?, "+
                    "DetailFuelIsotopes = ?, DetailFuelEnrichedU = ?, "+
                    "DetailFuelOxygen = ?, DetailFuelMechParts = ?, "+
                    "DetailFuelCoolant = ?, DetailFuelRobotics = ?, "+
                    "DetailFuelCharter = ?, DetailFuelLiquidO = ?, "+
                    "DetailFuelHeavyW = ?, DetailFuelStrontium = ? "+
                    "WHERE monitorName = ?;"
                );

                ArrayList<ApiStarbase> starbaseArray = new ArrayList<ApiStarbase>();
                starbaseArray.addAll(posList.getStarbases());
                ApiStarbase starbase = new ApiStarbase();
                for(ApiStarbase starbaseSingle : starbaseArray)
                {
                    if(Long.valueOf(apiDetails[3]).longValue() == starbaseSingle.getItemID())
                    {
                        starbase = starbaseSingle;
                        break;
                    }
                }

                addNewRow.setString(34,apiDetails[4]);
                addNewRow.setString(1, apiDetails[4]); //monitorName
                addNewRow.setString(2, apiDetails[0]); //apiKey
                addNewRow.setString(3, apiDetails[1]); //userID
                addNewRow.setString(4, apiDetails[2]); //characterID
                addNewRow.setString(5, apiDetails[3]); //structureID
                addNewRow.setString(6, posDetail.getCachedUntil().toString()); //ListCachedUntil
                addNewRow.setString(7, posDetail.getCurrentTime().toString()); //ListCurrentTime
                addNewRow.setString(8, String.valueOf(posList.getVersion())); //ListApiVersion
                addNewRow.setString(9, String.valueOf(starbase.getItemID())); //ListItemID
                addNewRow.setString(10,String.valueOf(starbase.getLocationID())); //ListLocationID
                addNewRow.setString(11,String.valueOf(starbase.getMoonID())); //ListMoonID
                addNewRow.setString(12,starbase.getOnlineTimestamp().toString()); //ListOnlineTime
                addNewRow.setString(13,String.valueOf(starbase.getState())); //ListState
                addNewRow.setString(14,starbase.getStateTimestamp().toString()); //ListStateTime
                addNewRow.setString(15,String.valueOf(starbase.getTypeID())); //ListTypeID
                addNewRow.setString(16,String.valueOf(posDetail.getDeployFlags())); //DetailDeployFlags
                addNewRow.setString(17,String.valueOf(posDetail.getOnAggression().isEnabled())); //DetailOnAggression
                addNewRow.setString(18,String.valueOf(posDetail.getOnCorporationWar().isEnabled())); //DetailOnCorpWar
                addNewRow.setString(19,String.valueOf(posDetail.getOnStandingDrop().isEnabled())); //DetailOnStandingDrop
                addNewRow.setString(20,String.valueOf(posDetail.getOnStatusDrop().isEnabled())); //DetailOnStatusDrop
                addNewRow.setString(21,String.valueOf(posDetail.getUsageFlags())); //DetailUseageFlags
                addNewRow.setString(22,String.valueOf(posDetail.isAllowAllianceMembers())); //DetailAllowAlliance
                addNewRow.setString(23,String.valueOf(posDetail.isAllowCorporationMembers())); //DetailAllowCorp

                Set<Integer> fuelKeySet = posDetail.getFuelMap().keySet();
                for(Integer fuelKey : fuelKeySet)
                {
                    int fuelIndex = 0;
                    switch(fuelKey)
                    {
                        case 44: fuelIndex = 25; break; //DetailFuelEnrichedU
                        case 3683: fuelIndex = 26; break; //DetailFuelOxygen
                        case 3689: fuelIndex = 27; break; //DetailFuelMechParts
                        case 9832: fuelIndex = 28; break; //DetailFuelCoolant
                        case 9848: fuelIndex = 29; break; //DetailFuelRobotics
                        case 16272: fuelIndex = 32; break; //DetailFuelHeavyW
                        case 16273: fuelIndex = 31; break; //DetailFuelLiquidO
                        case 16275: fuelIndex = 33; break; //DetailFuelStrontium
                        case 16274:
                        case 17887:
                        case 17888:
                        case 17889: fuelIndex = 24; break; //DetailFuelIsotopes
                        case 24592:
                        case 24593:
                        case 24594:
                        case 24595:
                        case 24596:
                        case 24597: fuelIndex = 30; break; //DetailFuelCharter
                        default: throw new Exception("Fuel Key Miss Match");
                    }
                    addNewRow.setString(fuelIndex,posDetail.getFuelMap().get(fuelKey).toString());
                }

                addNewRow.addBatch();
                database.setAutoCommit(false);
                addNewRow.executeBatch();
                database.commit();
                database.setAutoCommit(true);
                database.close();
            }
            catch(Exception e){ database.close(); e.printStackTrace(); return false; }
        }
        catch(Exception e) { e.printStackTrace(); return false; }
        return true;
    }
    public String[] getSavedSessionNames(){
        ArrayList<String> sessionNames = new ArrayList<String>();
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection("jdbc:sqlite:" + dbLocation + dbMonitor);
            Statement sqlStatement = database.createStatement();

            String sqlQuery = "SELECT monitorName FROM savedMonitorSessions ;";

            ResultSet sqlResult = sqlStatement.executeQuery(sqlQuery);
            while (sqlResult.next())
            {
                try
                {
                     sessionNames.add(sqlResult.getString("monitorName"));
                }
                catch(Exception e) { e.printStackTrace(); return null; }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e) { e.printStackTrace(); return null; }
        if(sessionNames.isEmpty())
        {
            return null;
        }
        else
        {
            return Arrays.copyOf(sessionNames.toArray(), sessionNames.toArray().length, String[].class);
        }
    }
    public boolean deleteSavedMonitorSession(String monitorName){
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection("jdbc:sqlite:" + dbLocation + dbMonitor);
            Statement sqlStatement = database.createStatement();
            String sqlQuery = "DELETE FROM savedMonitorSessions WHERE monitorName = '"+monitorName+"';";
            int sqlResult = sqlStatement.executeUpdate(sqlQuery);
            database.close();
        }
        catch(Exception e) { e.printStackTrace(); return false; }
        return true;
    }
    public boolean loadMonitorSession(String[] apiDetails,
            StarbaseDetailResponse posDetail, StarbaseListResponse posList){
        //apiDetails[0] = apiKey
        //apiDetails[1] = userID
        //apiDetails[2] = characterID
        //apiDetails[3] = structureID (posItemID)
        //apiDetails[4] = monitorName
        try
        {
            DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection("jdbc:sqlite:" + dbLocation + dbMonitor);
            Statement sqlStatement = database.createStatement();

            String sqlQuery = "SELECT * FROM savedMonitorSessions WHERE monitorName = '"+apiDetails[4]+"' LIMIT 1;";

            ResultSet res = sqlStatement.executeQuery(sqlQuery);
            while (res.next())
            {
                try
                {
                    apiDetails[0] = res.getString("apiKey");
                    apiDetails[1] = res.getString("userID");
                    apiDetails[2] = res.getString("characterID");
                    apiDetails[3] = res.getString("structureID");

                    posList.setCachedUntil(df.parse(res.getString("ListCachedUntil")));
                    posList.setCurrentTime(df.parse(res.getString("ListCurrentTime")));
                    posList.setVersion(res.getInt("ListApiVersion"));
                    
                    ApiStarbase starbase = new ApiStarbase();
                    starbase.setItemID(res.getLong("ListItemID"));
                    starbase.setLocationID(res.getInt("ListLocationID"));
                    starbase.setMoonID(res.getInt("ListMoonID"));
                    starbase.setState(res.getInt("ListState"));
                    starbase.setTypeID(res.getInt("ListTypeID"));
                    starbase.setOnlineTimestamp(df.parse(res.getString("ListOnlineTime")));
                    starbase.setStateTimestamp(df.parse(res.getString("ListStateTime")));
                    posList.addStarbase(starbase);

                    FuelLevel isotopes = new FuelLevel();
                    FuelLevel enriched = new FuelLevel();
                    FuelLevel oxygen = new FuelLevel();
                    FuelLevel mechanical = new FuelLevel();
                    FuelLevel coolant = new FuelLevel();
                    FuelLevel robotics = new FuelLevel();
                    FuelLevel charter = new FuelLevel();
                    FuelLevel liquid = new FuelLevel();
                    FuelLevel heavy = new FuelLevel();
                    FuelLevel strontium = new FuelLevel();

                    try{isotopes.setQuantity(res.getInt("DetailFuelIsotopes"));
                    isotopes.setTypeID(17889);posDetail.addFuelLevel(isotopes);}
                    catch(Exception e){/*Do Nothing*/}

                    try{enriched.setQuantity(res.getInt("DetailFuelEnrichedU"));
                    enriched.setTypeID(44);posDetail.addFuelLevel(enriched);}
                    catch(Exception e){/*Do Nothing*/}

                    try{oxygen.setQuantity(res.getInt("DetailFuelOxygen"));
                    oxygen.setTypeID(3683);posDetail.addFuelLevel(oxygen);}
                    catch(Exception e){/*Do Nothing*/}

                    try{mechanical.setQuantity(res.getInt("DetailFuelMechParts"));
                    mechanical.setTypeID(3689);posDetail.addFuelLevel(mechanical);}
                    catch(Exception e){/*Do Nothing*/}

                    try{coolant.setQuantity(res.getInt("DetailFuelCoolant"));
                    coolant.setTypeID(9832);posDetail.addFuelLevel(coolant);}
                    catch(Exception e){/*Do Nothing*/}

                    try{robotics.setQuantity(res.getInt("DetailFuelRobotics"));
                    robotics.setTypeID(9848);posDetail.addFuelLevel(robotics);}
                    catch(Exception e){/*Do Nothing*/}

                    try{charter.setQuantity(res.getInt("DetailFuelCharter"));
                    charter.setTypeID(24596);posDetail.addFuelLevel(charter);}
                    catch(Exception e){/*Do Nothing*/}

                    try{liquid.setQuantity(res.getInt("DetailFuelLiquidO"));
                    liquid.setTypeID(16273);posDetail.addFuelLevel(liquid);}
                    catch(Exception e){/*Do Nothing*/}

                    try{heavy.setQuantity(res.getInt("DetailFuelHeavyW"));
                    heavy.setTypeID(16272);posDetail.addFuelLevel(heavy);}
                    catch(Exception e){/*Do Nothing*/}

                    try{strontium.setQuantity(res.getInt("DetailFuelStrontium"));
                    strontium.setTypeID(16275);posDetail.addFuelLevel(strontium);}
                    catch(Exception e){/*Do Nothing*/}

                    posDetail.setDeployFlags(res.getInt("DetailDeployFlags"));
                    posDetail.setUsageFlags(res.getInt("DetailUseageFlags"));
                    posDetail.setAllowAllianceMembers(Boolean.parseBoolean(res.getString("DetailAllowAlliance")));
                    posDetail.setAllowCorporationMembers(Boolean.parseBoolean(res.getString("DetailAllowCorp")));
                    posDetail.setCachedUntil(df.parse(res.getString("ListCachedUntil")));
                    posDetail.setCurrentTime(df.parse(res.getString("ListCurrentTime")));

                    ApiCombatSetting onAgg = new ApiCombatSetting();
                    ApiCombatSetting onCW = new ApiCombatSetting();
                    ApiCombatSetting onSND = new ApiCombatSetting();
                    ApiCombatSetting onSTD = new ApiCombatSetting();

                    onAgg.setEnabled(Boolean.parseBoolean(res.getString("DetailOnAggression")));
                    onCW.setEnabled(Boolean.parseBoolean(res.getString("DetailOnCorpWar")));
                    onSND.setEnabled(Boolean.parseBoolean(res.getString("DetailOnStandingDrop")));
                    onSTD.setEnabled(Boolean.parseBoolean(res.getString("DetailOnStatusDrop")));

                    posDetail.setOnAggression(onAgg);
                    posDetail.setOnCorporationWar(onCW);
                    posDetail.setOnStandingDrop(onSND);
                    posDetail.setOnStatusDrop(onSTD);
                }
                catch(Exception e) { e.printStackTrace(); return false; }
            }
            res.close();
            database.close();
        }
        catch(Exception e) { e.printStackTrace(); return false; }
        return true;
    }

    public ArrayList<String[]> getModuleList(String parentNode, String node, String categoryID){   //Blaster Small 449L
        ArrayList<String[]> moduleList = new ArrayList<String[]>();
        String sqlQuery = "";

        //Generate sql query
        if("Blaster".equals(parentNode) || "Railgun".equals(parentNode) ||
           "Beam Laser".equals(parentNode) || "Pulse Laser".equals(parentNode) ||
           "Artillery".equals(parentNode) || "AutoCannon".equals(parentNode))
        {
            sqlQuery = "SELECT typeID,typeName FROM invTypes WHERE groupID = "+
            categoryID+" AND published = 1 AND typeName LIKE '%"+node+"%"+parentNode+"%';";
        }
        if("Missile Batteries".equals(parentNode))
        {
            if("Torpedo".equals(node))
            {
                sqlQuery = "SELECT typeID,typeName FROM invTypes WHERE groupID = "+
                categoryID+" AND published = 1 AND typeName LIKE '%Torpedo%' AND typeName NOT LIKE '%Citadel%';";
            }
            else
            {
                sqlQuery = "SELECT typeID,typeName FROM invTypes WHERE groupID = "+
                categoryID+" AND published = 1 AND typeName LIKE '%"+node+"%';";
            }
        }
        if("Electronic Warfare".equals(parentNode) || "Defence & Storage".equals(parentNode)
        || "Industry".equals(parentNode) || "Miscellaneous".equals(parentNode))
        {
            sqlQuery = "SELECT typeID,typeName FROM invTypes WHERE groupID = "+
            categoryID+" AND published = 1;";
        }
        if("Control Tower".equals(parentNode))
        {
            sqlQuery = "SELECT typeID,typeName FROM invTypes WHERE groupID = "+
            categoryID+" AND published = 1;";
        }
        if(sqlQuery.isEmpty()){return null;}
        //Search Database
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection("jdbc:sqlite:" + dbLocation + dbEve);
            Statement sqlStatement = database.createStatement();

            ResultSet sqlResult = sqlStatement.executeQuery(sqlQuery);
            while (sqlResult.next())
            {
                try
                {
                    String name = sqlResult.getString("typeName");
                    String id = sqlResult.getString("typeID");
                    moduleList.add(new String[]{name, "", "", id});
                }
                catch(Exception e) { e.printStackTrace(); return null; }
            }
            sqlResult.close();

            for(String[] module : moduleList)
            {
                String cpu = "", power = "";
                sqlResult = sqlStatement.executeQuery(
                "SELECT dgmattributetypes.attributeID, attributeName, valueFloat, "+
                "valueInt FROM dgmtypeattributes INNER JOIN dgmattributetypes "+
                "ON dgmtypeattributes.attributeID=dgmattributetypes.attributeID"+
                " WHERE typeID='"+module[3]+"' AND (dgmtypeattributes.attributeID"+
                " = 30 OR dgmtypeattributes.attributeID = 50);");

                while (sqlResult.next())
                {
                    try
                    {
                         switch(sqlResult.getInt("attributeID"))
                         {
                             case 30:
                             {
                                 power = sqlResult.getString("valueInt");
                                 if(power==null)
                                 { power = Integer.toString(sqlResult.getInt("valueFloat")); }
                                 break;
                             }
                             case 50:
                             {
                                 cpu = sqlResult.getString("valueInt");
                                 if(cpu==null)
                                 { cpu = Integer.toString(sqlResult.getInt("valueFloat")); }
                                 break;
                             }
                         }
                    }
                    catch(Exception e) { e.printStackTrace(); return null; }
                }
                module[1] = cpu;
                module[2] = power;
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e) { e.printStackTrace(); return null; }

        //Return results
        return moduleList;
    }
    public ArrayList<String[]> getModuleList(String parentNode, String node, long[] categoryIDs){
        ArrayList<String[]> moduleList = new ArrayList<String[]>();
        String sqlQuery = "";

        //Generate sql query
        if("Search".equals(parentNode))
        {
            String groups = "";
            for(int i=0; i<categoryIDs.length; i++)
            {
                if(i==0){ groups = "groupID = " + categoryIDs[i] + " "; }
                else { groups = groups + "OR groupID = " + categoryIDs[i] + " "; }
            }
            sqlQuery = "SELECT typeID,typeName FROM invTypes WHERE typeName "+
                       "LIKE '%"+node+"%' AND published = 1 AND ("+groups+");";
        }
        else { return null; }

        //Search Database
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection("jdbc:sqlite:" + dbLocation + dbEve);
            Statement sqlStatement = database.createStatement();

            ResultSet sqlResult = sqlStatement.executeQuery(sqlQuery);
            while (sqlResult.next())
            {
                try
                {
                    String name = sqlResult.getString("typeName");
                    String id = sqlResult.getString("typeID");
                    moduleList.add(new String[]{name, "", "", id});
                }
                catch(Exception e) { e.printStackTrace(); return null; }
            }
            sqlResult.close();

            for(String[] module : moduleList)
            {
                String cpu = "", power = "";
                sqlResult = sqlStatement.executeQuery(
                "SELECT dgmattributetypes.attributeID, attributeName, valueFloat, "+
                "valueInt FROM dgmtypeattributes INNER JOIN dgmattributetypes "+
                "ON dgmtypeattributes.attributeID=dgmattributetypes.attributeID"+
                " WHERE typeID='"+module[3]+"' AND (dgmtypeattributes.attributeID"+
                " = 30 OR dgmtypeattributes.attributeID = 50);");

                while (sqlResult.next())
                {
                    try
                    {
                         switch(sqlResult.getInt("attributeID"))
                         {
                             case 30:
                             {
                                 power = sqlResult.getString("valueInt");
                                 if(power==null)
                                 { power = Integer.toString(sqlResult.getInt("valueFloat")); }
                                 break;
                             }
                             case 50:
                             {
                                 cpu = sqlResult.getString("valueInt");
                                 if(cpu==null)
                                 { cpu = Integer.toString(sqlResult.getInt("valueFloat")); }
                                 break;
                             }
                         }
                    }
                    catch(Exception e) { e.printStackTrace(); return null; }
                }
                module[1] = cpu;
                module[2] = power;
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e) { e.printStackTrace(); return null; }

        //Return results
        return moduleList;
    }

    /*
     * moduleDetails[0] = Module Name
     * moduleDetails[1] = Count
     */
    public boolean saveDesignSession(String designName, String towerName, ArrayList<String[]> moduleDetails){
        //Check vlaues are not null
        //Check values are not empty or 0 (where applicable)
        String prefix = "ERROR: Saving Design Session: ";
        if(designName==null){ System.err.println(prefix + "Design Name Null"); return false; }
        if(towerName==null){ System.err.println(prefix + "Tower Name Null"); return false; }
        //if(moduleDetails==null){System.err.println(prefix + "Module Details Null"); return false;}

        if(designName.isEmpty()){ System.err.println(prefix + "Design Name Empty"); return false; }
        if(towerName.isEmpty()){ System.err.println(prefix + "Tower Name Empty"); return false; }
        //if(moduleDetails.isEmpty()){System.err.println(prefix + "Module Details Empty"); return false;}

        //TODO: Check that database table exists
        String createTable1String =
            "CREATE TABLE IF NOT EXISTS savedDesignSessions ("+
            "designID INTEGER PRIMARY KEY NOT NULL,"+
            "designName TEXT NOT NULL,"+
            "controlTowerName TEXT NOT NULL,"+
            "systemSecurityLevel REAL"+
            ");";

        String createTable2String =
            "CREATE TABLE IF NOT EXISTS designSessionModules ("+
            "designModuleID INTEGER PRIMARY KEY NOT NULL,"+
            "designID INTEGER NOT NULL,"+
            "moduleName TEXT NOT NULL,"+
            "countOfModules INTEGER NOT NULL"+
            ");";

        try
        {
            Connection database = null;
            try
            {
                Class.forName("org.sqlite.JDBC");
                database = DriverManager.getConnection("jdbc:sqlite:" + dbLocation + dbDesign);
                Statement sqlStatement = database.createStatement();

                //TODO: Create table if it does not exist
                sqlStatement.executeUpdate(createTable1String);
                sqlStatement.executeUpdate(createTable2String);

                //TODO: Create a row with details for this montior
                PreparedStatement addNewRow = database.prepareStatement
                ("INSERT INTO savedDesignSessions VALUES (?,?,?,?);");

                addNewRow.setString(1, null); //designID
                addNewRow.setString(2, designName); //designName
                addNewRow.setString(3, towerName); //controlTowerName
                addNewRow.setString(4, null); //systemSecurityLevel

                addNewRow.addBatch();
                database.setAutoCommit(false);
                addNewRow.executeBatch();
                database.commit();

                if(moduleDetails!=null)
                {
                    String designID = "";
                    String sqlThingy =
                    "SELECT designID FROM savedDesignSessions WHERE designName = '"+
                    designName+"';";

                    ResultSet sqlResult = sqlStatement.executeQuery(sqlThingy);
                    while (sqlResult.next())
                    {
                        try
                        {
                             designID = sqlResult.getString("designID");
                        }
                        catch(Exception e) { e.printStackTrace(); return false; }
                    }
                    sqlResult.close();

                    for(String[] module : moduleDetails)
                    {
                        addNewRow = database.prepareStatement
                        ("INSERT INTO designSessionModules VALUES (?,?,?,?);");
                        addNewRow.setString(1, null);      //designModuleID
                        addNewRow.setString(2, designID);  //designID
                        addNewRow.setString(3, module[0]); //moduleName
                        addNewRow.setString(4, module[1]); //countOfModules
                        addNewRow.addBatch();
                        addNewRow.executeBatch();
                        database.commit();
                    }
                }
                database.setAutoCommit(true);
                database.close();
            }
            catch(Exception e){ database.close(); e.printStackTrace(); return false; }
        }
        catch(Exception e) { e.printStackTrace(); return false; }
        return true;
    }
    public boolean updateDesignSession(String designName, String towerName, ArrayList<String[]> moduleDetails){
        try
        {
            Connection database = null;
            try
            {
                Class.forName("org.sqlite.JDBC");
                database = DriverManager.getConnection("jdbc:sqlite:" + dbLocation + dbDesign);
                Statement sqlStatement = database.createStatement();
                database.setAutoCommit(false);
                
                //Get Design ID
                String designID = "";
                String sqlQuery =
                "SELECT designID FROM savedDesignSessions WHERE designName = '"+
                designName+"' AND controlTowerName = '"+towerName+"';";

                ResultSet sqlResult = sqlStatement.executeQuery(sqlQuery);
                while (sqlResult.next())
                {
                    try
                    {
                         designID = sqlResult.getString("designID");
                    }
                    catch(Exception e) { e.printStackTrace(); return false; }
                }
                sqlResult.close();

                //Delete all rows in designSessionModules for this design
                sqlQuery = "DELETE FROM designSessionModules WHERE designID = '"+designID+"';";
                sqlStatement.executeUpdate(sqlQuery);

                //Insert new rows in designSessionModules for this desin
                for(String[] module : moduleDetails)
                {
                    PreparedStatement addNewRow = database.prepareStatement
                    ("INSERT INTO designSessionModules VALUES (?,?,?,?);");
                    addNewRow.setString(1, null);      //designModuleID
                    addNewRow.setString(2, designID);  //designID
                    addNewRow.setString(3, module[0]); //moduleName
                    addNewRow.setString(4, module[1]); //countOfModules
                    addNewRow.addBatch();
                    addNewRow.executeBatch();
                    database.commit();
                }
                database.setAutoCommit(true);
                database.close();
            }
            catch(Exception e){ database.close(); e.printStackTrace(); return false; }
        }
        catch(Exception e) { e.printStackTrace(); return false; }
        return true;
    }
    public boolean deleteDesignSession(String designName){
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection("jdbc:sqlite:" + dbLocation + dbDesign);
            Statement sqlStatement = database.createStatement();
            
            //Get Design ID
            String designID = "";
            String sqlQuery =
            "SELECT designID FROM savedDesignSessions WHERE designName = '"+
                    designName+"';";

            ResultSet sqlResult = sqlStatement.executeQuery(sqlQuery);
            while (sqlResult.next())
            {
                try
                {
                     designID = sqlResult.getString("designID");
                }
                catch(Exception e) { e.printStackTrace(); return false; }
            }
            sqlResult.close();
                
            //Delete design modules
            sqlQuery = "DELETE FROM designSessionModules WHERE designID = '"+
                    designID+"';";
            sqlStatement.executeUpdate(sqlQuery);
            
            //Delete design
            sqlQuery = "DELETE FROM savedDesignSessions WHERE designID = '"+
                    designID+"';";
            sqlStatement.executeUpdate(sqlQuery);
            
            database.close();
        }
        catch(Exception e) { e.printStackTrace(); return false; }
        return true;
    }
    public boolean loadDesignSession(String designName, String[] towerName, ArrayList<String[]> moduleDetails){
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection("jdbc:sqlite:" + dbLocation + dbDesign);
            Statement sqlStatement = database.createStatement();

            //Get Design ID
            String designID = "";
            String sqlQuery =
            "SELECT * FROM savedDesignSessions WHERE designName = '"+designName+"';";

            ResultSet sqlResult = sqlStatement.executeQuery(sqlQuery);
            while (sqlResult.next())
            {
                try
                {
                     designID = sqlResult.getString("designID");
                     towerName[0] = sqlResult.getString("controlTowerName");
                }
                catch(Exception e) { e.printStackTrace(); return false; }
            }
            sqlResult.close();
            
            sqlQuery = "SELECT * FROM designSessionModules WHERE designID = '"+designID+"';";

            ResultSet res = sqlStatement.executeQuery(sqlQuery);
            while (res.next())
            {
                try
                {
                    moduleDetails.add(new String[]
                    {
                        res.getString("moduleName"),
                        res.getString("countOfModules")
                    });
                }
                catch(Exception e) { e.printStackTrace(); return false; }
            }
            res.close();
            database.close();
        }
        catch(Exception e) { e.printStackTrace(); return false; }
        return true;
    }
    public String[] getDesignSessionNames(){
        ArrayList<String> sessionNames = new ArrayList<String>();
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection("jdbc:sqlite:" + dbLocation + dbDesign);
            Statement sqlStatement = database.createStatement();

            String sqlQuery = "SELECT designName FROM savedDesignSessions ;";

            ResultSet sqlResult = sqlStatement.executeQuery(sqlQuery);
            while (sqlResult.next())
            {
                try
                {
                     sessionNames.add(sqlResult.getString("designName"));
                }
                catch(Exception e) { e.printStackTrace(); return null; }
            }
            sqlResult.close();
            database.close();
        }
        catch(Exception e) { e.printStackTrace(); return null; }
        if(sessionNames.isEmpty())
        {
            return null;
        }
        else
        {
            return Arrays.copyOf(sessionNames.toArray(), sessionNames.toArray().length, String[].class);
        }
    }
}