package dev.hudsonprojects.backend.common.validation.constraint.cpf;

import dev.hudsonprojects.backend.common.lib.util.CPFUtil;
import dev.hudsonprojects.backend.common.lib.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPF, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isBlank(value) || CPFUtil.isCPF(value);
    }

}
