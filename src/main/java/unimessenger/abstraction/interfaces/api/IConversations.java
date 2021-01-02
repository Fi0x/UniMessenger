package unimessenger.abstraction.interfaces.api;

import java.net.http.HttpResponse;

public interface IConversations
{
    void requestAllConversations();
    void handleConversationResponse(HttpResponse<String> response);
}