package unimessenger.abstraction.wire.crypto;

import com.waz.model.Messages;
import unimessenger.abstraction.Headers;
import unimessenger.abstraction.URL;
import unimessenger.abstraction.wire.messages.*;
import unimessenger.communication.HTTP;
import unimessenger.userinteraction.Outputs;
import unimessenger.util.enums.REQUEST;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

public class MessageCreator
{
    public static Messages.GenericMessage createGenericTextMessage(String text)
    {
        MessageText msg = new MessageText(text);
        return msg.createGenericMsg();
    }

    public static Messages.GenericMessage createGenericPingMessage()
    {
        Ping msg = new Ping();
        return msg.createGenericMsg();
    }

    public static Messages.GenericMessage createGenericFilePreviewMessage(File file, UUID id)
    {
        String mimeType = "image/png";//TODO: Set correct mime type
        FileAssetPreview preview = new FileAssetPreview(file.getName(), mimeType, file.length(), id);
        return preview.createGenericMsg();
    }
    public static Messages.GenericMessage createGenericFileMessage(File file, UUID id)
    {
        try
        {
            String mimeType = "image/png";//TODO: Set correct mime type
            FileAsset asset = new FileAsset(file, mimeType, id);

            AssetKey ak = uploadAsset(asset);
            if(ak == null) return null;

            asset.setAssetKey(ak.key);
            asset.setAssetToken(ak.token);

            return asset.createGenericMsg();
        } catch(Exception ignored)
        {
            Outputs.create("Could not create FileAsset", "MessageCreator").verbose().WARNING().print();
        }
        return null;
    }


    /**
    Code from lithium
    **/
    private static AssetKey uploadAsset(IAsset asset) throws Exception
    {
        StringBuilder sb = new StringBuilder();

        // Part 1
        String strMetadata = String.format("{\"public\": %s, \"retention\": \"%s\"}", asset.isPublic(), asset.getRetention());
        sb.append("--frontier\r\n");
        sb.append("Content-Type: application/json; charset=utf-8\r\n");
        sb.append("Content-Length: ").append(strMetadata.length()).append("\r\n\r\n");
        sb.append(strMetadata).append("\r\n");

        // Part 2
        sb.append("--frontier\r\n");
        sb.append("Content-Type: ").append(asset.getMimeType()).append("\r\n");
        sb.append("Content-Length: ").append(asset.getEncryptedData().length).append("\r\n");
        sb.append("Content-MD5: ").append(Util.calcMd5(asset.getEncryptedData())).append("\r\n\r\n");

        // Complete
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        os.write(asset.getEncryptedData());
        os.write("\r\n--frontier--\r\n".getBytes(StandardCharsets.UTF_8));

//        Response res = assets.post(Entity.entity(os.toByteArray(), "multipart/mixed; boundary=frontier"));



        String url = URL.WIRE + URL.WIRE_ASSETS + URL.wireBearerToken();//TODO: Create correct request to upload asset
        String[] headers = new String[]{
                Headers.CONTENT, Headers.MIXED + "; boundary=frontier",
                Headers.ACCEPT, Headers.JSON};
        String body = Arrays.toString(os.toByteArray());

        HttpResponse<String> response = new HTTP().sendRequest(url, REQUEST.POST, body, headers);

        if(response == null) Outputs.create("No HTTP response received", "MessagesCreator").debug().INFO().print();
        else if(response.statusCode() == 200 || response.statusCode() == 201)
        {
            System.out.println("Successfully uploaded asset");
            return keyFromResponse(response);
        } else Outputs.create("Response code is " + response.statusCode()).verbose().WARNING().print();

        System.out.println("Body: " + response.body());

        return null;
    }
    private static AssetKey keyFromResponse(HttpResponse<String> response)//TODO: Get correct asset key from upload
    {
//        return res.readEntity(AssetKey.class);
        return null;
    }
}