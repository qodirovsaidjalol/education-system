package com.education.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.education.dto.payload.PayloadCreateDto;
import com.education.dto.payload.PayloadDto;
import com.education.dto.payload.PayloadUpdateDto;
import com.education.dto.response.DataDto;
import com.education.service.payload.PayloadService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PayloadController extends AbstractController<PayloadService> {

    public PayloadController(PayloadService service) {
        super(service);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Student or Group not found in Education.Enter correct id!", content = @Content)
    })
    @Operation(summary = "create daily", description = "This method create payload for student")
    @RequestMapping(value = PATH + "/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Long>> create(@Valid @RequestBody PayloadCreateDto createDto) {
        return service.create(createDto);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Student not found in Education.Enter correct id!", content = @Content)
    })
    @Operation(summary = "create daily", description = "This method returns student's payload")
    @RequestMapping(value = PATH + "/get-by-student/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<PayloadDto>>> getByStudent(@PathVariable Long id) {
        return service.getByStudent(id);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "payload not found .Enter correct id!", content = @Content)
    })
    @Operation(summary = "create daily", description = "This method returns payload")
    @RequestMapping(value = PATH + "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<PayloadDto>> get(@PathVariable Long id) {
        return service.get(id);
    }
   
    
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Student or group not found in Education.Enter correct id!", content = @Content)
    })
    @Operation(summary = "create daily", description = "This method returns student's payload")
    @RequestMapping(value = PATH + "/get/{studentId}/{groupId}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<PayloadDto>>> getByStudentAndGroup(@PathVariable Long studentId, @PathVariable Long groupId) {
        return service.getByStudentAndGroup(studentId, groupId);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "payload not found .Enter correct id!", content = @Content)
    })
    @Operation(summary = "create daily", description = "This method returns All payloads")
    @RequestMapping(value = PATH + "/getAll", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<PayloadDto>>> getAll() {
        return service.getAll();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "payload not found .Enter correct id!", content = @Content)
    })
    @Operation(summary = "create daily", description = "This method returns All payloads from Education")
    //    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','HR')")
    @RequestMapping(value = PATH + "/getAllByEducation", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<PayloadDto>>> getAllByEducation() {
        return service.getAllByEducation();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Payload not found. Enter correct id!", content = @Content)
    })
    @Operation(summary = "update daily", description = "This method for update payload for student")
    @RequestMapping(value = PATH + "/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<Boolean>> update(@RequestBody PayloadUpdateDto dto) {
        return service.update(dto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully deleted",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400",description = "Payload not found Enter correct id!",content = @Content)
    })
    @RequestMapping(value = PATH + "/delete/{id}/", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
