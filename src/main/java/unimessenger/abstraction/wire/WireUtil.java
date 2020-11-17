package unimessenger.abstraction.wire;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import unimessenger.abstraction.interfaces.IUtil;
import unimessenger.userinteraction.CLI;
import unimessenger.userinteraction.Outputs;
import unimessenger.util.Commands;
import unimessenger.util.Storage;
import unimessenger.util.Variables;

import java.net.http.HttpResponse;

public class WireUtil implements IUtil {

    public boolean refreshSession() {
        String url = Variables.URL_WIRE + Commands.ACCESS + "?access_token=" + Storage.wireBearerToken;
        String[] headers = new String[]{
                "cookie", Storage.wireAccessCookie,
                "content-type", "application/json",
                "accept", "application/json"};

        HttpResponse<String> response = CLI.userHTTP.sendRequest(url, Variables.REQUESTTYPE.POST, "", headers);

        if(response == null)
        {
            Outputs.printError("Couldn't get a HTTP response");
            return false;
        } else if(response.statusCode() == 200)
        {
            JSONObject obj;
            try
            {
                assert false;
                obj = (JSONObject) new JSONParser().parse(response.body());
                Storage.wireBearerToken = obj.get("access_token").toString();
                Storage.setWireBearerTime(Integer.parseInt(obj.get("expires_in").toString()));
                Outputs.printDebug("Successfully refreshed token");
            } catch(ParseException ignored)
            {
                Outputs.printError("Failed refreshing token");
                return false;
            }
            return true;
        } else
        {
            Outputs.printDebug("Response code is " + response.statusCode() + ". Deleting Wire access cookie...");
            Storage.wireAccessCookie = null;
            Storage.deleteFile(Storage.wireDataFile);
            return false;
        }
    }

}
