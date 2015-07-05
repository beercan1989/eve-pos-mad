package eveposmad;
import PosObjects.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * @author James Bacon
 */
public class EvePosMad_DatabaseTest
{
    EvePosMad_Database testDatabase;
    public EvePosMad_DatabaseTest(){}
    @BeforeClass public static void setUpClass() throws Exception {}
    @AfterClass public static void tearDownClass() throws Exception {}
    @Before public void setUp()   {testDatabase = new EvePosMad_Database();}
    @After public void tearDown() {}

    @Test public void testGetStaticInts()
    {
        System.out.println("Testing the static integers");
        assertTrue(EvePosMad_Database.AMMO_CRYSTAL==1);
        assertTrue(EvePosMad_Database.AMMO_HYBRID==2);
        assertTrue(EvePosMad_Database.AMMO_MISSILE==3);
        assertTrue(EvePosMad_Database.AMMO_PROJECTILE==4);
        assertTrue(EvePosMad_Database.STRUCTURE_ASSEMBLY_ARRAY==5);
        assertTrue(EvePosMad_Database.STRUCTURE_CORP_STORAGE==6);
        assertTrue(EvePosMad_Database.STRUCTURE_CYNO_TOOL==7);
        assertTrue(EvePosMad_Database.STRUCTURE_ENERGY_NEUT==8);
        assertTrue(EvePosMad_Database.STRUCTURE_JAMMER==9);
        assertTrue(EvePosMad_Database.STRUCTURE_JUMP_BRIDGE==10);
        assertTrue(EvePosMad_Database.STRUCTURE_MISSILE_BATTERY==11);
        assertTrue(EvePosMad_Database.STRUCTURE_MOBILE_LAB==12);
        assertTrue(EvePosMad_Database.STRUCTURE_MOON_MINER==13);
        assertTrue(EvePosMad_Database.STRUCTURE_REACTOR==14);
        assertTrue(EvePosMad_Database.STRUCTURE_REFINER==15);
        assertTrue(EvePosMad_Database.STRUCTURE_SENSOR_DAMP==16);
        assertTrue(EvePosMad_Database.STRUCTURE_SHIELD_HARDNER==17);
        assertTrue(EvePosMad_Database.STRUCTURE_SILO==18);
        assertTrue(EvePosMad_Database.STRUCTURE_STASIS_WEB==19);
        assertTrue(EvePosMad_Database.STRUCTURE_SCANNER==20);
        assertTrue(EvePosMad_Database.STRUCTURE_TURRET_BATTERY==21);
        assertTrue(EvePosMad_Database.STRUCTURE_WARP_INHIBITOR==22);
    }

    @Test public void testGetControlTower()
    {
        System.out.println("Testing the getControlTower() method");
        PosControlTower firstCT=testDatabase.getControlTower
                ("Caldari Control Tower");
        PosControlTower secondCT=testDatabase.getControlTower
                ("Shadow Control Tower Medium");
        PosControlTower thirdCT=testDatabase.getControlTower
                ("Dread Guristas Control Tower");
        assertNotNull(firstCT);
        assertNotNull(secondCT);
        assertNotNull(thirdCT);
        assertNotNull(firstCT.getFuelList());
        assertNotNull(firstCT.getTowerBonus());
        assertNotNull(secondCT.getFuelList());
        assertNotNull(secondCT.getTowerBonus());
        assertNotNull(thirdCT.getFuelList());
        assertNotNull(thirdCT.getTowerBonus());
        assertTrue(!firstCT.getFuelList().isEmpty());
        assertTrue(!secondCT.getFuelList().isEmpty());
        assertTrue(!thirdCT.getFuelList().isEmpty());
        assertTrue(!firstCT.getTowerBonus().isEmpty());
        assertTrue(!secondCT.getTowerBonus().isEmpty());
        assertTrue(!thirdCT.getTowerBonus().isEmpty());
        assertTrue(firstCT.getFuelList().size()==10);
        assertTrue(secondCT.getFuelList().size()==10);
        assertTrue(thirdCT.getFuelList().size()==10);
        assertTrue(firstCT.getTowerBonus().size()>=2);
        assertTrue(secondCT.getTowerBonus().size()>=2);
        assertTrue(thirdCT.getTowerBonus().size()>=2);

        assertTrue(secondCT.getActivationProximity()==250000);
        assertTrue(secondCT.getAnchoringDelay()==720000);
        assertTrue(secondCT.getArmorEmResistance()==0);
        assertTrue(secondCT.getArmorExplosiveResistance()==0);
        assertTrue(secondCT.getArmorHitpoints()==4800000);
        assertTrue(secondCT.getArmorKineticResistance()==0);
        assertTrue(secondCT.getArmorThermalResistance()==0);
        assertTrue(secondCT.getBaseprice()==200000000);
        assertTrue(secondCT.getControlTowerPeriod()==3600000);
        assertTrue(secondCT.getCpu()==3375);
        assertTrue(secondCT.getCurrentPrice()==0);
        assertTrue(secondCT.getDescription()!=null);
        assertTrue(secondCT.getFactionModule());
        assertTrue(secondCT.getMass()==1000000);
        assertTrue(secondCT.getMaxMoonAnchorDistance()==100000);
        assertTrue(secondCT.getMaxStructureDistance()==41215);
        assertTrue("Shadow Control Tower Medium".equals(secondCT.getName()));
        assertTrue(secondCT.getNormalFuelCapacity()==55000);
        assertTrue(secondCT.getOnliningDelay()==720000);
        assertTrue(!secondCT.getPlayerControlable());
        assertTrue(secondCT.getPowerGrid()==1875000);
    }

