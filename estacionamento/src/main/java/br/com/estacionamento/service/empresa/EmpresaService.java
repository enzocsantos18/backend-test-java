package br.com.estacionamento.service.empresa;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.empresa.Endereco;
import br.com.estacionamento.domain.empresa.Telefone;
import br.com.estacionamento.domain.dto.in.EmpresaFormDTO;
import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.repository.empresa.EmpresaRepository;
import br.com.estacionamento.repository.empresa.EnderecoRepository;
import br.com.estacionamento.repository.empresa.TelefoneRepository;
import br.com.estacionamento.service.empresa.CepParaEnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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


    public Empresa buscarPorId(Long id) {
        Empresa empresa = encontrarPorId(id);
        return empresa;
    }

    @Transactional
    public Empresa criar(EmpresaFormDTO dadosEmpresa) {
        Empresa empresa = dadosEmpresa.converterParaEmpresa();

        if (empresaRepository.findByCnpj(empresa.getCnpj()).isPresent()) {
            throw new DomainException("Cnpj já cadastrado.");
        }

        try {
            Endereco endereco = cepParaEnderecoService.buscarDadosEndereco(dadosEmpresa.getCep(), dadosEmpresa.getNumero());
            Endereco enderecoSalvo = enderecoRepository.save(endereco);
            empresa.setEndereco(enderecoSalvo);

            Empresa empresaSalva = empresaRepository.save(empresa);

            Telefone telefone = dadosEmpresa.getTelefone();
            telefone.setEmpresa(empresaSalva);
            Telefone telefoneSalvo = telefoneRepository.save(telefone);

            empresaSalva.adicionarTelefone(telefoneSalvo);
            return empresaSalva;
        } catch (Exception e) {
            throw new DomainException("Cadastro empresa não foi realizado, confira os dados novamente.");
        }
    }

    @Transactional
    public void deletarPorId(Long id) {
        Empresa empresa = encontrarPorId(id);
        empresaRepository.deleteById(empresa.getId());
    }

    private Empresa encontrarPorId(Long id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if (!empresa.isPresent()) {
            throw new DomainNotFoundException("Empresa não encontrada");
        }

        return empresa.get();
    }

}
