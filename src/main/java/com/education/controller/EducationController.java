package com.education.controller;


import com.education.criteria.EducationCriteria;
import com.education.dto.education.EducationCreateDto;
import com.education.dto.education.EducationDto;
import com.education.dto.education.EducationUpdateDto;
import com.education.dto.response.DataDto;
import com.education.service.education.EducationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("education/*")
public class EducationController extends AbstractController<EducationServiceImpl> {

    public EducationController(EducationServiceImpl service) {
        super(service);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully created Education",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",description = "Parameters is incorrect",content = @Content),
            @ApiResponse(responseCode = "404",description = "Student or Group not found in Education.Enter correct id!",content = @Content)
    })
    @Operation(summary = "create daily",description = "This method for create daily for student and only a teacher can create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/create")
    private ResponseEntity<DataDto<Long>> create(  @Valid @RequestBody EducationCreateDto dto) throws ParseException {
        return service.create(dto);

    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully updated Education",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",description = "Parameters is incorrect",content = @Content),
            @ApiResponse(responseCode = "404",description = "Group not found in Education. Enter correct id!",content = @Content)
    })
    @Operation(summary = "update daily",description = "This method for update education")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/update")
    private ResponseEntity<DataDto<Long>> update(@RequestBody EducationUpdateDto dto) {
        return service.update(dto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully created Education",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",description = "Parameters is incorrect",content = @Content),
            @ApiResponse(responseCode = "404",description = "Education.Enter correct id!",content = @Content)
    })
    @Operation(summary = "get education",description = "get eduction for ADMIN and SUPER_ADMIN")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<EducationDto>> get(@PathVariable Long id) {
       return service.get(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully created Education",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",description = "Parameters is incorrect",content = @Content),
            @ApiResponse(responseCode = "404",description = "Student or Group not found in Education.Enter correct id!",content = @Content)
    })
    @Operation(summary = "create daily",description = "This method for create daily for student and only a teacher can create")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<DataDto<EducationDto>> getOne() {
        return service.getOne();
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully deleted Education",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400",description = "Education not found in Education. Enter correct id!",content = @Content)
    })
   @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<EducationDto>>> getAll(@RequestParam EducationCriteria criteria) {

        return service.getAll(criteria);

    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",description = "Parameters is incorrect",content = @Content),
            @ApiResponse(responseCode = "404",description = " Education.Enter correct id!",content = @Content)
    })
    @Operation(summary = "block user",description = "This method for block education")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/block/{id}", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Void>> block(@PathVariable Long id) {

       return service.block(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully deleted Education",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",description = "Parameters is incorrect",content = @Content),
            @ApiResponse(responseCode = "404",description = "Student or Group not found in Education.Enter correct id!",content = @Content)
    })
    @Operation(summary = "create daily",description = "delete user")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Void>> delete(@PathVariable Long id) {
       return service.delete(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully created Education",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",description = "Parameters is incorrect",content = @Content),
            @ApiResponse(responseCode = "404",description = "Student or Group not found in Education.Enter correct id!",content = @Content)
    })
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/payed{id}", method = RequestMethod.POST)
    @Operation(description ="date format yyyy-mm-dd hh:mm:ss" )
    public ResponseEntity<DataDto<Void>> payed(@RequestBody String data,@PathVariable Long id) throws ParseException {
        return new ResponseEntity<>(new DataDto<>(service.payed(data,id)), HttpStatus.OK);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully created Education",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",description = "Parameters is incorrect",content = @Content),
            @ApiResponse(responseCode = "404",description = "Student or Group not found in Education.Enter correct id!",content = @Content)
    })
    @Operation(summary = "block",description = "This method for update owner for")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "update/owner/{ownerId}/{eduId}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Void>> updateOwner(@PathVariable Long ownerId,@PathVariable Long eduId) {
        return service.updateOwner(ownerId,eduId);
    }

}
