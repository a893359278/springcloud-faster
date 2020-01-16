package com.csp.github.base.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.csp.github.base.common.annotation.Create;
import com.csp.github.base.common.annotation.Update;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 陈少平
 * @date 2020-01-15 23:52
 */

@Getter
@Setter
public class BaseEntity extends DateEntity {

    @Null(groups = {Create.class})
    @NotNull(groups = {Update.class})
    @TableId
    private Long id;

}
