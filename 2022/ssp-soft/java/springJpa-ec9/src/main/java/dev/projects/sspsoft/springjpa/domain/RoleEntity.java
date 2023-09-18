package dev.projects.sspsoft.springjpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "role", schema = "public")
public class RoleEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role-sequence-generator")
    @SequenceGenerator(name = "role-sequence-generator", sequenceName = "role_sequence", allocationSize = 1, initialValue = 1)
    private Integer id;

    @Column(nullable = false, length = 256)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}