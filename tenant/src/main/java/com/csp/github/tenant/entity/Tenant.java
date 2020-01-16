package com.csp.github.tenant.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.csp.github.base.web.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 租户
 * </p>
 *
 * @author 陈少平
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Tenant extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    /**
     * 昵称
     */
    private String nickName;

    private String avatar;

    /**
     * 客服微信号
     */
    private String kfWeixin;

    /**
     * 客服微信二维码
     */
    private String kfWeixinQr;

    /**
     * 客服电话
     */
    private String kfPhone;

    /**
     * 余额
     */
    private BigDecimal credit;

    /**
     * 充值费率
     */
    private BigDecimal charge;

    /**
     * 是否自动打款1.是 2.否
     */
    private Integer isAutoTransfer;

    /**
     * 开启微信打款 1.是 2.否
     */
    private Integer openWxpay;

    /**
     * 开启支付宝打款 1.是 2.否
     */
    private Integer openAlipay;

    /**
     * 开启短信登录 1.是 2.否
     */
    private Integer openSms;

    /**
     * 短信单价
     */
    private BigDecimal smsPer;

    /**
     * 短信数量
     */
    private Integer smsCount;

    /**
     * 存储空间单价
     */
    private BigDecimal storageSpacePer;

    /**
     * 存储空间大小
     */
    private Integer storageSpaceCount;

    /**
     * 1.启用  2.禁用
     */
    private Integer enable;

    /**
     * 到期时间
     */
    private LocalDateTime expirationTime;

    @TableField(exist = false)
    private List<String> permission;
}
