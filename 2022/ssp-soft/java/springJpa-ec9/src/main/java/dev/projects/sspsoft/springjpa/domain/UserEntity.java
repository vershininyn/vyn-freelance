package dev.projects.sspsoft.springjpa.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user", schema = "public")
public class UserEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user-sequence-generator")
    @SequenceGenerator(name = "user-sequence-generator", sequenceName = "user_sequence", allocationSize = 1, initialValue = 1)
    private Integer id;

    @Lob
    @Column(nullable = false)
    private String login;

    @Column(nullable = false, length = 256)
    private String email;

    @Column(nullable = false, length = 256)
    private String password;

    @Column(nullable = false)
    private Instant registrationDateAndTime;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(table = "user", name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(table = "role", name = "role_id", referencedColumnName = "id")
    )
    private Set<RoleEntity> roles = new LinkedHashSet<>();

    public UserEntity() {
    }

    public UserEntity(Integer id, String login, String email, String password, Instant registrationDateAndTime, Set<RoleEntity> roles) {
        this(login, email, password, registrationDateAndTime, roles);

        this.id = id;
    }

    public UserEntity(String login, String email, String password, Instant registrationDateAndTime, Set<RoleEntity> roles) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.registrationDateAndTime = registrationDateAndTime;
        this.roles = roles;
    }
    public Set<RoleEntity> getPublicUserRoles() {
        return roles;
    }

    public void setPublicUserRoles(Set<RoleEntity> publicUserRoles) {
        roles = publicUserRoles;
    }

    public Instant getRegistrationDateAndTime() {
        return registrationDateAndTime;
    }

    public void setRegistrationDateAndTime(Instant registrationDateAndTime) {
        this.registrationDateAndTime = registrationDateAndTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        return Objects.equals(id, that.id)
                && Objects.equals(login, that.login)
                && Objects.equals(email, that.email)
                && Objects.equals(password, that.password)
                && Objects.equals(registrationDateAndTime, that.registrationDateAndTime)
                && Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, email, password, registrationDateAndTime, roles);
    }
}