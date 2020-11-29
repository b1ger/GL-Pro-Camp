package com.company.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "reports")
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "buildings")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_name")
    private String name;

    @Column
    private double price;

    @Column(name = "order_date")
    private Date orderDate;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Building> buildings = new HashSet<>();

    public void addBuilding(Building building) {
        this.buildings.add(building);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report report = (Report) o;

        if (Double.compare(report.price, price) != 0) return false;
        if (id != null ? !id.equals(report.id) : report.id != null) return false;
        if (name != null ? !name.equals(report.name) : report.name != null) return false;
        if (orderDate != null ? !orderDate.equals(report.orderDate) : report.orderDate != null) return false;
        return user.equals(report.user);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
