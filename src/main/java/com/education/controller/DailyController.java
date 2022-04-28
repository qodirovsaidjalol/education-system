package com.education.controller;

import com.education.criteria.DailyCriteria;
import com.education.dto.daily.DailyCreateDto;
import com.education.dto.daily.DailyDto;
import com.education.dto.daily.DailyUpdateDto;
import com.education.dto.response.DataDto;
import com.education.entity.AuthUser;
import com.education.service.daily.DailyServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.bytebuddy.implementation.Implementation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("daily/*")
@Tag(name = "Daily Controller",description = "the Daily API")
public class DailyController extends AbstractController<DailyServiceImpl> {

    public DailyController(DailyServiceImpl service) {
        super(service);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully created Daily",
            content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",description = "Parameters is incorrect",content = @Content),
            @ApiResponse(responseCode = "404",description = "Student or Group not found in Education.Enter correct id!",content = @Content)
    })
    @Operation(summary = "create daily",description = "This method for create daily for student and only a teacher can create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Long>> create(@RequestBody DailyCreateDto dto) {
        return service.create(dto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully updated Daily",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",description = "Parameters is incorrect",content = @Content),
            @ApiResponse(responseCode = "404",description = "Group not found in Education. Enter correct id!",content = @Content)
    })
    @Operation(summary = "update daily",description = "This method for update daily for student and only a teacher can update")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<Long>> update(@RequestBody DailyUpdateDto dto) {
        return service.update(dto);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully deleted Daily",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400",description = "Daily not found in Education. Enter correct id!",content = @Content)
    })
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Void>> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully deleted Daily",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400",description = "Daily not found in Education. Enter correct id!",content = @Content)
    })
    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<DailyDto>> get(@PathVariable Long id) {
        return service.get(id);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully get Dailies",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400",description = "Daily not found in Education. Enter correct id!",content = @Content)
    })
    @RequestMapping(value = "/list/{page}/{size}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<DailyDto>>> getAll(@PathVariable Integer page,@PathVariable Integer size) {
        return service.getAll(new DailyCriteria(page,size));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully get Dailies",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400",description = "Daily not found in Education. Enter correct id!",content = @Content)
    })
    @RequestMapping(value = "/list/{studentId}/exam/{groupId}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<DailyDto>>> getAllByStudentAndGroupId(@PathVariable Long studentId,@PathVariable Long groupId) {
        return service.getAllByStudentAndGroupId(new DailyCriteria(),studentId,groupId);
    }

//    @RequestMapping(value = "/list/time", method = RequestMethod.GET)
//    public ResponseEntity<DataDto<List<DailyDto>>> getAllByTime(LocalDate date) {
//        return service.getAllByTime(new DailyCriteria(),date);
//    }
}