    @Test public void testGetPosStructure()
    {
        System.out.println("Testing the getPosStructure() method");
        try //Assembly Arrays Tests
        {
            System.out.println("\tTesting subsection 01: Assembly Arrays");

            PosAssemblyArray testOne =
             (PosAssemblyArray)testDatabase.getPosStructure
             (
                "Rapid Equipment Assembly Array",
                EvePosMad_Database.STRUCTURE_ASSEMBLY_ARRAY
             );

            PosAssemblyArray testTwo =
             (PosAssemblyArray)testDatabase.getPosStructure
             (
                "Capital Ship Assembly Array",
                EvePosMad_Database.STRUCTURE_ASSEMBLY_ARRAY
             );

            PosAssemblyArray testThree =
             (PosAssemblyArray)testDatabase.getPosStructure
             (
                "Small Ship Assembly Array",
                EvePosMad_Database.STRUCTURE_ASSEMBLY_ARRAY
             );

            assertNotNull(testOne);
            assertNotNull(testTwo);
            assertNotNull(testThree);
            assertTrue(testOne.isSovereigntyRequired()==false);
            assertTrue(testTwo.isSovereigntyRequired());
            assertTrue(testThree.isSovereigntyRequired()==false);
            assertTrue(testOne.getManufacturingSlots()==5);
            assertTrue(testOne.getBaseTimeModifier()==0.65);
            assertTrue(testOne.getBaseMaterialModifier()==1.2);
            assertTrue(testTwo.getManufacturingSlots()==1);
            assertTrue(testTwo.getBaseTimeModifier()==0);
            assertTrue(testTwo.getBaseMaterialModifier()==0);
            assertTrue(testThree.getManufacturingSlots()==10);
            assertTrue(testThree.getBaseTimeModifier()==0.75);
            assertTrue(testThree.getBaseMaterialModifier()==1);
            assertTrue(testOne.getMaxOperationalDistance()==3000);
            assertTrue(testTwo.getMaxOperationalDistance()==3000);
            assertTrue(testThree.getMaxOperationalDistance()==3000);
            assertTrue(testOne.getMaxConcurrentOperationalUsers()==1);
            assertTrue(testTwo.getMaxConcurrentOperationalUsers()==1);
            assertTrue(testThree.getMaxConcurrentOperationalUsers()==1);
        }
        catch(Exception e)
        {
            fail("Somthing went wrong in the: Assembly Arrays Tests");
        }
        try //Corporation Storage Tests
        {
            System.out.println("\tTesting subsection 02: Corporation Storage");
            PosCorporationStorage testFour =
              (PosCorporationStorage) testDatabase.getPosStructure
              (
                "Ship Maintenance Array",
                EvePosMad_Database.STRUCTURE_CORP_STORAGE
              );

            PosCorporationStorage testFive =
              (PosCorporationStorage) testDatabase.getPosStructure
              (
                  "Capital Ship Maintenance Array",
                  EvePosMad_Database.STRUCTURE_CORP_STORAGE
              );

            PosCorporationStorage testSix =
              (PosCorporationStorage) testDatabase.getPosStructure
              (
                  "Corporate Hangar Array",
                  EvePosMad_Database.STRUCTURE_CORP_STORAGE
              );

            assertNotNull(testFour);
            assertNotNull(testFive);
            assertNotNull(testSix);
            assertTrue(testFour.isSovereigntyRequired()==false);
            assertTrue(testFive.isSovereigntyRequired());
            assertTrue(testSix.isSovereigntyRequired()==false);
            assertTrue("CorporationStorage".equals(testFour.getType()));
            assertTrue(testFour.getBaseprice()==20000000);
            assertTrue("Ship Maintenance Array".equals(testFour.getName()));
            assertTrue(testFive.getRequiredCPU()==0);
            assertTrue(testFive.getRequiredPowerGrid()==1000000);
            assertTrue(testFive.getVolume()==40000);
            assertTrue(testSix.getCurrentPrice()==0);
            assertTrue(testSix.getFactionModule()==false);
            assertTrue(testSix.getUnanchoringDelay()==600000);

        }
        catch(Exception e)
        {
            fail("Somthing went wrong in the: Assembly Arrays Tests");
        }
    }

