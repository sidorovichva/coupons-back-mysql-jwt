package com.vs.couponsbackmysqljwt.Exceptions;

public enum ExpReason {
    COMPANY_ALREADY_EXISTS(new StringBuffer("company with such e-mail or name already exists")),
    COMPANY_ALREADY_EXISTS_ID(new StringBuffer("provided id number already exists")),
    COMPANY_ALREADY_EXISTS_BY_EMAIL(new StringBuffer("company with such e-mail already exists")),
    COMPANY_DOESNT_EXIST(new StringBuffer("company doesn't exist")),
    COMPANIES_WERENT_FOUND(new StringBuffer("no company found")),

    COUPON_ALREADY_EXISTS(new StringBuffer("coupon with such title already exists")),
    COUPON_DOESNT_EXIST(new StringBuffer("coupon doesn't exist")),
    COUPONS_WERENT_FOUND(new StringBuffer("no coupon found")),
    COUPON_NOT_AVAILABLE(new StringBuffer("this coupon isn't available")),

    CUSTOMER_ALREADY_EXISTS(new StringBuffer("customer with such e-mail already exists")),
    CUSTOMER_ALREADY_EXISTS_ID(new StringBuffer("provided id number already exists")),
    CUSTOMER_DOESNT_EXIST(new StringBuffer("customer doesn't exist")),
    CUSTOMERS_WERENT_FOUND(new StringBuffer("no customer found")),

    CATEGORY_ALREADY_EXISTS(new StringBuffer("category already exists")),
    CATEGORY_DOESNT_EXIST(new StringBuffer("category doesn't exist")),
    CATEGORIES_WERENT_FOUND(new StringBuffer("no category found")),

    //company creation
    FIELD_IS_EMPTY(new StringBuffer("all fields must be filled")),

    //coupon creation
    WRONG_AMOUNT(new StringBuffer("the amount of coupons must be at least 1")),
    TOO_LATE(new StringBuffer("start or end of promotion period of a new or an updated coupon can't start in the past")),
    EMPTY_FIELDS(new StringBuffer("title and description must be filled")),
    WRONG_PRICE(new StringBuffer("price must be more than 0")),

    //coupon creation and update related errors
    INSUFFICIENT_AMOUNT(new StringBuffer("sorry, all coupons are sold out")),
    TIME_IS_OVER(new StringBuffer("sorry, the promotion period for this coupon is over")),
    TOO_EARLY(new StringBuffer("sorry, the promotion period for this coupon hasn't started yet")),
    PURCHASE_EXISTS(new StringBuffer("you have already bought this coupon")),
    END_BEFORE_FINISH(new StringBuffer("end of promotion period can't start before the beginning")),
    WRONG_DAY(new StringBuffer("incorrect day input, must be between 1 and 28-31 included (depends on the month)")),
    WRONG_DAY_OF_MONTH(new StringBuffer("int this month there aren't so many days...")),
    LEAP_YEAR(new StringBuffer("the year is a leap year")),
    WRONG_MONTH(new StringBuffer("incorrect month input, must be between 1 and 12 included")),
    WRONG_YEAR(new StringBuffer("incorrect day input, must be between 2021 and 2071 included")),
    ZERO_OR_LESS(new StringBuffer("incorrect input, the number must be more than 0")),
    NEGATIVE_NUMBER(new StringBuffer("incorrect input, the number must be 0 or positive")),
    WRONG_INPUT_INT(new StringBuffer("incorrect input, must be an integer")),

    //general data related errors
    TEST_ENTITY(new StringBuffer("you can't delete test entities (id #1)")),
    NOTHING_FOUND(new StringBuffer("no data found")),
    LIST_LENGTH_ERROR(new StringBuffer("list wasn't generated")),
    WRONG_CHOICE(new StringBuffer("no such option available"));


    private StringBuffer text;
    private final StringBuffer beginning = new StringBuffer("Reason: ");

    ExpReason(StringBuffer message) {
        this.text = beginning.append(message);
    }

    public StringBuffer getText() {
        return text;
    }
}
