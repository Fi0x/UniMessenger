package unimessenger.abstraction.wire.structures;

import java.io.Serializable;

public class WirePerson implements Serializable
{
    public String hidden_ref;
    public int status;
    public String service;
    public String otr_muted_ref;
    public String conversation_role;
    public String status_time;
    public boolean hidden;
    public String status_ref;
    public String id;
    public boolean otr_archived;
    public String otr_muted_status;
    public boolean otr_muted;
    public String otr_archived_ref;

    public WirePerson()
    {
        status = -1;
        hidden = false;
        otr_archived = false;
        otr_muted = false;
    }
}