package eveposmad;
import com.beimin.eveapi.EveApi;
import com.beimin.eveapi.core.*;
import com.beimin.eveapi.account.characters.*;
import com.beimin.eveapi.connectors.ApiConnector;
import com.beimin.eveapi.corporation.starbase.list.*;
import com.beimin.eveapi.corporation.starbase.detail.*;
import com.beimin.eveapi.core.ApiException;
/**
 * @author James Bacon
 */
public class EvePosMad_Api
{
    public static CharactersResponse getAccountCharacters(String apiKey,
            int userID){
        EveApi.setConnector(new ApiConnector("http://apitest.eveonline.com")); //For Tests
        CharactersParser charParser = new CharactersParser();
        CharactersResponse charResponse = new CharactersResponse();
        try
        {
            charResponse = charParser.getResponse
            (
                new ApiAuthorization(userID, 0, apiKey)
            );
        }
        catch(ApiException error) { return null; }
        return charResponse;
    }

    public static StarbaseListResponse getStarbaseList(String apiKey,
            long charID, int userID){
        EveApi.setConnector(new ApiConnector("http://apitest.eveonline.com")); //For Tests
        StarbaseListParser stationParser = new StarbaseListParser();
        StarbaseListResponse stationResponse = new StarbaseListResponse();
        try
        {
            stationResponse = stationParser.getResponse
            (
                new ApiAuthorization(userID, charID, apiKey)
            );
        }
        catch(ApiException error) { return null; }
        return stationResponse;
    }

    public static StarbaseDetailResponse getStarbaseDetail(String apiKey,
            long charID, int userID, long stationID){
        EveApi.setConnector(new ApiConnector("http://apitest.eveonline.com")); //For Tests
        StarbaseDetailParser stationDetailParser = new StarbaseDetailParser();
        StarbaseDetailResponse stationDetailResponse = new StarbaseDetailResponse();
        try
        {
            stationDetailResponse = stationDetailParser.getResponse
            (
                new ApiAuthorization(userID, charID, apiKey),
                stationID
            );
        }
        catch(ApiException error) { return null; }
        return stationDetailResponse;
    }
}