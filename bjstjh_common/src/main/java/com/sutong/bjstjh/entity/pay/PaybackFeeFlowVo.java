/**
 * @Description: 存放生成补费流水需要的参数
 * @ClassName: PaybackFeeFlowVo
 * @author： WangLei
 * @date: 2019/12/24 14:03
 * @Version： 1.0
 */
package com.sutong.bjstjh.entity.pay;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by 王磊 on 2019/12/24.
 */
@Data
public class PaybackFeeFlowVo implements Serializable{

    private String tollProvinceId;//工单发起方省中心id
    private Long messageId;//工单发起方省中心生成的文件id
    private String passId;//通行标识
    private String obuId;//OBU号
    private Integer owefee;//欠费金额

}
