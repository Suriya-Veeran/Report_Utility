package report_utility.enums;

import lombok.Getter;

@Getter
public enum ProductLogo {

    ARCHON_DATA_STORE("ADS", "product_logo/Archon-Datastore.png"),
    ARCHON_ETL("ETL", "product_logo/Archon-ETL.png"),
    PATIENT_360("Patient 360", "product_logo/Patient-360.png"),
    ARCHON_ANALYSER("Analyser", "product_logo/Archon-Analyser.png"),
    DCOM_360("Dcom 360", "product_logo/DCOM-360.png"),;

    private String displayName;
    private String filePath;

    ProductLogo(String logo, String filePath) {
        this.displayName = logo;
        this.filePath = filePath;
    }


}
