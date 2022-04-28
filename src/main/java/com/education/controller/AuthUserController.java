package com.education.controller;

import com.education.criteria.AuthUserCriteria;
import com.education.dto.auth.*;
import com.education.dto.response.DataDto;
import com.education.enums.Role;
import com.education.service.auth.AuthUserServiceImpl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("auth/*")
public class AuthUserController extends AbstractController<AuthUserServiceImpl> {

    public AuthUserController(AuthUserServiceImpl service) {
        super(service);
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<DataDto<SessionDto>> getToken(@RequestBody LoginDto dto) {

        return service.getToken(dto);
    }

    @RequestMapping(value = "/refresh-token", method = RequestMethod.GET)
    public ResponseEntity<DataDto<SessionDto>> getToken(HttpServletRequest request, HttpServletResponse response) {
        return service.refreshToken(request, response);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully created AuthUser",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",description = "Parameters is incorrect",content = @Content),
            @ApiResponse(responseCode = "404",description = "Student or Group not found in Education.Enter correct id!",content = @Content)
    })
    @Operation(summary = "create for ADMIN user",description = "This method for create daily for student and only a teacher can create")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Long>> create(@RequestBody @Valid AuthUserCreateDto dto) {
        return service.create(dto);

    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully created AuthUser",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",description = "Parameters is incorrect",content = @Content),
            @ApiResponse(responseCode = "404",description = "Student or Group not found in Education.Enter correct id!",content = @Content)
    })
    @Operation(summary = "create daily",description = "This method for create daily for student and only a teacher can create")

    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @RequestMapping(value = "/create/super/", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Long>> createSuper(@RequestParam Long id, @RequestBody @Valid AuthUserCreateDto dto) {
        return service.create(id, dto);

    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully created AuthUser",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",description = "Parameters is incorrect",content = @Content),
            @ApiResponse(responseCode = "404",description = "Student or Group not found in Education.Enter correct id!",content = @Content)
    })
    @Operation(summary = "create daily",description = "This method for create daily for student and only a teacher can create")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Long>> update(@Valid @RequestBody AuthUserUpdateDto dto) {
        return service.update(dto);

    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully created AuthUser",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",description = "Parameters is incorrect",content = @Content),
            @ApiResponse(responseCode = "404",description = "Student or Group not found in Education.Enter correct id!",content = @Content)
    })
    @Operation(summary = "create daily",description = "This method for create daily for student and only a teacher can create")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @RequestMapping(value = "/change/role", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Void>> changeRole(@RequestParam Long roleID, @RequestParam Long userId) {
        return service.changeRole(roleID, userId);

    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully created AuthUser",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500",description = "Parameters is incorrect",content = @Content),
            @ApiResponse(responseCode = "404",description = "Student or Group not found in Education.Enter correct id!",content = @Content)
    })
    @Operation(method = "for delete User", description = "delete only admin")
    // @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Void>> delete(@PathVariable Long id) {
        return service.delete(id);
    }


    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<AuthUserDto>> get(@PathVariable Long id) {

        return service.get(id);

    }


    @RequestMapping(value = "block/super/", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Void>> block(@RequestParam Long id, @RequestParam Long educationId) {

        return service.block(id, educationId);
    }

    @RequestMapping(value = "block/{id}", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Void>> block(@PathVariable Long id) {

        return service.block(id);

    }
}
