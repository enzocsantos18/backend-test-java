package br.com.estacionamento.service;

import br.com.estacionamento.domain.Empresa;
import br.com.estacionamento.domain.Endereco;
import br.com.estacionamento.domain.Telefone;
import br.com.estacionamento.domain.dto.in.EmpresaFormDTO;
import br.com.estacionamento.repository.EmpresaRepository;
import br.com.estacionamento.repository.EnderecoRepository;
import br.com.estacionamento.repository.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EmpresaService {

    @Autowired
    private CepParaEnderecoService cepParaEnderecoService;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private TelefoneRepository telefoneRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;


    @Transactional
    public Empresa criar(EmpresaFormDTO dadosEmpresa){
        Empresa empresa = dadosEmpresa.converterParaEmpresa();

        if(empresaRepository.findByCnpj(empresa.getCnpj()).isPresent()){
           throw new RuntimeException();
        }

        try{

            Endereco endereco = cepParaEnderecoService.buscarDadosEndereco(dadosEmpresa.getCep());
            endereco.setNumero(dadosEmpresa.getNumero());
            endereco = enderecoRepository.save(endereco);
            empresa.setEndereco(endereco);
            empresa = empresaRepository.save(empresa);
            Telefone telefone = dadosEmpresa.getTelefone();
            telefone.setEmpresa(empresa);
            telefone = telefoneRepository.save(telefone);

            empresa.adicionarTelefone(telefone);
            return empresa;
        }
        catch(Exception e){
            throw new RuntimeException();
        }
    }

}
