package dev.hudsonprojects.api.person;


import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsResolved;
import dev.hudsonprojects.api.common.validation.constraint.cpf.CPF;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("person")
@Tag(name = "Person")
public class PersonEndpoint {

    private final PersonService personService;

    @Autowired
    public PersonEndpoint(PersonService personService) {
        this.personService = personService;
    }

    @ApiResponse(responseCode = "200", description = "Finds a person by CPF.", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PersonDTO.class)) })
    @ApiResponse(responseCode = "401", description = "Authentication needed", content = {
            @Content() })
    @ApiResponse(responseCode = "400", description = "CPF is invalid", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @ApiResponse(responseCode = "404", description = "Person not found", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @GetMapping(path = "/cpf/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO findByCPF(@PathVariable("cpf") @Valid @CPF  String cpf){
        return personService.findDTOByCpf(cpf);
    }
}
