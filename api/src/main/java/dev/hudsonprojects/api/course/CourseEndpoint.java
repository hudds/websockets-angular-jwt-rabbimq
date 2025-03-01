package dev.hudsonprojects.api.course;

import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsResolved;
import dev.hudsonprojects.api.common.validation.constraint.cpf.CPF;
import dev.hudsonprojects.api.person.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("course")
@Tag(name = "Course")
public class CourseEndpoint {

    private final CourseService courseService;

    @Autowired
    public CourseEndpoint(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(description = "Retrieve course info by id.")
    @ApiResponse(responseCode = "200", description = "Course found", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PersonDTO.class)) })
    @ApiResponse(responseCode = "401", description = "Authentication needed", content = {
            @Content() })
    @ApiResponse(responseCode = "404", description = "Course not found", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @GetMapping(value = "id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CourseInfoDTO getById(@PathVariable(name = "id") Long courseId){
        return courseService.getCourseInfoById(courseId);
    }

    @Operation(description = "Retrieve all courses info")
    @ApiResponse(responseCode = "200", description = "Returns the courses info", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PersonDTO.class)) })
    @ApiResponse(responseCode = "401", description = "Authentication needed", content = {
            @Content() })
    @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CourseInfoDTO> getAllCourseInfo(@RequestParam(value = "pageNumber", required = false) Integer pageNumber, @RequestParam(value = "pageSize", required = false) Integer pageSize, @Valid @CPF @RequestParam(value = "inRelationToCpf", required = false) String inRelationToCpf){
        return courseService.getAllCourseInfo(pageNumber, pageSize, inRelationToCpf);
    }

}
