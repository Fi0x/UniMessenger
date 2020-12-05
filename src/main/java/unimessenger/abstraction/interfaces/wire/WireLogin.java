package unimessenger.abstraction.interfaces.wire;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import unimessenger.abstraction.Headers;
import unimessenger.abstraction.URL;
import unimessenger.abstraction.interfaces.ILoginOut;
import unimessenger.abstraction.storage.WireStorage;
import unimessenger.communication.HTTP;
import unimessenger.userinteraction.tui.Inputs;
import unimessenger.userinteraction.tui.Outputs;
import unimessenger.util.enums.REQUEST;

import java.net.http.HttpResponse;

public class WireLogin implements ILoginOut
{
    @Override
    public boolean checkIfLoggedIn()
    {
        if(WireStorage.cookie == null)
        {
            Outputs.create("No cookie stored").verbose().INFO().print();
            return false;
        }
        if(WireStorage.isBearerTokenStillValid())
        {
            Outputs.create("Bearer token is valid").verbose().INFO().print();
            return true;
        }
        Outputs.create("Bearer token not valid", this.getClass().getName()).debug().INFO().print();
        return false;
    }

    @Override
    public boolean login()
    {
        //TODO: Add more login options (phone)
        String mail = Inputs.getStringAnswerFrom("Please enter your E-Mail");//TestAccount: pechtl97@gmail.com
        String pw = Inputs.getStringAnswerFrom("Please enter your password");//TestAccount: Passwort1!
        WireStorage.persistent = Inputs.getBoolAnswerFrom("Do you want to stay logged in?");
        return login(mail, pw);
    }
    @Override
    public boolean login(String mail, String pw)
    {
        String url = URL.WIRE + URL.WIRE_LOGIN;
        if(WireStorage.persistent) url += URL.WIRE_PERSIST;

        JSONObject obj = new JSONObject();
        obj.put("email", mail);
        obj.put("password", pw);
        String body = obj.toJSONString();

        String[] headers = new String[]{
                Headers.CONTENT, Headers.JSON,
                Headers.ACCEPT, Headers.JSON};

        return handleResponse(new HTTP().sendRequest(url, REQUEST.POST, body, headers));
    }

    @Override
    public boolean logout()
    {
        String url = URL.WIRE + URL.WIRE_LOGOUT + URL.wireBearerToken();
        String[] headers = new String[]{
                "cookie", WireStorage.cookie,
                Headers.CONTENT, Headers.JSON,
                Headers.ACCEPT, Headers.JSON};

        HttpResponse<String> response = new HTTP().sendRequest(url, REQUEST.POST, "", headers);

        if(response == null)
        {
            Outputs.create("Could not get a HTTP response", this.getClass().getName()).debug().WARNING().print();
            return false;
        } else if(response.statusCode() == 200)
        {
            Outputs.create("Successfully logged out").verbose().INFO().print();
            WireStorage.clearUserData();
            return true;
        } else
        {
            Outputs.create("Response code is " + response.statusCode(), this.getClass().getName()).debug().WARNING().print();
            return false;
        }
    }

    @Override
    public boolean needsRefresh()
    {
        WireStorage.isBearerTokenStillValid();
        return false;
    }

    private boolean handleResponse(HttpResponse<String> response)
    {
        if(response == null || response.statusCode() != 200) return false;

        JSONObject obj;
        try
        {
            obj = (JSONObject) new JSONParser().parse(response.body());
            WireStorage.userID = obj.get("user").toString();
            WireStorage.setBearerToken(obj.get("access_token").toString(), Integer.parseInt(obj.get("expires_in").toString()));

            String cookieArr = response.headers().map().get("set-cookie").get(0);
            String[] arr = cookieArr.split("zuid=");
            if(arr.length > 1) arr = arr[1].split(";");
            WireStorage.cookie = "zuid=" + arr[0];

            Outputs.create("User: " + WireStorage.userID).verbose().INFO().print();
            Outputs.create("Expires in: " + obj.get("expires_in") + " seconds").verbose().INFO().print();
        } catch(ParseException ignored)
        {
            return false;
        }
        return true;
    }
}