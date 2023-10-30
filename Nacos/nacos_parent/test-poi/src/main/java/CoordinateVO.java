import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordinateVO {
    /**
     * excel中的横坐标
     */
    private Integer coordinateX;
    /**
     * excel中的纵坐标
     */
    private Integer coordinateY;
    /**
     * excel中的每个单元格的数据值
     */
    private String dataValue;
}
