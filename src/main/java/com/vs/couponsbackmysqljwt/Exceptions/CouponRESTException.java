package com.vs.couponsbackmysqljwt.Exceptions;

public enum CouponRESTException {
    COMPANY_ADD(new StringBuffer("the company wasn't added"), new StringBuffer("the company was added successfully")),
    COMPANY_DELETE(new StringBuffer("the company wasn't deleted"), new StringBuffer("the company was deleted successfully")),
    COMPANY_UPDATE(new StringBuffer("the company wasn't updated"), new StringBuffer("the company was updated successfully")),
    COMPANY_GET(new StringBuffer("no companies found"), new StringBuffer("")),
    COMPANY_EXISTS(new StringBuffer("no companies found with such ID"), new StringBuffer("")),
    COMPANY_ALREADY_EXISTS(new StringBuffer("company with such e-mail or name already exists"), new StringBuffer("")),

    CUSTOMER_ADD(new StringBuffer("the customer wasn't added"), new StringBuffer("the customer was added successfully")),
    CUSTOMER_DELETE(new StringBuffer("the customer wasn't deleted"), new StringBuffer("the customer was deleted successfully")),
    CUSTOMER_UPDATE(new StringBuffer("the customer wasn't updated"), new StringBuffer("the customer was updated successfully")),
    CUSTOMER_GET(new StringBuffer("no customers found"), new StringBuffer("")),

    CATEGORY_ADD(new StringBuffer("the category wasn't added"), new StringBuffer("the category was added successfully")),
    CATEGORY_GET(new StringBuffer("no category found"), new StringBuffer("")),

    COUPON_ADD(new StringBuffer("the coupon wasn't added"), new StringBuffer("the coupon was added successfully")),
    COUPON_DELETE(new StringBuffer("the coupon wasn't deleted"), new StringBuffer("the coupon was deleted successfully")),
    COUPON_UPDATE(new StringBuffer("the coupon wasn't updated"), new StringBuffer("the coupon was updated successfully")),
    COUPON_GET(new StringBuffer("no coupon found"), new StringBuffer("")),

    PURCHASE_ADD(new StringBuffer("the purchase wasn't added"), new StringBuffer("the purchase was added successfully"));

    private StringBuffer failure;
    private StringBuffer success;

    private final StringBuffer failureBeginning = new StringBuffer("SYSTEM ERROR: ");
    private final StringBuffer successBeginning = new StringBuffer("NOTIFICATION: ");

    CouponRESTException(StringBuffer failure, StringBuffer success) {
        this.success = successBeginning.append(success);
        this.failure = failureBeginning.append(failure);
    }

    public StringBuffer getSuccess(){
        return this.success;
    }

    public StringBuffer getFailure(){
        return this.failure;
    }
}
