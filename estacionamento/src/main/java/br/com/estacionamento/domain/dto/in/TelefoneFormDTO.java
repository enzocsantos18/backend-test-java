package br.com.estacionamento.domain.dto.in;

import javax.validation.constraints.Pattern;

public class TelefoneFormDTO {
    @Pattern(regexp = "^\\([1-9]{2}\\) [0-9]{4,5}-[0-9]{4}$", message = "O campo telefone de seguir o padrão (XX) XXXX-XXXX")
    private String numero;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
