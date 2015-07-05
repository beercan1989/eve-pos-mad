package eveposmad;
import java.sql.*;
import java.util.ArrayList;
public class DropTables
{
    //String dbEveLocation="src/EvePosMad/Resources/DataBase/incursion101.sqlite3";
    String dbEveLocation="src/EvePosMad/Resources/DataBase/inc101-sqlite3-v1.sqlite3";
    public static void main(String[] args)
    {
        DropTables temp = new DropTables();
        temp.cleanUpCcpTables();
        temp.createMonitoringSessionTable();
        temp.createDesignSessionTable();
    }

    private void createDesignSessionTable(){
        String createTable1String =
        "CREATE TABLE IF NOT EXISTS savedDesignSessions ("+
        "designID INTEGER PRIMARY KEY NOT NULL,"+
        "designName TEXT NOT NULL,"+
        "controlTowerName TEXT NOT NULL,"+
        "systemSecurityLevel REAL"+
        ");";

        String createTable2String =
        "CREATE TABLE designSessionModules ("+
        "designModuleID INTEGER PRIMARY KEY NOT NULL,"+
        "designID INTEGER NOT NULL,"+
        "moduleName TEXT NOT NULL,"+
        "countOfModules INTEGER NOT NULL,"+
        //"moduleType TEXT"+
        ");";
        try
        {
            Connection database = null;
            try
            {
                Class.forName("org.sqlite.JDBC");
                database = DriverManager.getConnection("jdbc:sqlite:" + "src/EvePosMad/resources/database/" + "design.sqlite3");
                Statement sqlStatement = database.createStatement();
                sqlStatement.executeUpdate(createTable1String);
                sqlStatement.executeUpdate(createTable2String);
                database.close();
            }
            catch(Exception e){ database.close(); e.printStackTrace();}
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    private void createMonitoringSessionTable(){
        String createTableString =
        "CREATE TABLE IF NOT EXISTS savedMonitorSessions("+
            "monitorID INTEGER PRIMARY KEY NOT NULL,"+
            "monitorName NOT NULL,"+
            "apiKey NOT NULL,"+
            "userID NOT NULL,"+
            "characterID NOT NULL,"+
            "structureID NOT NULL,"+
            "ListCachedUntil NOT NULL,"+
            "ListCurrentTime NOT NULL,"+
            "ListApiVersion NOT NULL,"+
            "ListItemID NOT NULL,"+
            "ListLocationID NOT NULL,"+
            "ListMoonID NOT NULL,"+
            "ListOnlineTime NOT NULL,"+
            "ListState NOT NULL,"+
            "ListStateTime NOT NULL,"+
            "ListTypeID NOT NULL,"+
            "DetailDeployFlags NOT NULL,"+
            "DetailOnAggression NOT NULL,"+
            "DetailOnCorpWar NOT NULL,"+
            "DetailOnStandingDrop NOT NULL,"+
            "DetailOnStatusDrop NOT NULL,"+
            "DetailUseageFlags NOT NULL,"+
            "DetailAllowAlliance NOT NULL,"+
            "DetailAllowCorp NOT NULL,"+
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
                database = DriverManager.getConnection("jdbc:sqlite:" + "src/EvePosMad/resources/database/" + "monitor.sqlite3");
                Statement sqlStatement = database.createStatement();
                sqlStatement.executeUpdate(createTableString);
                database.close();
            }
            catch(Exception e){ database.close(); e.printStackTrace();}
        }
        catch(Exception e) { e.printStackTrace(); }
    }
    
    private void cleanUpCcpTables(){
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbEveLocation);
            Statement stat1 = conn.createStatement();

