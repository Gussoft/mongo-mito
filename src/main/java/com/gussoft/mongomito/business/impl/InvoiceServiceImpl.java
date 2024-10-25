package com.gussoft.mongomito.business.impl;

import com.gussoft.mongomito.business.InvoiceService;
import com.gussoft.mongomito.models.Invoice;
import com.gussoft.mongomito.models.InvoiceDetail;
import com.gussoft.mongomito.repository.ClientRepository;
import com.gussoft.mongomito.repository.DishRepository;
import com.gussoft.mongomito.repository.InvoiceRepository;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class InvoiceServiceImpl extends GenericServiceImpl<Invoice, String> implements InvoiceService {

    private final InvoiceRepository repository;
    private final ClientRepository clientRepository;
    private final DishRepository dishRepository;


    @Autowired
    public InvoiceServiceImpl(InvoiceRepository repository,
                              ClientRepository clientRepository,
                              DishRepository dishRepository) {
        super(repository);
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    public Mono<byte[]> generateReport(String id) {
        return repository.findById(id)
                .flatMap(this::populateClient)
                .flatMap(this::populateDetails)
                .map(this::generatePDF)
                .onErrorResume(e -> Mono.empty());
    }

    private Mono<Invoice> populateClient(Invoice obj) {
        return clientRepository.findById(obj.getClient().getId())
                .map(data -> {
                    obj.setClient(data);
                    return obj;
                });
    }

    private Mono<Invoice> populateDetails(Invoice obj) {
        List<Mono<InvoiceDetail>> details = obj.getDetail().stream()
                .map(item -> dishRepository.findById(item.getDish().getId())
                        .map(data -> {
                            item.setDish(data);
                            return item;
                        })
                ).toList();
        return Mono.when(details).then(Mono.just(obj));
    }

    private byte[] generatePDF(Invoice obj) {
        try {
            InputStream stream = new ClassPathResource("facturas.jrxml").getInputStream();
            Map<String, Object> params = new HashMap<>();
            params.put("txt_client", obj.getClient().getName());

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(obj.getDetail());
            JasperReport report = JasperCompileManager.compileReport(stream);
            JasperPrint print = JasperFillManager.fillReport(report, params, dataSource);
            return JasperExportManager.exportReportToPdf(print);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new byte[0];
        }
    }
}
