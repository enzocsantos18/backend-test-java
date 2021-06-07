package br.com.estacionamento.domain.dto.in;

import br.com.estacionamento.domain.Estacionamento;
import br.com.estacionamento.domain.Modelo;
import br.com.estacionamento.domain.Veiculo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class VeiculoFormDTO {
    @NotNull @NotEmpty @Pattern(regexp = "^[a-zA-Z]{3}[0-9][A-Za-z0-9][0-9]{2}$")
    private String placa;
    @NotNull @NotEmpty
    private String cor;
    @Min(1) 
    private Long id_modelo;
    @Min(1)
    private Long id_estacionamento;

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

    public Long getId_estacionamento() {
        return id_estacionamento;
    }

    public void setId_estacionamento(Long id_estacionamento) {
        this.id_estacionamento = id_estacionamento;
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
