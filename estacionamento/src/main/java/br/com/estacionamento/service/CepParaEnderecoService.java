package br.com.estacionamento.service;

import br.com.estacionamento.domain.Endereco;
import br.com.estacionamento.domain.dto.out.EnderecoDTO;
import org.springframework.stereotype.Service;

@Service
public class CepParaEnderecoService {
    public Endereco buscarDadosEndereco(String cep){
        Endereco endereco = new Endereco();

        endereco.setCep(cep);
        endereco.setLogradouro("teste");
        endereco.setNumero(202);
        endereco.setBairro("teste");
        endereco.setCidade("teste");
        endereco.setEstado("teste");

        return endereco;
    }
}
