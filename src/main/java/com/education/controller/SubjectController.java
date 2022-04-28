package com.education.controller;

import com.education.criteria.GenericCriteria;
import com.education.dto.response.DataDto;
import com.education.dto.subject.SubjectCreateDto;
import com.education.dto.subject.SubjectDto;
import com.education.dto.subject.SubjectUpdateDto;
import com.education.service.subject.SubjectServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SubjectController extends AbstractController<SubjectServiceImpl> {
    public SubjectController(SubjectServiceImpl service) {
        super(service);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    @Operation(summary = "create daily", description = "This method create subject for education")
    @RequestMapping(value = PATH + "/subject/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Long>> create(@Valid @RequestBody SubjectCreateDto dto) {
        return service.create(dto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Subject not found in Education.Enter correct id!", content = @Content)
    })
    @Operation(summary = "create daily", description = "This method updated subject")
    @RequestMapping(value = PATH + "/subject/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<Boolean>> update(@RequestBody SubjectUpdateDto dto) {
        return service.update(dto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Subject not found in Education.Enter correct id!", content = @Content)
    })
    @Operation(summary = "create daily", description = "This method delete subject")
    @RequestMapping(value = PATH + "/subject/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Subject not found in Education.Enter correct id!", content = @Content)
    })
    @Operation(summary = "create daily", description = "This method return subject by id")
    @RequestMapping(value = PATH + "/subject/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<SubjectDto>> get(@PathVariable Long id) {
        return service.get(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Subject not found in Education.Enter correct id!", content = @Content)
    })
    @Operation(summary = "create daily", description = "This method returns All subjects")
    @RequestMapping(value = PATH + "/subject/list", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<SubjectDto>>> getAll() {
        return service.getAll(new GenericCriteria());
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Subject not found in Education.Enter correct id!", content = @Content)
    })
    @Operation(summary = "create daily", description = "This method returns All subjects by education")
    @RequestMapping(value = PATH + "/subject/list/", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<SubjectDto>>> getAllByEducation() {
        return service.getAllByEducation();
    }

}
