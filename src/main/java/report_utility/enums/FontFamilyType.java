package report_utility.enums;

import lombok.Getter;

@Getter
public enum FontFamilyType {

    HELVETICA("Helvetica"),
    HELVETICA_BOLD("Helvetica-Bold"),
    ROBOTO_REGULAR("Roboto-Regular"),
    ROBOTO_ITALIC("Roboto-Italic"),
    ROBOTO_BOLD("Roboto-Bold"),
    ROBOTO_BOLD_ITALIC("Roboto-BoldItalic"),
    ROBOTO_LIGHT("Roboto-Light"),
    ROBOTO_LIGHT_ITALIC("Roboto-LightItalic"),
    ROBOTO_MEDIUM("Roboto-Medium"),
    ROBOTO_MEDIUM_ITALIC("Roboto-MediumItalic"),
    ROBOTO_THIN("Roboto-Thin"),
    ROBOTO_THIN_ITALIC("Roboto-ThinItalic"),
    ROBOTO_BLACK("Roboto-Black"),
    ROBOTO_BLACK_ITALIC("Roboto-BlackItalic"),

    ;

    private String value;

    FontFamilyType(String value) {
        this.value = value;
    }

}
