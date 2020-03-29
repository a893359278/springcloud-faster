package com.csp.github.base.web.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.csp.github.base.common.annotation.Create;
import java.time.LocalDateTime;
import javax.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 陈少平
 * @date 2019-12-30 21:50
 */
@Getter
@Setter
public class DateEntity {

    /**
     * 创建时间
     */
    @Null(groups = Create.class)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Null(groups = Create.class)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
