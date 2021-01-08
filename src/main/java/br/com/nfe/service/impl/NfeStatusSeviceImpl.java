package br.com.nfe.service.impl;

import br.com.nfe.dto.NfeStatusOcorrenciaDTO;
import br.com.nfe.enumerations.Status;
import br.com.nfe.job.NfeStatusScheduledTasks;
import br.com.nfe.model.NfeStatus;
import br.com.nfe.repository.NfeStatusRepository;
import br.com.nfe.service.NfeStatusService;
import br.com.nfe.util.Columns;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class NfeStatusSeviceImpl extends CrudServiceImpl<NfeStatus, Long> implements NfeStatusService{

    private final NfeStatusRepository nfeStatusRepository;

    private static final Logger log = LoggerFactory.getLogger(NfeStatusScheduledTasks.class);

    private static final String url = "http://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx";

    public NfeStatusSeviceImpl(NfeStatusRepository nfeStatusRepository) {
        this.nfeStatusRepository = nfeStatusRepository;
    }

    @Override
    protected CrudRepository<NfeStatus, Long> repository() {
        return this.nfeStatusRepository;
    }

    @Override
    public void process() {

        try {

            HttpClient cliente = HttpClientBuilder
                    .create()
                    .setRedirectStrategy(new LaxRedirectStrategy())
                    .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                    .build();

            HttpGet pedido = new HttpGet(url);
            HttpResponse resposta = cliente.execute(pedido);
            HttpEntity entidade = resposta.getEntity();
            String html = EntityUtils.toString(entidade);
            Document doc = Jsoup.parse(html);
            int qtdColumn = 0;
            List<NfeStatus> listNfeStatus = new ArrayList<>();
            for (org.jsoup.nodes.Element i : doc.getAllElements().select("table[class=tabelaListagemDados] tbody tr")) {
                Element td = i.select("td").first();
                if(td != null){
                    NfeStatus nfeStatus = new NfeStatus();
                    nfeStatus.setEstado(td.text());
                    nfeStatus.setDataHoraExecucao(LocalDateTime.now());
                    createStatusNfe(i, nfeStatus, qtdColumn);
                    listNfeStatus.add(nfeStatus);
                }else{
                    qtdColumn = i.select("th").size();
                }
            }

            save(listNfeStatus);

        }catch (Exception e){
            log.info("error: " + e);
            throw new RuntimeException(e.getMessage());
        }

    }

    private void createStatusNfe(Element i, NfeStatus nfeStatus, int qtdColumn) {
        try {
            Map<Integer,String> header = Columns.create();
            for(int column = 1; column < qtdColumn; column++){
                String val = header.get(column);
                if(val != null){
                    String image = i.select("td").get(column).select("img").attr("src");
                    Status status = Status.fromString(image);
                    Field declaredField = NfeStatus.class.getDeclaredField(val);
                    declaredField.setAccessible(true);
                    declaredField.set(nfeStatus, status);
                }
            }
        }catch (Exception e){
            log.info("error: " + e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public NfeStatus findByState(String state) {
        return nfeStatusRepository.findFirstByEstadoOrderByDataHoraExecucaoDesc(state);
    }

    @Override
    public List<NfeStatus> findByStatusCurrent() {
        return nfeStatusRepository.findByStatusCurrent();
    }

    @Override
    public List<NfeStatus> findByStatusPerDate(LocalDate data) {
        return nfeStatusRepository.findByStatusPerDate(data);
    }

    @Override
    public NfeStatusOcorrenciaDTO findByStateWithGreaterUnavailability() {

        List<NfeStatus> nfeStatus = nfeStatusRepository.findByStateWithGreaterUnavailability();

        Map<String, Integer> map = new HashMap<>();

        nfeStatus.stream().forEach(e -> {
            e.getAutorizacao().isUnavailability(map, e.getEstado());
            e.getConsultaCadastro().isUnavailability(map, e.getEstado());
            e.getInutilizacao().isUnavailability(map, e.getEstado());
            e.getConsultaProtocolo().isUnavailability(map, e.getEstado());
            e.getRecepcaoEvento().isUnavailability(map, e.getEstado());
            e.getRetornoAutorizacao().isUnavailability(map, e.getEstado());
            e.getServico().isUnavailability(map, e.getEstado());
        });

        Optional<Map.Entry<String, Integer>> maxEntry = map.entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue));

        return maxEntry.isEmpty() ? null : new NfeStatusOcorrenciaDTO(maxEntry.get().getKey(), maxEntry.get().getValue());
    }

}
