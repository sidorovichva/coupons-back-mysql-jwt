package com.vs.couponsbackmysqljwt.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer object
 */
@Component
@Data
@Entity
@Table(name = "CUSTOMERS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")
    @Column(nullable = false, name = "ID", updatable = false)
    private int id;

    @Column(nullable = false, length = 100, name = "FIRST_NAME")
    private String firstName;

    @Column(nullable = false, length = 100, name = "LAST_NAME")
    private String lastName;

    @Column(nullable = false, length = 100, name = "EMAIL", unique = true)
    private String email;

    @Column(nullable = false, length = 100, name = "PASSWORD")
    private String password;

    //@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Purchase> purchases = new ArrayList<>();

    public Customer(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String details() {
        return this.toString() + "\n" + this.purchases;
    }
}

//package com.vs.couponsbackmysqljwt.beans;
//
//        import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//        import com.fasterxml.jackson.annotation.JsonManagedReference;
//        import lombok.AllArgsConstructor;
//        import lombok.Builder;
//        import lombok.Data;
//        import lombok.NoArgsConstructor;
//        import org.springframework.stereotype.Component;
//
//        import javax.persistence.*;
//        import java.util.ArrayList;
//        import java.util.List;
//
///**
// * Customer object
// */
//@Component
//@Data
//@Entity
//@Table(name = "CUSTOMERS")
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@JsonIgnoreProperties("purchases")
//public class Customer {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")
//    @Column(nullable = false, name = "ID", updatable = false)
//    private int id;
//
//    @Column(nullable = false, length = 100, name = "FIRST_NAME")
//    private String firstName;
//
//    @Column(nullable = false, length = 100, name = "LAST_NAME")
//    private String lastName;
//
//    @Column(nullable = false, length = 100, name = "EMAIL", unique = true)
//    private String email;
//
//    @Column(nullable = false, length = 100, name = "PASSWORD")
//    private String password;
//
//    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @JsonManagedReference
//    private List<Purchase> purchases = new ArrayList<>();
//
//    public Customer(int id, String firstName, String lastName, String email, String password) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.password = password;
//    }
//
//    @Override
//    public String toString() {
//        return "Customer{" +
//                "id=" + id +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                '}';
//    }
//
//    public String details() {
//        return this.toString() + "\n" + this.purchases;
//    }
//}
