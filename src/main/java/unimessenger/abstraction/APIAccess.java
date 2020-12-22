package unimessenger.abstraction;

import unimessenger.abstraction.interfaces.api.IConversations;
import unimessenger.abstraction.interfaces.api.ILoginOut;
import unimessenger.abstraction.interfaces.api.IMessages;
import unimessenger.abstraction.interfaces.api.IUtil;
import unimessenger.abstraction.interfaces.storage.IData;
import unimessenger.abstraction.wire.api.*;
import unimessenger.userinteraction.tui.Out;
import unimessenger.util.enums.SERVICE;

public class APIAccess
{
    private final IConversations WIRE_CON = new WireConversations();
    private final ILoginOut WIRE_LOGIN = new WireLogin();
    private final IMessages WIRE_MESSAGES = new WireMessages();
    private final IUtil WIRE_UTIL = new WireUtil();
    private final IData WIRE_DATA = new WireData();

    public IConversations getConversationInterface(SERVICE service)
    {
        if(service == null)
        {
            Out.newBuilder("No service for interface provided").origin(this.getClass().getName()).d().WARNING().print();
            return null;
        }
        switch(service)
        {
            case WIRE:
                return WIRE_CON;
            case TELEGRAM:
                Out.newBuilder("Conversation interface not implemented yet").origin(this.getClass().getName()).a().ERROR().print();
                return null;
            case NONE:
            default:
                return null;
        }
    }
    public ILoginOut getLoginInterface(SERVICE service)
    {
        if(service == null)
        {
            Out.newBuilder("No service for interface provided").origin(this.getClass().getName()).d().WARNING().print();
            return null;
        }
        switch(service)
        {
            case WIRE:
                return WIRE_LOGIN;
            case TELEGRAM:
                Out.newBuilder("Login interface not implemented yet").origin(this.getClass().getName()).a().ERROR().print();
                return null;
            case NONE:
            default:
                return null;
        }
    }
    public IMessages getMessageInterface(SERVICE service)
    {
        if(service == null)
        {
            Out.newBuilder("No service for interface provided").origin(this.getClass().getName()).d().WARNING().print();
            return null;
        }
        switch(service)
        {
            case WIRE:
                return WIRE_MESSAGES;
            case TELEGRAM:
                Out.newBuilder("Messages interface not implemented yet").origin(this.getClass().getName()).a().ERROR().print();
                return null;
            case NONE:
            default:
                return null;
        }
    }
    public IUtil getUtilInterface(SERVICE service)
    {
        if(service == null)
        {
            Out.newBuilder("No service for interface provided").origin(this.getClass().getName()).d().WARNING().print();
            return null;
        }
        switch(service)
        {
            case WIRE:
                return WIRE_UTIL;
            case TELEGRAM:
                Out.newBuilder("Util interface not implemented yet").origin(this.getClass().getName()).a().ERROR().print();
                return null;
            case NONE:
            default:
                return null;
        }
    }
    public IData getDataInterface(SERVICE service)
    {
        if(service == null)
        {
            Out.newBuilder("No service for interface provided").origin(this.getClass().getName()).d().WARNING().print();
            return null;
        }
        switch(service)
        {
            case WIRE:
                return WIRE_DATA;
            case TELEGRAM:
                Out.newBuilder("Data interface not implemented yet").origin(this.getClass().getName()).a().ERROR().print();
                return null;
            case NONE:
            default:
                return null;
        }
    }
}