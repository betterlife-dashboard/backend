package com.betterlife.todo.controller;

import com.betterlife.todo.dto.*;
import com.betterlife.todo.service.RecurTaskService;
import com.betterlife.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final RecurTaskService recurTaskService;

    @Operation(operationId = "todoDetail", summary = "To-Do 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "To-Do 확인",
                    content = @Content(schema = @Schema(implementation = TodoResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음 (다른 사용자의 Todo 접근)",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 To-Do",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))
    })
    @Parameter(
            in = ParameterIn.HEADER,
            name = "X-User-Id",
            required = true,
            schema = @Schema(type = "integer", defaultValue = "-1", example = "-1")
    )
    @GetMapping("/detail/todo/{id}")
    public ResponseEntity<TodoResponse> getTodoDetail(@PathVariable("id") Long id, @RequestHeader("X-User-Id") Long userId) {
        TodoResponse todoResponse = todoService.getTodoById(userId, id);
        return ResponseEntity.ok(todoResponse);
    }

    @Operation(operationId = "todoCreate", summary = "To-Do 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "To-Do 생성",
                    content = @Content(schema = @Schema(implementation = TodoResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
    })
    @Parameter(
            in = ParameterIn.HEADER,
            name = "X-User-Id",
            required = true,
            schema = @Schema(type = "integer", defaultValue = "-1", example = "-1")
    )
    @PostMapping("/create/todo")
    public ResponseEntity<TodoResponse> createTodo(@RequestBody TodoCreateRequest todoCreateRequest, @RequestHeader("X-User-Id") Long userId) {
        TodoResponse todoResponse = todoService.createTodo(userId, todoCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(todoResponse);
    }

    @Operation(operationId = "todoDelete", summary = "To-Do 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "To-Do 삭제 완료",
                    content = @Content(schema = @Schema(implementation = TodoResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
            @ApiResponse(responseCode = "403", description = "허용되지 않은 To-Do 접근",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
    })
    @Parameter(
            in = ParameterIn.HEADER,
            name = "X-User-Id",
            required = true,
            schema = @Schema(type = "integer", defaultValue = "-1", example = "-1")
    )
    @DeleteMapping("/delete/todo/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") Long id, @RequestHeader("X-User-Id") Long userId) {
        todoService.deleteTodo(userId, id);
        return ResponseEntity.noContent().build();
    }

    @Operation(operationId = "todoUpdate", summary = "To-Do 변경")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "To-Do 변경 완료",
                    content = @Content(schema = @Schema(implementation = TodoResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
            @ApiResponse(responseCode = "403", description = "허용되지 않은 To-Do 접근",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
    })
    @Parameter(
            in = ParameterIn.HEADER,
            name = "X-User-Id",
            required = true,
            schema = @Schema(type = "integer", defaultValue = "-1", example = "-1")
    )
    @PutMapping("/put/todo/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable("id") Long id, @RequestBody TodoUpdateRequest request, @RequestHeader("X-User-Id") Long userId) {
        TodoResponse updated = todoService.updateTodo(userId, id, request);
        return ResponseEntity.ok(updated);
    }

    @Operation(operationId = "recurTaskCreate", summary = "RecurTask 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "RecurTask 생성",
                    content = @Content(schema = @Schema(implementation = RecurTaskResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
    })
    @Parameter(
            in = ParameterIn.HEADER,
            name = "X-User-Id",
            required = true,
            schema = @Schema(type = "integer", defaultValue = "-1", example = "-1")
    )
    @PostMapping("/create/recur")
    public ResponseEntity<RecurTaskResponse> createRecurTask(@RequestBody RecurTaskCreateRequest recurTaskCreateRequest, @RequestHeader("X-User-Id") Long userId) {
        RecurTaskResponse recurTaskResponse = recurTaskService.createRecurTask(userId, recurTaskCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(recurTaskResponse);
    }

    @Operation(operationId = "recurTaskDetail", summary = "Recur Task 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recur Task 확인",
                    content = @Content(schema = @Schema(implementation = RecurTaskResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음 (다른 사용자의 Todo 접근)",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 To-Do",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))
    })
    @Parameter(
            in = ParameterIn.HEADER,
            name = "X-User-Id",
            required = true,
            schema = @Schema(type = "integer", defaultValue = "-1", example = "-1")
    )
    @GetMapping("/detail/recur/{id}")
    public ResponseEntity<RecurTaskResponse> getRecurTaskDetail(@PathVariable("id") Long id, @RequestHeader("X-User-Id") Long userId) {
        RecurTaskResponse recurTaskResponse = recurTaskService.getRecurTaskById(id, userId);
        return ResponseEntity.ok(recurTaskResponse);
    }

    @Operation(operationId = "recurTaskDelete", summary = "반복 일정 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "반복 일정 삭제 완료",
                    content = @Content(schema = @Schema(implementation = RecurTaskResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
            @ApiResponse(responseCode = "403", description = "허용되지 않은 반복 일정 접근",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
    })
    @Parameter(
            in = ParameterIn.HEADER,
            name = "X-User-Id",
            required = true,
            schema = @Schema(type = "integer", defaultValue = "-1", example = "-1")
    )
    @DeleteMapping("/delete/recur/{id}")
    public ResponseEntity<Void> deleteRecurTask(@PathVariable("id") Long id, @RequestHeader("X-User-Id") Long userId) {
        recurTaskService.deleteRecurTask(id, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(operationId = "recurTaskUpdate", summary = "반복 일정 변경")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "반복 일정 변경 완료",
                    content = @Content(schema = @Schema(implementation = TodoResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
            @ApiResponse(responseCode = "403", description = "허용되지 않은 반복 일정 접근",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
    })
    @Parameter(
            in = ParameterIn.HEADER,
            name = "X-User-Id",
            required = true,
            schema = @Schema(type = "integer", defaultValue = "-1", example = "-1")
    )
    @PutMapping("/put/recur/{id}")
    public ResponseEntity<RecurTaskResponse> updateRecurTask(@PathVariable("id") Long id, @RequestBody RecurTaskUpdateRequest request, @RequestHeader("X-User-Id") Long userId) {
        RecurTaskResponse updated = recurTaskService.updateRecurTask(id, userId, request);
        return ResponseEntity.ok(updated);
    }
}
