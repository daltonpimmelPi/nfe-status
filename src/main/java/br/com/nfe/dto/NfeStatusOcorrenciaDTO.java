package br.com.nfe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NfeStatusOcorrenciaDTO {

    private String estado;

    private Integer quantidade;

    public NfeStatusOcorrenciaDTO(String estado, Integer quantidade) {
        this.estado = estado;
        this.quantidade = quantidade;
    }
}
