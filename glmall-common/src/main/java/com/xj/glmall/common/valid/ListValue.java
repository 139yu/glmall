package com.xj.glmall.common.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
//指定校验器，未指定需要在初始化时指定
@Constraint(
        validatedBy = {ListValueConstraintValidator.class}
)
//注解可标注位置
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
//合适能获取注解
@Retention(RetentionPolicy.RUNTIME)
public @interface ListValue {
    String message() default "{com.xj.glmall.common.valid.ListValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] vals() default {};
}
