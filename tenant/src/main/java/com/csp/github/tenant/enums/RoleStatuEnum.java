package com.csp.github.tenant.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author 陈少平
 * @date 2020-01-01 16:37
 */
public enum RoleStatuEnum implements IEnum<Integer> {
    none(0, "none"), qiyong(1, "启用"), jinyong(2, "禁用");

    private Integer value;
    private String name;

    RoleStatuEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
