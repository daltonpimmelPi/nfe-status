package br.com.nfe.model;

import br.com.nfe.enumerations.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "NFE_STATUS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NfeStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NFE_STATUS")
    private Long id;

    @Column(name="ESTADO", nullable = false)
    private String estado;

    @Column(name="AUTORIZACAO")
    @Enumerated(EnumType.STRING)
    public Status autorizacao;

    @Column(name = "RETORNO_AUTORIZACAO")
    @Enumerated(EnumType.STRING)
    private Status retornoAutorizacao;

    @Column(name = "INUTILIZACAO")
    @Enumerated(EnumType.STRING)
    private Status inutilizacao;

    @Column(name = "CONSULTA_PROTOCOLO")
    @Enumerated(EnumType.STRING)
    private Status consultaProtocolo;

    @Column(name = "SERVICO")
    @Enumerated(EnumType.STRING)
    private Status servico;

    @Column(name = "TEMPO_MEDIO")
    private String tempoMedio;

    @Column(name = "CONSULTA_CADASTRO")
    @Enumerated(EnumType.STRING)
    private Status consultaCadastro;

    @Column(name = "RECEPCAO_EVENTO")
    @Enumerated(EnumType.STRING)
    private Status recepcaoEvento;

    @Column(name = "DATA_HORA_EXECUCAO")
    private LocalDateTime dataHoraExecucao;

}
