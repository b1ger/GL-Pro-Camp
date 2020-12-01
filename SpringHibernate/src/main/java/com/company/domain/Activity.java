package com.company.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "activities")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "work_name")
    private String workName;

    @Column
    private String measurement;

    @Column
    private double price;

    @Column
    private double amount;

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        if (Double.compare(activity.price, price) != 0) return false;
        if (Double.compare(activity.amount, amount) != 0) return false;
        if (id != null ? !id.equals(activity.id) : activity.id != null) return false;
        if (workName != null ? !workName.equals(activity.workName) : activity.workName != null) return false;
        if (measurement != null ? !measurement.equals(activity.measurement) : activity.measurement != null)
            return false;
        return building != null ? building.equals(activity.building) : activity.building == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (workName != null ? workName.hashCode() : 0);
        result = 31 * result + (measurement != null ? measurement.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (building != null ? building.hashCode() : 0);
        return result;
    }
}
