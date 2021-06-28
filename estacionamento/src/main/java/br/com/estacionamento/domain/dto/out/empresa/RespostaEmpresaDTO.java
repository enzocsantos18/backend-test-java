package br.com.estacionamento.domain.dto.out.empresa;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.empresa.Endereco;
import br.com.estacionamento.domain.empresa.Telefone;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RespostaEmpresaDTO {
    private Long id;
    private String nome;
    private String cnpj;
    private List<Telefone> telefone = new ArrayList<>();
    private Endereco endereco;

    public RespostaEmpresaDTO(Empresa empresa) {
        this.id = empresa.getId();
        this.nome = empresa.getNome();
        this.cnpj = empresa.getCnpj();
        this.telefone = empresa.getTelefone();
        this.endereco = empresa.getEndereco();
    }
}
