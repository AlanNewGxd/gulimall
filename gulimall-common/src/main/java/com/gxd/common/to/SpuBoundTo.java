package com.gxd.common.to;

import lombok.Data;
/**
 * @author guxiaodong
 * @version 1.0
 * @title TODO
 * @date 2021/6/17 14:51
 */
import java.math.BigDecimal;

@Data
public class SpuBoundTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
