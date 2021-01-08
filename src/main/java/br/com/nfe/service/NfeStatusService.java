package br.com.nfe.service;

import br.com.nfe.dto.NfeStatusOcorrenciaDTO;
import br.com.nfe.model.NfeStatus;

import java.time.LocalDate;
import java.util.List;

public interface NfeStatusService extends CrudService<NfeStatus, Long>{

    void process();

    NfeStatus findByState(String state);

    List<NfeStatus> findByStatusCurrent();

    List<NfeStatus> findByStatusPerDate(LocalDate data);

    NfeStatusOcorrenciaDTO findByStateWithGreaterUnavailability();

}
