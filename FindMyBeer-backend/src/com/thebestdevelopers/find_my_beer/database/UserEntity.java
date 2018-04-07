/**
 * @author Wiktor Florencki
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "user", schema = "main", catalog = "d86n3p8h6i057d")
public class UserEntity {
    private long userId;
    private String password;
    private String username;

    @javax.persistence.Id
    @javax.persistence.Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "password")
    public java.lang.String getPassword() {
        return password;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "username")
    public java.lang.String getUsername() {
        return username;
    }

    public void setUsername(java.lang.String username) {
        this.username = username;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        UserEntity that = (UserEntity) object;

        if (userId != that.userId) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
