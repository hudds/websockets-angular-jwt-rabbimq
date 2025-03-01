package dev.hudsonprojects.api.person;

import dev.hudsonprojects.api.common.exception.NotFoundException;
import dev.hudsonprojects.api.common.exception.ValidationException;
import dev.hudsonprojects.api.common.lib.util.CPFUtil;
import dev.hudsonprojects.api.common.lib.util.StringUtils;
import dev.hudsonprojects.api.common.messages.APIMessage;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public PersonDTO findDTOByCpf(String cpf){
        if(StringUtils.isBlank(cpf)){
            throw new ValidationException(ErrorDetailsBuilder.builder()
                    .setMessage(new APIMessage("validation.person.cpf.required"))
                    .build());
        }

        if(!CPFUtil.isCPF(cpf)){
            throw new ValidationException(ErrorDetailsBuilder.builder()
                    .setMessage(new APIMessage("validation.CPF.invalid"))
                    .build());
        }

        cpf = StringUtils.removeNonDigits(cpf);

        return personRepository.findByCpf(cpf)
                .map(PersonDTO::new)
                .orElseThrow(() -> new NotFoundException(ErrorDetailsBuilder.builder()
                    .setMessage(new APIMessage("validation.person.notFound"))
                    .build())
                );
    }


    public Person getPersonByCpf(String cpf) {
        if(StringUtils.isBlank(cpf)){
            throw new ValidationException(ErrorDetailsBuilder.builder()
                    .setMessage(new APIMessage("validation.person.cpf.required"))
                    .build());
        }

        if(!CPFUtil.isCPF(cpf)){
            throw new ValidationException(ErrorDetailsBuilder.builder()
                    .setMessage(new APIMessage("validation.CPF.invalid"))
                    .build());
        }
        return personRepository.findByCpf(cpf)
                .orElseThrow(() -> new NotFoundException(ErrorDetailsBuilder.builder()
                        .setMessage(new APIMessage("validation.person.notFound"))
                        .build())
                );
    }
}
