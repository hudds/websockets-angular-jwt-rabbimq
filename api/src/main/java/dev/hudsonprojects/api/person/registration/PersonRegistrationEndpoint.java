package dev.hudsonprojects.api.person.registration;

import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsResolved;
import dev.hudsonprojects.api.common.validation.constraint.cpf.CPF;
import dev.hudsonprojects.api.person.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("person/registration")
@Tag(name = "Person Registration")
public class PersonRegistrationEndpoint {

    private final PersonRegistrationService personRegistrationService;

    @Autowired
    public PersonRegistrationEndpoint(PersonRegistrationService personRegistrationService) {
        this.personRegistrationService = personRegistrationService;
    }

    @Operation(description = "Creates a new person. If a person with the same CPF exists then nothing is done.")
    @ApiResponse(responseCode = "200", description = "Person created or person already exists. If the person already exists, the content of the response will be the data of the existing person.", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PersonDTO.class)) })
    @ApiResponse(responseCode = "401", description = "Authentication needed", content = {
            @Content() })
    @ApiResponse(responseCode = "400", description = "Person data is invalid", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @PostMapping(value = "/cpf/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO createByCpf(@Valid @CPF @PathVariable("cpf") String cpf, @Valid @RequestBody PersonRegistrationDTO personRegistrationDTO){
        personRegistrationDTO.setCpf(cpf);
        return personRegistrationService.createByCpf(personRegistrationDTO);
    }


    @Operation(description = "Creates or updates a person with the CPF.")
    @ApiResponse(responseCode = "200", description = "Person created or updated.", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PersonDTO.class)) })
    @ApiResponse(responseCode = "401", description = "Authentication needed", content = {
            @Content() })
    @ApiResponse(responseCode = "400", description = "Person data is invalid", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @PutMapping(value = "/cpf/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO createOrUpdateByCpf(@Valid @CPF @PathVariable("cpf") String cpf, @Valid @RequestBody PersonRegistrationDTO personRegistrationDTO){
        personRegistrationDTO.setCpf(cpf);
        return personRegistrationService.createOrUpdateByCpf(personRegistrationDTO);
    }
}
