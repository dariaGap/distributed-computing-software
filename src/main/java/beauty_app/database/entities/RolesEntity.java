package beauty_app.database.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles", schema = "public", catalog = "beauty")
public class RolesEntity implements GrantedAuthority {
    private int id;
    private String roleName;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "role_name", nullable = false, length = -1)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Transient
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolesEntity that = (RolesEntity) o;
        return id == that.id &&
                Objects.equals(roleName, that.roleName);
    }

    @Transient
    @Override
    public int hashCode() {
        return Objects.hash(id, roleName);
    }

    @Transient
    @Override
    public String getAuthority() {
        return getRoleName();
    }
}
