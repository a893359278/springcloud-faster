package com.csp.github.tenant.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author 陈少平
 * @date 2020-01-01 16:37
 */
public enum PermissionTypeEnum implements IEnum<Integer> {
    none(0, "none"), mulu(1, "目录"), caidan(2, "菜单"), anniu(3, "按钮");

    private Integer value;
    private String name;

    PermissionTypeEnum(Integer value, String name) {
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
