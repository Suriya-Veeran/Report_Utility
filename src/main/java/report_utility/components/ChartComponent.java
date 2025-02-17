package report_utility.components;

import com.itextpdf.layout.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import report_utility.beans.ChartCreationConfig;
import report_utility.core.interfaces.ReportComponent;

import java.io.IOException;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartComponent implements ReportComponent {

    private ChartCreationConfig inputBean;

    @Override
    public void render(Document document) throws IOException {

    }
}
