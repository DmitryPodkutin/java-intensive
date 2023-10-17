package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@JsonPropertyOrder({"nominal", "date", "curs", "cdx"})
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRate {
    private Integer nominal;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date date;
    private BigDecimal curs;
    private String cdx;
}
