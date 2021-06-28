package br.com.estacionamento.domain.dto.in.veiculo;

import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.veiculo.Modelo;
import br.com.estacionamento.domain.veiculo.Veiculo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class VeiculoFormDTO {
    @Pattern(regexp = "^[a-zA-Z]{3}[0-9][A-Za-z0-9][0-9]{2}$", message = "O campo placa deve seguir o padrão ABC1234")
    private String placa;
    @NotNull @NotEmpty
    private String cor;
    @NotNull  @Min(1)
    private Long id_modelo;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Long getId_modelo() {
        return id_modelo;
    }

    public void setId_modelo(Long id_modelo) {
        this.id_modelo = id_modelo;
    }


    public Veiculo converterParaVeiculo(Modelo modelo, Estacionamento estacionamento) {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(placa);
        veiculo.setCor(this.cor);
        veiculo.setModelo(modelo);
        veiculo.setEstacionamento(estacionamento);

        return veiculo;
    }
}