            // Delete Tables
            stat1.executeUpdate("drop table if exists crpnpccorporationtrades;");
            stat1.executeUpdate("drop table if exists crtrecommendations;");
            stat1.executeUpdate("drop table if exists eveicons ;");
            stat1.executeUpdate("drop table if exists invtypematerials;");
            stat1.executeUpdate("drop table if exists maplocationscenes;");
            stat1.executeUpdate("drop table if exists maplocationwormholeclasses;");
            stat1.executeUpdate("drop table if exists planetschematics;");
            stat1.executeUpdate("drop table if exists planetschematicspinmap;");
            stat1.executeUpdate("drop table if exists planetschematicstypemap;");
            stat1.executeUpdate("drop table if exists raminstallationtypecontents;");
            stat1.executeUpdate("drop table if exists ramtyperequirements;");
            stat1.executeUpdate("drop table if exists stastationtypes;");
            stat1.executeUpdate("drop table if exists agtAgentTypes;");
            stat1.executeUpdate("drop table if exists agtAgents;");
            stat1.executeUpdate("drop table if exists agtConfig;");
            stat1.executeUpdate("drop table if exists agtResearchAgents;");
            stat1.executeUpdate("drop table if exists chrAncestries;");
            stat1.executeUpdate("drop table if exists chrAttributes;");
            stat1.executeUpdate("drop table if exists chrBloodlines;");
            stat1.executeUpdate("drop table if exists chrCareerSpecialities;");
            stat1.executeUpdate("drop table if exists chrCareers;");
            stat1.executeUpdate("drop table if exists chrFactions;");
            stat1.executeUpdate("drop table if exists chrRaceSkills;");
            stat1.executeUpdate("drop table if exists chrRaces;");
            stat1.executeUpdate("drop table if exists chrSchoolAgents;");
            stat1.executeUpdate("drop table if exists chrSchools;");
            stat1.executeUpdate("drop table if exists crpActivities;");
            stat1.executeUpdate("drop table if exists crpNPCCorporationDivisions;");
            stat1.executeUpdate("drop table if exists crpNPCCorporationResearchFields;");
            stat1.executeUpdate("drop table if exists crpNPCCorporations;");
            stat1.executeUpdate("drop table if exists crpNPCDivisions;");
            stat1.executeUpdate("drop table if exists crtCategories;");
            stat1.executeUpdate("drop table if exists crtCertificates;");
            stat1.executeUpdate("drop table if exists crtClasses;");
            stat1.executeUpdate("drop table if exists crtRelationships;");
            stat1.executeUpdate("drop table if exists dgmAttributeCategories;");
            stat1.executeUpdate("drop table if exists dgmEffects;");
            stat1.executeUpdate("drop table if exists dgmTypeEffects;");
            stat1.executeUpdate("drop table if exists eveGraphics;");
            stat1.executeUpdate("drop table if exists eveNames;");
            stat1.executeUpdate("drop table if exists eveUnits;");
            stat1.executeUpdate("drop table if exists invBlueprintTypes;");
            stat1.executeUpdate("drop table if exists invCategories;");
            stat1.executeUpdate("drop table if exists invContrabandTypes;");
            stat1.executeUpdate("drop table if exists invFlags;");
            stat1.executeUpdate("drop table if exists invMetaGroups;");
            stat1.executeUpdate("drop table if exists invMetaTypes;");
            stat1.executeUpdate("drop table if exists invTypeReactions;");
            stat1.executeUpdate("drop table if exists mapCelestialstat1istics;");
            stat1.executeUpdate("drop table if exists mapConstellationJumps;");
            stat1.executeUpdate("drop table if exists mapJumps;");
            stat1.executeUpdate("drop table if exists mapLandmarks;");
            stat1.executeUpdate("drop table if exists mapRegionJumps;");
            stat1.executeUpdate("drop table if exists mapSolarSystemJumps;");
            stat1.executeUpdate("drop table if exists mapUniverse;");
            stat1.executeUpdate("drop table if exists ramActivities;");
            stat1.executeUpdate("drop table if exists ramAssemblyLinestat1ions;");
            stat1.executeUpdate("drop table if exists ramAssemblyLineTypeDetailPerCategory;");
            stat1.executeUpdate("drop table if exists ramAssemblyLineTypeDetailPerGroup;");
            stat1.executeUpdate("drop table if exists ramAssemblyLineTypes;");
            stat1.executeUpdate("drop table if exists ramAssemblyLines;");
            stat1.executeUpdate("drop table if exists staOperationServices;");
            stat1.executeUpdate("drop table if exists staOperations;");
            stat1.executeUpdate("drop table if exists staServices;");
            stat1.executeUpdate("drop table if exists stastat1ionTypes;");
            stat1.executeUpdate("drop table if exists stastat1ions;");
            stat1.executeUpdate("drop table if exists trnTranslationColumns;");
            stat1.executeUpdate("drop table if exists trnTranslations;");
            stat1.executeUpdate("drop table if exists typeActivityMaterials;");
            stat1.executeUpdate("drop table if exists mapcelestialstatistics;");
            stat1.executeUpdate("drop table if exists ramassemblylinestations;");
            stat1.executeUpdate("drop table if exists stastations;");
            stat1.executeUpdate("drop table if exists stastiontypes;");
            stat1.executeUpdate("drop table if exists invMarketGroups;");
            
            //Delete Table Rows
            //Delete all none moon items from mapDenormalize
            stat1.execute("DELETE FROM mapDenormalize WHERE itemName NOT LIKE '%Moon%';");

