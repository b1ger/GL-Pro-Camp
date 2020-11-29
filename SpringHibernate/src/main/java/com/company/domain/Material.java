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

@Entity(name = "materials")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "material")
    private String name;

    @Column
    private Double price;

    @Column
    private String supplier;

    @Column
    private String measurement;

    @Column
    private Long balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Material material = (Material) o;

        if (id != null ? !id.equals(material.id) : material.id != null) return false;
        if (name != null ? !name.equals(material.name) : material.name != null) return false;
        if (price != null ? !price.equals(material.price) : material.price != null) return false;
        if (supplier != null ? !supplier.equals(material.supplier) : material.supplier != null) return false;
        if (measurement != null ? !measurement.equals(material.measurement) : material.measurement != null)
            return false;
        return balance != null ? balance.equals(material.balance) : material.balance == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (supplier != null ? supplier.hashCode() : 0);
        result = 31 * result + (measurement != null ? measurement.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }
}
