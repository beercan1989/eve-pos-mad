package eveposmad;
import com.beimin.eveapi.EveApi;
import com.beimin.eveapi.connectors.ApiConnector;
import com.beimin.eveapi.account.characters.ApiCharacter;
import com.beimin.eveapi.account.characters.CharactersResponse;
import com.beimin.eveapi.corporation.starbase.detail.StarbaseDetailResponse;
import com.beimin.eveapi.corporation.starbase.list.StarbaseListResponse;
import com.beimin.eveapi.corporation.starbase.list.ApiStarbase;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * @author James Bacon
 */
public class EvePosMad_ApiTest
{
    private String apiKey = "bSfo9G8dBzG1WDyz4ayX5Rvbf2kc5aa3IW1mC5o2mOtaUognT184hIzgkUgxCQie";
    private long charID = 199987967L;
    private int userID = 791789;
    private long stationID = 1001959373231L;

    public EvePosMad_ApiTest(){}
    @BeforeClass public static void setUpClass() throws Exception{
        EveApi.setConnector(new ApiConnector("http://apitest.eveonline.com")); }
    @AfterClass public static void tearDownClass() throws Exception{
        EveApi.setConnector(new ApiConnector()); }
    @Before public void setUp() { }
    @After public void tearDown() { }

    @Test public void testGetAccountCharacters() {
        System.out.println("Testing the getAccountCharacters() static method");
        CharactersResponse result = EvePosMad_Api.getAccountCharacters(apiKey, userID);
        assertNotNull(result);
        ArrayList<ApiCharacter> resultArrayList = (ArrayList<ApiCharacter>)result.getEveCharacters();
        assertTrue("Kael Urui".equals(resultArrayList.get(0).getName()));
        assertTrue("Huoriel Sulime".equals(resultArrayList.get(1).getName()));
        assertTrue("Galadhwen Urui".equals(resultArrayList.get(2).getName()));
    }
    @Test public void testGetStarbaseList() {
        System.out.println("Testing the getStarbaseList() static method");
        StarbaseListResponse result = EvePosMad_Api.getStarbaseList(apiKey, charID, userID);
        assertNotNull(result);
        ArrayList<ApiStarbase> resultArrayList = new ArrayList<ApiStarbase>();
        resultArrayList.addAll(result.getStarbases());
        assertTrue(resultArrayList.get(0).getItemID() == stationID);
        assertTrue(resultArrayList.get(0).getTypeID() == 16213);
        assertTrue(resultArrayList.get(0).getState() == 4);
    }
    @Test public void testGetStarbaseDetail() {
        System.out.println("Testing the getStarbaseDetail() static method");
        StarbaseDetailResponse result = EvePosMad_Api.getStarbaseDetail(apiKey, charID, userID, stationID);
        assertNotNull(result);
        assertTrue(!result.getOnCorporationWar().isEnabled());
        assertTrue(!result.getOnAggression().isEnabled());
        assertTrue(!result.getFuelMap().values().isEmpty());
    }
}