    @Test public void testGetPosAmmo()
    {
        System.out.println("Testing the getPosAmmo() method");

        PosAmmoCrystal testAC = (PosAmmoCrystal) testDatabase.getPosAmmo
        ("BLOOD MULTIFREQUENCY XL", EvePosMad_Database.AMMO_CRYSTAL);

        PosAmmoHybrid testAH = (PosAmmoHybrid) testDatabase.getPosAmmo
        ("THORIUM CHARGE M", EvePosMad_Database.AMMO_HYBRID);

        PosAmmoMissile testAM = (PosAmmoMissile) testDatabase.getPosAmmo
        ("RIFT CITADEL TORPEDO", EvePosMad_Database.AMMO_MISSILE);

        PosAmmoProjectile testAP = (PosAmmoProjectile) testDatabase.getPosAmmo
        ("REPUBLIC FLEET NUCLEAR L", EvePosMad_Database.AMMO_PROJECTILE);

        assertNotNull(testAC);
        assertNotNull(testAH);
        assertNotNull(testAM);
        assertNotNull(testAP);

        assertTrue(testAC.getFaction());
        assertTrue(testAC.getCapacitorBonus()==0);
        assertTrue(Integer.parseInt(testAC.getChargeSize())==4);

        assertTrue(testAH.getBaseprice()==2250);
        assertTrue(testAH.getKineticDamage()==10);
        assertTrue(testAH.getRangeBonus()==(1-(12.5/100)));

        assertTrue(testAM.getMaximumFlightTime()==15000);
        assertTrue(testAM.getMaximumVelocity()==1750);
        assertTrue(testAM.getKineticDamage()==2000);

        assertTrue(testAP.getMass()==1);
        assertTrue(testAP.getVolume()==0.025);
        assertTrue(testAP.getCurrentPrice()==0);
    }

    @Test public void testGetLocationDetails()
    {
        System.out.println("Testing the getLocationDetails method");
        String[] results1 = testDatabase.getLocationDetails(40154719L,30002432L);
        String[] results2 = testDatabase.getLocationDetails(40165970L,30002602L);
        String[] results3 = testDatabase.getLocationDetails(0L, 0L);
        String[] results4 = testDatabase.getLocationDetails(40357642L,31000138L);

        assertNotNull(results1);
        assertTrue("YN3-E3 IX - Moon 1".equals(results1[0]));
        assertTrue("-0.0716111529575163".equals(results1[1]));
        assertTrue("UBPU-9".equals(results1[2]));
        assertTrue("Geminate".equals(results1[3]));
        assertTrue("Normal Space".equals(results1[4]));

        assertNotNull(results2);
        assertTrue("O2-39S VIII - Moon 7".equals(results2[0]));
        assertTrue("-0.27424458025256".equals(results2[1]));
        assertTrue("1A-WYQ".equals(results2[2]));
        assertTrue("Impass".equals(results2[3]));
        assertTrue("Normal Space".equals(results2[4]));

        assertNotNull(results3);
        assertNull(results3[0]);
        assertNull(results3[1]);
        assertNull(results3[2]);
        assertNull(results3[3]);
        assertNull(results3[4]);

        assertNotNull(results4);
        assertTrue("J123111 III - Moon 1".equals(results4[0]));
        assertTrue("-0.99".equals(results4[1]));
        assertTrue("Unknown".equals(results4[2]));
        assertTrue("Unknown".equals(results4[3]));
        assertTrue("Wormhole Space".equals(results4[4]));
    }
}