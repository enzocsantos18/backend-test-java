package br.com.estacionamento.service.empresa;

import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.domain.dto.in.TelefoneFormDTO;
import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.empresa.Telefone;
import br.com.estacionamento.repository.empresa.EmpresaRepository;
import br.com.estacionamento.repository.empresa.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class TelefoneService {

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Transactional
    public void deletar(TelefoneFormDTO dadosTelefone, Long empresaId) {
        Optional<Telefone> telefone = telefoneRepository.findByNumeroAndEmpresaId(dadosTelefone.getNumero(), empresaId);

        if (!telefone.isPresent()) {
            throw new DomainNotFoundException("Número não encontrado");
        }

        telefoneRepository.deleteById(telefone.get().getId());

    }

    @Transactional
    public Telefone criar(TelefoneFormDTO dadosTelefone, Long empresaId) {
        Optional<Telefone> verificarTelefone = telefoneRepository.findByNumeroAndEmpresaId(dadosTelefone.getNumero(), empresaId);

        if (verificarTelefone.isPresent()) {
            throw new DomainNotFoundException("Número já cadastrado");
        }

        Empresa empresa = getEmpresa(empresaId);
        Telefone telefone = dadosTelefone.converterParaTelefone(empresa);


        return telefoneRepository.save(telefone);
    }

    private Empresa getEmpresa(Long id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if (!empresa.isPresent()) {
            throw new DomainNotFoundException("Empresa não encontrada");
        }

        return empresa.get();
    }

}
