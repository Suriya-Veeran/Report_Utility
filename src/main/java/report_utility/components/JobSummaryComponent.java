package report_utility.components;

import com.itextpdf.layout.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import report_utility.beans.JobSummaryBean;
import report_utility.core.interfaces.ReportComponent;

import java.io.IOException;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSummaryComponent implements ReportComponent {

    private JobSummaryBean inputBean;


    @Override
    public void render(Document document) throws IOException {

    }
}
