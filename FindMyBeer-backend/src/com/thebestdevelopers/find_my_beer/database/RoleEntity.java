/**
 * @author Wiktor Florencki
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "role", schema = "main", catalog = "d86n3p8h6i057d")
public class RoleEntity {
    private int userId;
    private String role;

    @javax.persistence.Id
    @javax.persistence.Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "role")
    public java.lang.String getRole() {
        return role;
    }

    public void setRole(java.lang.String role) {
        this.role = role;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        RoleEntity that = (RoleEntity) object;

        if (userId != that.userId) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + userId;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
