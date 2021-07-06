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
 * Company object
 */
@Component
@Data
@Entity
@Table(name = "COMPANIES")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")
    @Column(nullable = false, name = "ID", updatable = false)
    private int id;

    @Column(nullable = false, length = 100, name = "NAME", unique = true)
    private String name;

    @Column(nullable = false, length = 100, name = "EMAIL", unique = true)
    private String email;

    @Column(nullable = false, length = 100, name = "PASSWORD")
    private String password;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Coupon> coupons = new ArrayList<>();

    public Company(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
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
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String details() {
        return this.toString() + "\n" + this.coupons;
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
//        import org.springframework.context.annotation.Lazy;
//        import org.springframework.stereotype.Component;
//
//        import javax.persistence.*;
//        import java.util.ArrayList;
//        import java.util.List;
//
///**
// * Company object
// */
//@Component
//@Data
//@Entity
//@Table(name = "COMPANIES")
//@NoArgsConstructor
//@AllArgsConstructor
//@Lazy
//@Builder
//@JsonIgnoreProperties("coupons")
//public class Company {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")
//    @Column(nullable = false, name = "ID", updatable = false)
//    private int id;
//
//    @Column(nullable = false, length = 100, name = "NAME", unique = true)
//    private String name;
//
//    @Column(nullable = false, length = 100, name = "EMAIL", unique = true)
//    private String email;
//
//    @Column(nullable = false, length = 100, name = "PASSWORD")
//    private String password;
//
//    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @JsonManagedReference
//    private List<Coupon> coupons = new ArrayList<>();
//
//    public Company(int id, String name, String email, String password) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.password = password;
//    }
//
//    @Override
//    public String toString() {
//        return "Company{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                '}';
//    }
//
//    public String details() {
//        return this.toString() + "\n" + this.coupons;
//    }
//}
