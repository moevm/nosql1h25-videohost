package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static org.example.dto.ChartConfig.*;

@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        X_AXIS_ATTRIBUTE_PROPERTY, Y_AXIS_ATTRIBUTE_PROPERTY, CHART_TYPE_PROPERTY
})
@JsonTypeName("ChartConfig")
public class ChartConfig {

    public static final String X_AXIS_ATTRIBUTE_PROPERTY = "xAxisAttribute";
    private String xAxisAttribute;  // e.g., "views", "uploadDate"

    public static final String Y_AXIS_ATTRIBUTE_PROPERTY = "yAxisAttribute";
    private String yAxisAttribute;  // e.g., "user", "tags"

    public static final String CHART_TYPE_PROPERTY = "chartType";
    private ChartType chartType;    // BAR, PIE, LINE, etc.

    @JsonProperty(X_AXIS_ATTRIBUTE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getxAxisAttribute() {
        return xAxisAttribute;
    }

    @JsonProperty(X_AXIS_ATTRIBUTE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setxAxisAttribute(String xAxisAttribute) {
        this.xAxisAttribute = xAxisAttribute;
    }

    @JsonProperty(Y_AXIS_ATTRIBUTE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getyAxisAttribute() {
        return yAxisAttribute;
    }

    @JsonProperty(Y_AXIS_ATTRIBUTE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setyAxisAttribute(String yAxisAttribute) {
        this.yAxisAttribute = yAxisAttribute;
    }

    @JsonProperty(CHART_TYPE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public ChartType getChartType() {
        return chartType;
    }

    @JsonProperty(CHART_TYPE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }
}
