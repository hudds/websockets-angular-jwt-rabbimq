package dev.hudsonprojects.api.course.registration;

import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsResolved;
import dev.hudsonprojects.api.course.CourseDTO;
import dev.hudsonprojects.api.person.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("course")
@Tag(name = "Course Registration")
public class CourseRegistrationEndpoint {

    private final CourseRegistrationService courseRegistrationService;

    @Autowired
    public CourseRegistrationEndpoint(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }


    @Operation(description = "Creates a new course.")
    @ApiResponse(responseCode = "200", description = "Course created", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PersonDTO.class)) })
    @ApiResponse(responseCode = "401", description = "Authentication needed", content = {
            @Content() })
    @ApiResponse(responseCode = "400", description = "Course data is invalid", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CourseDTO create(@RequestBody CourseRegistrationDTO personRegistrationDTO){
        return courseRegistrationService.createCourse(personRegistrationDTO);
    }

    @Operation(description = "Update course")
    @ApiResponse(responseCode = "200", description = "Course updates", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PersonDTO.class)) })
    @ApiResponse(responseCode = "401", description = "Authentication needed", content = {
            @Content() })
    @ApiResponse(responseCode = "400", description = "Course data is invalid", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @ApiResponse(responseCode = "404", description = "Course not found", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CourseDTO update(@RequestBody CourseUpdateDTO personRegistrationDTO){
        return courseRegistrationService.updateCourse(personRegistrationDTO);
    }
}
