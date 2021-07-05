package cinema;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TitleValidator implements ConstraintValidator<Title, String> {

    private int maxLength;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null &&
                !value.isBlank() &&
                value.length() > 2 &&
                value.length() <= maxLength;
    }

    @Override
    public void initialize(Title constraintAnnotation) {
        maxLength = constraintAnnotation.maxLength();
    }
}
