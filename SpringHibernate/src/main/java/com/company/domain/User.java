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
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "reports")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String email;

    @Column(name = "email_backup")
    private String emailBackup;

    @Column(name = "TN")
    private String tn;

    @Column(name = "TN_backup")
    private String tnBackup;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Report> reports = new HashSet<>();

    public void addReport(Report report) {
        this.reports.add(report);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (!email.equals(user.email)) return false;
        if (emailBackup != null ? !emailBackup.equals(user.emailBackup) : user.emailBackup != null) return false;
        if (tn != null ? !tn.equals(user.tn) : user.tn != null) return false;
        return tnBackup != null ? tnBackup.equals(user.tnBackup) : user.tnBackup == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + email.hashCode();
        result = 31 * result + (emailBackup != null ? emailBackup.hashCode() : 0);
        result = 31 * result + (tn != null ? tn.hashCode() : 0);
        result = 31 * result + (tnBackup != null ? tnBackup.hashCode() : 0);
        return result;
    }
}
