package com.company.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
import java.util.HashSet;
import java.util.Set;

@Entity(name = "buildings")
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "activities")
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "building_name")
    private String name;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Activity> activities = new HashSet<>();

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Building building = (Building) o;

        if (isActive != building.isActive) return false;
        if (id != null ? !id.equals(building.id) : building.id != null) return false;
        if (name != null ? !name.equals(building.name) : building.name != null) return false;
        return report != null ? report.equals(building.report) : building.report == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (report != null ? report.hashCode() : 0);
        return result;
    }
}
