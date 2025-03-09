package dev.hudsonprojects.backend.integration.coursesapi.course;

import dev.hudsonprojects.backend.appuser.AppUserDTO;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsResolved;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("course")
public class CourseEndpoint {

    private final CourseAPIService courseAPIService;

    @Autowired
    public CourseEndpoint(CourseAPIService courseAPIService) {
        this.courseAPIService = courseAPIService;
    }

    @Operation(description = "Get all courses info")
    @ApiResponse(responseCode = "200", description = "Returns a list of courses", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AppUserDTO.class)) })
    @ApiResponse(responseCode = "401", description = "Authentication required", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema()) })
    @ApiResponse(responseCode = "500", description = "Error communicating with course API", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CourseInfoDTO> getCourses(@RequestParam(value = "pageNumber", required = false) Integer pageNumber, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return courseAPIService.getCourses(pageNumber, pageSize);
    }

}
