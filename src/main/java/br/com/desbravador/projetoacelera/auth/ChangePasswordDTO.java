package br.com.desbravador.projetoacelera.auth;

public class ChangePasswordDTO {

    private String password;
    private String confirmPassword;
    private String token;

    public ChangePasswordDTO(String password, String confirmPassword, String token) {
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean notEquals() {
        return !password.equals(confirmPassword);
    }

    @Override
    public String toString() {
        return "ChangePasswordDTO{" +
                "password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