            String groupIDs_and_typeIDs_to_keep = "(((groupID=449 OR groupID=430 "+
            "OR groupID=417 OR groupID=426 OR groupID=837 OR groupID=440 OR "+
            "groupID=439 OR groupID=441 OR groupID=443 OR groupID=471 OR "+
            "groupID=363 OR groupID=444 OR groupID=397 OR groupID=413 OR "+
            "groupID=416 OR groupID=311 OR groupID=438 OR groupID=404 OR "+
            "groupID=838 OR groupID=839 OR groupID=707) OR (groupID=365) OR "+
            "(typeID=44 OR typeID=3683 OR typeID=3689 OR typeID=9832 OR "+
            "typeID=9848 OR typeID=16272 OR typeID=16273 OR typeID=16275 OR "+
            "typeID=16274 OR typeID=17887 OR typeID=17888 OR typeID=17889 OR "+
            "typeID=24592 OR typeID=24593 OR typeID=24594 OR typeID=24595 OR "+
            "typeID=24596 OR typeID=24597) OR (groupID=389 OR groupID=476 OR "+
            "groupID=386 OR groupID=85 OR groupID=86 OR groupID=83)) AND "+
            "published=1);";

            //Delete all none used items from dgmTypeAttributes
            //stat1.execute("DELETE FROM dgmTypeAttributes WHERE typeID NOT IN ( SELECT typeID FROM invTypes );");

            //Delete all none used items from invTypes
            stat1.execute("DELETE FROM invTypes WHERE NOT"+groupIDs_and_typeIDs_to_keep);


            //Remove Columns from mapDenormalize table
            stat1.execute("CREATE TEMPORARY TABLE temp_table(itemID int(11) NOT NULL, itemName varchar(100) default NULL, PRIMARY KEY  (itemID) );");
            stat1.execute("INSERT INTO temp_table SELECT itemID , itemName FROM mapDenormalize;");
            stat1.execute("DROP TABLE mapDenormalize;");
            stat1.execute("CREATE TABLE mapDenormalize(itemID int(11) NOT NULL, itemName varchar(100) default NULL, PRIMARY KEY  (itemID) );");
            stat1.execute("INSERT INTO mapDenormalize(itemID, itemName) SELECT itemID, itemName FROM temp_table;");
            stat1.execute("DROP TABLE temp_table;");

            //Remove Columns from mapRegions table
            stat1.execute("CREATE TEMPORARY TABLE temp_table(regionID int(11) NOT NULL, regionName varchar(100) default NULL, PRIMARY KEY  (regionID) );");
            stat1.execute("INSERT INTO temp_table SELECT regionID , regionName FROM mapRegions;");
            stat1.execute("DROP TABLE mapRegions;");
            stat1.execute("CREATE TABLE mapRegions(regionID int(11) NOT NULL, regionName varchar(100) default NULL, PRIMARY KEY  (regionID) );");
            stat1.execute("INSERT INTO mapRegions(regionID, regionName) SELECT regionID, regionName FROM temp_table;");
            stat1.execute("DROP TABLE temp_table;");

            //Remove Columns from mapConstellations table
            stat1.execute("CREATE TEMPORARY TABLE temp_table(regionID int(11) default NULL, constellationID int(11) NOT NULL, constellationName varchar(100) default NULL, PRIMARY KEY  (constellationID) );");
            stat1.execute("INSERT INTO temp_table SELECT regionID , constellationID, constellationName FROM mapConstellations;");
            stat1.execute("DROP TABLE mapConstellations;");
            stat1.execute("CREATE TABLE mapConstellations (regionID int(11) default NULL, constellationID int(11) NOT NULL, constellationName varchar(100) default NULL, PRIMARY KEY  (constellationID) );");
            stat1.execute("INSERT INTO mapConstellations(regionID , constellationID, constellationName) SELECT regionID , constellationID, constellationName FROM temp_table;");
            stat1.execute("DROP TABLE temp_table;");

            //Remove Columns from mapSolarSystems table
            stat1.execute("CREATE TEMPORARY TABLE temp_table(constellationID int(11) default NULL, solarSystemID int(11) NOT NULL, solarSystemName varchar(100) default NULL, security double default NULL, PRIMARY KEY (solarSystemID) );");
            stat1.execute("INSERT INTO temp_table SELECT constellationID , solarSystemID, solarSystemName, security FROM mapSolarSystems;");
            stat1.execute("DROP TABLE mapSolarSystems;");
            stat1.execute("CREATE TABLE mapSolarSystems(constellationID int(11) default NULL, solarSystemID int(11) NOT NULL, solarSystemName varchar(100) default NULL, security double default NULL, PRIMARY KEY (solarSystemID) );");
            stat1.execute("INSERT INTO mapSolarSystems(constellationID , solarSystemID, solarSystemName, security) SELECT constellationID , solarSystemID, solarSystemName, security FROM temp_table;");
            stat1.execute("DROP TABLE temp_table;");

            //Clean Database
            stat1.execute("VACUUM;");
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}