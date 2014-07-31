package com.app.injectedIn;

import com.app.injectees.Injectable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * Created by korena on 7/25/14.
 */
@SessionScoped
public class ReceiveInjection implements Serializable {

    @Inject Injectable injectable;
    private String sign;




    public ReceiveInjection() {
        this.sign = "I am here!";
    }

    public String useInjected(){
        if (injectable == null) return "injectable is null";
       return injectable.method();
    }

    public String getSign() {
        return sign;
    }
}
