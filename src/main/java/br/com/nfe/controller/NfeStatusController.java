package br.com.nfe.controller;

import br.com.nfe.dto.NfeStatusOcorrenciaDTO;
import br.com.nfe.model.NfeStatus;
import br.com.nfe.service.CrudService;
import br.com.nfe.service.NfeStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/nfe-status")
@Api(value = "Status NFe", tags = { "Status NFe" })
public class NfeStatusController extends CrudControllerImpl<NfeStatus, Long>{

    private final NfeStatusService nfeStatusService;

    public NfeStatusController(NfeStatusService nfeStatusService) {
        this.nfeStatusService = nfeStatusService;
    }

    @GetMapping("/current")
    @ApiOperation(value="status current per state ")
    public List<NfeStatus> findByStatusCurrent(){
        return nfeStatusService.findByStatusCurrent();
    }

    @GetMapping("/state/{state}")
    @ApiOperation(value="last status per state ")
    public NfeStatus findByState(
            @ApiParam(example = "SP", required = true)
            @PathVariable("state") String state){
        return nfeStatusService.findByState(state);
    }

    @GetMapping("/state-per-data/{data}")
    @ApiOperation(value="find status per date ")
    public List<NfeStatus> findByStatusPerDate(
            @ApiParam(example = "2021-01-01", required = true)
            @PathVariable("data")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data){
        return nfeStatusService.findByStatusPerDate(data);
    }

    @GetMapping("/state-with-greater-unavailability")
    @ApiOperation(value="find state with greater unavailability")
    public NfeStatusOcorrenciaDTO findByStateWithGreaterUnavailability(){
         return nfeStatusService.findByStateWithGreaterUnavailability();
    }

    @Override
    public CrudService<NfeStatus, Long> service() {
        return this.nfeStatusService;
    }
}
