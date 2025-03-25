package org.orient.flashsalesystem.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.orient.flashsalesystem.utils.ValidatorUtil;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!required && s.isEmpty()) {
            return true;
        } else {
            return ValidatorUtil.isMobile(s);
        }
    }
}
