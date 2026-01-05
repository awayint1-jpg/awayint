package com.smart.manage.validator;

import com.smart.manage.domain.TActivity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * 活动时间范围校验器（结束时间≥开始时间）
 *
 * @author smart
 * @date 2026-01-05
 */
public class TimeRangeValidator implements ConstraintValidator<ValidTimeRange, TActivity> {
    @Override
    public boolean isValid(TActivity activity, ConstraintValidatorContext context) {
        Date startTime = activity.getActiveStartTime();
        Date endTime = activity.getActiveEndTime();

        // 若开始/结束时间为空，由@NotNull注解校验，此处不处理
        if (startTime == null || endTime == null) {
            return true;
        }

        // 结束时间 ≥ 开始时间
        return endTime.after(startTime) || endTime.equals(startTime);
    }
}