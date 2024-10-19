package dev.hudsonprojects.api.common.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import dev.hudsonprojects.api.common.messages.APIMessage;
import dev.hudsonprojects.api.common.messages.error.fielderror.APIFieldError;

public class GenericValidator<T> {


    private final List<GenericValidatorAction<T>> validationActions;
    private final Class<T> type;

    private GenericValidator(Builder<T> builder, Class<T> type) {
        this.validationActions = new ArrayList<>(builder.actions);
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }

    public List<APIFieldError> applyValidations(T value) {
        List<APIFieldError> messages = new ArrayList<>();
        for(var action : this.validationActions){
            action.validate(value).ifPresent(messages::add);
        }
        return messages;
    }

    public static <T> Builder<T> builder(Class<T> type){
        return new Builder<>(type);
    }


    public static class Builder<T> {
        private final List<GenericValidatorAction<T>> actions = new ArrayList<>();
        private final Class<T> type;

        private Builder(Class<T> type){
            this.type = type;
        }

        public Builder<T> addValidation(String fieldName, String message, Function<T, Boolean> isValid){
            this.actions.add(new GenericValidatorAction<>(fieldName, message, isValid));
            return this;
        }

        public GenericValidator<T> build(){
            return new GenericValidator<>(this, type);
        }
    }
    

    public static class GenericValidatorAction<T>{
        private final String fieldName;
        private final Function<T, Boolean> isValid;
        private final String message;

        public GenericValidatorAction(String fieldName, String message, Function<T, Boolean> isValid) {
            this.fieldName = fieldName;
            this.isValid = isValid;
            this.message = message;
        }

        public Optional<APIFieldError> validate(T value){
            if (Boolean.TRUE.equals(isValid.apply(value))) {
                return Optional.empty();
            }
            return Optional.of(new APIFieldError(fieldName, List.of(new APIMessage(message))));
        }
        
    }

}
