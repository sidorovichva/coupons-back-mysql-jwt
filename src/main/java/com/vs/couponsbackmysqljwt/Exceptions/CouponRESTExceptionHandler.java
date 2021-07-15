package com.vs.couponsbackmysqljwt.Exceptions;

import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
public class CouponRESTExceptionHandler extends Exception{
    private StringBuffer text;

    private final StringBuffer timeStamp = new StringBuffer(new Timestamp(System.currentTimeMillis()).toString());
    private final StringBuffer space = new StringBuffer(" ");
    private final StringBuffer beginning = new StringBuffer("Reason: ");

    public CouponRESTExceptionHandler(StringBuffer exception) {
        this.text = timeStamp.append(space).append(exception);
    }

    public CouponRESTExceptionHandler(StringBuffer exception, ExpReason reason) {
        this.text = timeStamp.append(space).append(exception).append(space).append(reason.getText());
    }

    public CouponRESTExceptionHandler(StringBuffer exception, String reason) {
        this.text = timeStamp.append(space).append(exception).append(space).append(beginning.append(reason));
    }

    public String getText() {
        return text.toString();
    }
}
