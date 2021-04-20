package br.com.desbravador.projetoacelera.application.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ForgotPasswordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Email
    @NotEmpty
    private String email;

    public ForgotPasswordDTO() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
