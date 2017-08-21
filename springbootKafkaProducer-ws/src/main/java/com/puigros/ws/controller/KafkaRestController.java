package com.puigros.ws.controller;

import com.puigros.dto.TestDTO;
import com.puigros.service.KafkaMessaging;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This is a sample rest controller class.
 * <p>
 * <p>

 * @since JDK1.8
 */
@Slf4j
@RestController
@RequestMapping(value = "/kafka/1.0/")
@Api(value="Example System")
public class KafkaRestController {

    @Autowired
    private KafkaMessaging service;

    /**s
     * Retrieve All Samples
     * @return All samples
     */

    @ApiOperation(
            tags = "SAMPLE",
            value = "Get a list of SampleDTO",
            notes = "The Samples endpoint returns a list of SampleDTO objects",
            response = Boolean.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Failure")
    })
    @RequestMapping(value = "/message", produces = {MediaType.APPLICATION_JSON_VALUE},
                    headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Void> sendMessage(
            @ApiParam(value = "testDTO", required = true) @RequestBody TestDTO testDTO

    ) {

            List<Boolean> dtos = new ArrayList<>();
            service.sendMessage(testDTO);
            return new ResponseEntity<>(HttpStatus.OK);

    }



}
