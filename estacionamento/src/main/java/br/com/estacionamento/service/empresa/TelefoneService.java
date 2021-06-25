package br.com.estacionamento.service.empresa;

import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.domain.dto.in.EmpresaFormDTO;
import br.com.estacionamento.domain.dto.in.EmpresaFormUpdateDTO;
import br.com.estacionamento.domain.dto.in.TelefoneFormDTO;
import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.empresa.Endereco;
import br.com.estacionamento.domain.empresa.Telefone;
import br.com.estacionamento.repository.empresa.EmpresaRepository;
import br.com.estacionamento.repository.empresa.EnderecoRepository;
import br.com.estacionamento.repository.empresa.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class TelefoneService {

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Transactional
    public void deletar(TelefoneFormDTO dadosTelefone, Long empresaId) {
        Optional<Telefone> telefone = telefoneRepository.findByNumeroAndEmpresaId(dadosTelefone.getNumero(), empresaId);

        if (!telefone.isPresent()){
            throw new DomainNotFoundException("Número não encontrado");
        }

        telefoneRepository.deleteById(telefone.get().getId());

    }
}
