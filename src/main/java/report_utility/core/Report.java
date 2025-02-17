package report_utility.core;

import com.itextpdf.layout.Document;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import report_utility.core.factory.ReportComponentFactory;
import report_utility.core.interfaces.ReportBean;
import report_utility.core.interfaces.ReportComponent;
import report_utility.core.utils.FileUtility;
import report_utility.enums.ComponentType;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class Report {
    @Getter
    private final Document document;
    private final List<ReportComponent> components = new LinkedList<>();

    public Report(Document document) {
        this.document = document;
    }

    public void addComponent(ComponentType type, ReportBean bean) {
//        components.add(ReportComponentFactory.createComponent(type, bean));
        ReportComponent component = ReportComponentFactory.createComponent(type, bean);
        if (component == null) {
            log.error("Component creation failed for type: {}", type);
        } else {
            log.info("Component added: {}", component.getClass().getSimpleName());
            components.add(component);
        }
    }

    public void addComponent(ReportComponent reportComponent) {
        components.add(reportComponent);
    }

    public void render() throws IOException {
        if (document == null) {
            throw new IllegalStateException("Document is null in render()");
        }
        for (ReportComponent component : components) {
            component.render(this.document);
        }
        components.clear();
        FileUtility.cleanTemporaryFiles();
    }

    public void close() {
        if (Objects.nonNull(this.document)) {
            this.document.flush();
            this.document.close();
        }
    }

}
