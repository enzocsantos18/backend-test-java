package br.com.estacionamento.service;

import br.com.estacionamento.domain.Estacionamento;
import br.com.estacionamento.domain.TipoVeiculo;
import br.com.estacionamento.domain.Vaga;
import br.com.estacionamento.domain.dto.in.VagaFormDTO;
import br.com.estacionamento.repository.EstacionamentoRepository;
import br.com.estacionamento.repository.TipoVeiculoRepository;
import br.com.estacionamento.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VagaService {

    @Autowired
    private VagaRepository vagaRepository;
    @Autowired
    private EstacionamentoRepository estacionamentoRepository;
    @Autowired
    private TipoVeiculoRepository tipoVeiculoRepository;


    public Vaga adicionar(VagaFormDTO dadosVaga) {

        Optional<Estacionamento> estacionamentoEncontrado = estacionamentoRepository.findByEmpresaIdAndId(
                dadosVaga.getEmpresa_id(),
                dadosVaga.getEstacionamento_id());

        if (!estacionamentoEncontrado.isPresent()){
            throw new RuntimeException("Estacionamento não encontrado!");
        }

        Optional<TipoVeiculo> tipoEncontrado = tipoVeiculoRepository.findById(dadosVaga.getTipo_id());
        if (!tipoEncontrado.isPresent()){
            throw new RuntimeException("Tipo de veiculo não encontrado!");
        }


        Estacionamento estacionamento = estacionamentoEncontrado.get();
        TipoVeiculo tipo = tipoEncontrado.get();

        Optional<Vaga> temVaga = vagaRepository.findByEstacionamentoIdAndTipoId(
                dadosVaga.getEstacionamento_id(),
                dadosVaga.getTipo_id());

        if (temVaga.isPresent()){
            throw new RuntimeException("Não é possível criar mais uma vaga para esse tipo");
        }

        Vaga vaga = dadosVaga.converterParaVaga(estacionamento, tipo);

        Vaga vagaCriada = vagaRepository.save(vaga);

        return vagaCriada;
    };

    public void deletar(Long empresaId,Long estacionamentoId, Long tipoVagaId) {
        Optional<Vaga> vagaEncontrada = vagaRepository.findByEstacionamentoIdAndTipoId(estacionamentoId, tipoVagaId);
        if (!vagaEncontrada.isPresent()){
            throw new RuntimeException("Vaga não encontrada");
        }


        Vaga vaga = vagaEncontrada.get();
        if (vaga.getEstacionamento().getEmpresa().getId() != empresaId) {
            throw new RuntimeException("Vaga não encontrada");
        }


        vagaRepository.delete(vaga);
    }

    public List<Vaga> buscar(Long empresaId, Long estacionamentoId) {

        List<Vaga> vagas = vagaRepository.findByEstacionamentoIdAndEstacionamentoEmpresaId(estacionamentoId, empresaId);

        return vagas;
    }

    public Vaga atualizar(VagaFormDTO dadosVaga) {
        Optional<Estacionamento> estacionamentoEncontrado = estacionamentoRepository.findByEmpresaIdAndId(
                dadosVaga.getEmpresa_id(),
                dadosVaga.getEstacionamento_id());

        if (!estacionamentoEncontrado.isPresent()){
            throw new RuntimeException("Estacionamento não encontrado!");
        }

        Optional<TipoVeiculo> tipoEncontrado = tipoVeiculoRepository.findById(dadosVaga.getTipo_id());
        if (!tipoEncontrado.isPresent()){
            throw new RuntimeException("Tipo de veiculo não encontrado!");
        }


        Optional<Vaga> temVaga = vagaRepository.findByEstacionamentoIdAndTipoId(
                dadosVaga.getEstacionamento_id(),
                dadosVaga.getTipo_id());

        if (!temVaga.isPresent()){
            throw new RuntimeException("Vaga não encontrada");
        }

        Vaga vaga = temVaga.get();

        vaga.setQuantidade(dadosVaga.getQuantidade());

        Vaga vagaAtualizada = vagaRepository.save(vaga);

        return vagaAtualizada;
    }
}
