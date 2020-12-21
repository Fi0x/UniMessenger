package unimessenger.abstraction.wire.api;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import unimessenger.abstraction.Headers;
import unimessenger.abstraction.URL;
import unimessenger.abstraction.interfaces.api.ILoginOut;
import unimessenger.abstraction.wire.storage.Storage;
import unimessenger.communication.HTTP;
import unimessenger.userinteraction.tui.Inputs;
import unimessenger.userinteraction.tui.Out;
import unimessenger.util.enums.REQUEST;

import java.net.http.HttpResponse;

public class WireLogin implements ILoginOut
{
    @Override
    public boolean checkIfLoggedIn()
    {
        if(Storage.cookie == null)
        {
            Out.newBuilder("No cookie stored; User is not logged in").d().print();
            return false;
        }
        if(Storage.getInstance().isBearerTokenStillValid())
        {
            Out.newBuilder("Bearer token is valid").print();
            return true;
        }
        Out.newBuilder("Bearer token not valid; User is not logged in").origin(this.getClass().getName()).d().print();
        return false;
    }

    @Override
    public boolean login()
    {
        //TODO: Add more login options (phone)
        String mail = Inputs.getStringAnswerFrom("Please enter your E-Mail");//TestAccount: pechtl97@gmail.com
        String pw = Inputs.getStringAnswerFrom("Please enter your password");//TestAccount: Passwort1!
        Storage.persistent = Inputs.getBoolAnswerFrom("Do you want to stay logged in?");
        return login(mail, pw);
    }
    @Override
    public boolean login(String mail, String pw)
    {
        String url = URL.WIRE + URL.WIRE_LOGIN;
        if(Storage.persistent) url += URL.WIRE_PERSIST;

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
                "cookie", Storage.cookie,
                Headers.CONTENT, Headers.JSON,
                Headers.ACCEPT, Headers.JSON};

        HttpResponse<String> response = new HTTP().sendRequest(url, REQUEST.POST, "", headers);

        if(response == null)
        {
            Out.newBuilder("Could not get a HTTP response to logout").origin(this.getClass().getName()).d().WARNING().print();
            return false;
        } else if(response.statusCode() == 200)
        {
            Out.newBuilder("Successfully logged out of Wire").v().print();
            Storage.getInstance().clearUserData();
            return true;
        } else
        {
            Out.newBuilder("Response code from logout is " + response.statusCode()).origin(this.getClass().getName()).d().WARNING().print();
            return false;
        }
    }

    @Override
    public boolean needsRefresh()
    {
        Storage.getInstance().isBearerTokenStillValid();
        return false;
    }

    private boolean handleResponse(HttpResponse<String> response)
    {
        if(response == null || response.statusCode() != 200) return false;

        JSONObject obj;
        try
        {
            obj = (JSONObject) new JSONParser().parse(response.body());
            Storage.userID = obj.get("user").toString();
            Storage.getInstance().setBearerToken(obj.get("access_token").toString(), Integer.parseInt(obj.get("expires_in").toString()));

            String cookieArr = response.headers().map().get("set-cookie").get(0);
            String[] arr = cookieArr.split("zuid=");
            if(arr.length > 1) arr = arr[1].split(";");
            Storage.cookie = "zuid=" + arr[0];

            Out.newBuilder("User: " + Storage.userID).vv().print();
            Out.newBuilder("Expires in: " + obj.get("expires_in") + " seconds").vv().print();
        } catch(ParseException ignored)
        {
            return false;
        }
        return true;
    }
}