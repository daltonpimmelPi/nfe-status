package br.com.nfe.repository;

import br.com.nfe.model.NfeStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface NfeStatusRepository extends CrudRepository<NfeStatus, Long> {

    NfeStatus findFirstByEstadoOrderByDataHoraExecucaoDesc(String state);

    @Query(
            value = "select " +
                    "nf.*," +
                    "(select estado from nfe_status " +
                    "where estado = nf.estado " +
                    "and data_hora_execucao = (select MAX(data_hora_execucao) from nfe_status ) group by estado) as estado " +
                    "from nfe_status nf " +
                    "where data_hora_execucao = (select MAX(data_hora_execucao) from nfe_status )",
            nativeQuery = true
    )
    List<NfeStatus> findByStatusCurrent();

    @Query(
            value = "SELECT " +
                    " * " +
                    "FROM nfe_status " +
                    "where DATE_FORMAT(data_hora_execucao , '%Y-%m-%d') = :data",
            nativeQuery = true
    )
    List<NfeStatus> findByStatusPerDate(@Param("data") LocalDate data);

    @Query(
            value = "select ns.* from nfe_status ns " +
                    "where ns.autorizacao in ('VERMELHO', 'AMARELO') " +
                    "or ns.consulta_cadastro in ('VERMELHO', 'AMARELO') " +
                    "or ns.consulta_protocolo in ('VERMELHO', 'AMARELO') " +
                    "or ns.inutilizacao in ('VERMELHO', 'AMARELO') " +
                    "or ns.recepcao_evento in ('VERMELHO', 'AMARELO') " +
                    "or ns.retorno_autorizacao in ('VERMELHO', 'AMARELO') " +
                    "or ns.servico in ('VERMELHO', 'AMARELO') " +
                    "ORDER BY ns.estado ",
            nativeQuery = true
    )
    List<NfeStatus> findByStateWithGreaterUnavailability();
}
