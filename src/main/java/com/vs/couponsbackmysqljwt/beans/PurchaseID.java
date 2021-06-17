package com.vs.couponsbackmysqljwt.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Component
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseID implements Serializable {

    private int couponID;
    private int customerID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseID that = (PurchaseID) o;
        return couponID == that.couponID && customerID == that.customerID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(couponID, customerID);
    }
}
