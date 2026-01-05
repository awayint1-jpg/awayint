package com.smart.manage.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义校验注解：活动结束时间 ≥ 开始时间
 *
 * @author smart
 * @date 2026-01-05
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {TimeRangeValidator.class})
public @interface ValidTimeRange {
    /** 校验失败提示语 */
    String message() default "活动结束时间不能早于开始时间";

    /** 分组 */
    Class<?>[] groups() default {};

    /** 负载 */
    Class<? extends Payload>[] payload() default {};
}