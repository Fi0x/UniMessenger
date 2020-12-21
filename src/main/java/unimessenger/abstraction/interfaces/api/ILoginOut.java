package unimessenger.abstraction.interfaces.api;

public interface ILoginOut
{
    boolean checkIfLoggedIn();
    boolean login();
    boolean login(String email, String password);
    boolean logout();
    boolean needsRefresh();
}