package com.androidsample.ygj.androidsample.models;

/**
 * Created by YGJ on 2015/10/13 0013.
 */
public class AjaxResult {
    private int Status;
    private Object Message;

    public int getStatus(){
        return Status;
    }

    public void setStatus(int status){
        this.Status = status;
    }

    public Object getMessage(){
        return this.Message;
    }

    public void setMessage(Object message){
        this.Message = message;
    }

    public AjaxResult(){
        this.Status = 0;
    }
}
