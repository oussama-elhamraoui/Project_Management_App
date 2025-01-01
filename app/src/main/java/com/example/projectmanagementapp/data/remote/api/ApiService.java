package com.example.projectmanagementapp.data.remote.api;

import com.example.projectmanagementapp.data.remote.model.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    // Authentication API

    /**
     * Logs in a user with email and password.
     * @param loginRequest Contains the user's email and password.
     * @return A AuthResponse containing the token and user details.
     */
    @POST("api/v1/auth/login")
    Call<AuthResponse> login(@Body LoginRequest loginRequest);

    /**
     * Registers a new user with the provided details.
     * @param firstname The user's first name.
     * @param lastname The user's last name.
     * @param email The user's email address.
     * @param password The user's password.
     * @return A SignUpResponse containing the created user details.
     */
    @POST("api/v1/auth/register")
    Call<AuthResponse> signUp(@Query("first_name") String firstname,
                                @Query("last_name") String lastname,
                                @Query("email") String email,
                                @Query("password") String password,
                                @Query("color") int color);

                                                ;

    // Project API

    /**
     * Fetches all projects accessible to the authenticated user.
     * @param token Authorization token.
     * @return A list of ProjectResponse objects.
     */
    @GET("/api/projects")
    Call<List<ProjectResponse>> getAllProjects(@Header("Authorization") String token);

    /**
     * Fetches details of a specific project by its ID.
     * @param token Authorization token.
     * @param projectId The ID of the project.
     * @return A ProjectResponse containing project details.
     */
    @GET("/api/projects/{projectId}")
    Call<ProjectResponse> getProjectById(@Header("Authorization") String token,
                                         @Path("projectId") int projectId);

    /**
     * Creates a new project.
     * @param token Authorization token.
     * @param projectRequest Contains the project details to be created.
     * @return A ProjectResponse containing the created project details.
     */
    @POST("/api/projects")
    Call<ProjectResponse> createProject(@Header("Authorization") String token,
                                        @Body ProjectRequest projectRequest);

    /**
     * Updates an existing project.
     * @param token Authorization token.
     * @param projectId The ID of the project to be updated.
     * @param projectDetails The updated project details.
     * @return A ProjectResponse containing the updated project details.
     */
    @PUT("/api/projects/{projectId}")
    Call<ProjectResponse> updateProject(@Header("Authorization") String token,
                                        @Path("projectId") int projectId,
                                        @Body ProjectRequest projectDetails);

    /**
     * Deletes a project by its ID.
     * @param token Authorization token.
     * @param projectId The ID of the project to be deleted.
     * @return Void.
     */
    @DELETE("/api/projects/{projectId}")
    Call<Void> deleteProject(@Header("Authorization") String token,
                             @Path("projectId") int projectId);



    // Task API

    /**
     * Fetches all tasks for a specific project.
     * @param token Authorization token.
     * @param projectId The ID of the project.
     * @return A list of TaskResponse objects.
     */
    @GET("/api/tasks/project/{projectId}")
    Call<List<TaskResponse>> getTasksByProject(@Header("Authorization") String token,
                                               @Path("projectId") int projectId);

    /**
     * Creates a new task and assigns it to a user in a project.
     * @param token Authorization token.
     * @param projectId The ID of the project.
     * @param userId The ID of the user to whom the task will be assigned.
     * @param taskRequest Contains the task details to be created.
     * @return A TaskResponse containing the created task details.
     */
    @POST("/api/tasks/project/{projectId}/user/{userId}")
    Call<TaskResponse> createTask(@Header("Authorization") String token,
                                  @Path("projectId") int projectId,
                                  @Path("userId") int userId,
                                  @Body TaskRequest taskRequest);

    /**
     * Updates an existing task.
     * @param token Authorization token.
     * @param taskId The ID of the task to be updated.
     * @param taskDetails The updated task details.
     * @return A TaskResponse containing the updated task details.
     */
    @PUT("/api/tasks/{taskId}")
    Call<TaskResponse> updateTask(@Header("Authorization") String token,
                                  @Path("taskId") int taskId,
                                  @Body TaskRequest taskDetails);

    @POST("/api/contributors/user")
    Call<UserResponse> getUser(@Header("Authorization") String token,
                                  @Body UserRequest userRequest);

    /**
     * Deletes a task by its ID.
     * @param token Authorization token.
     * @param taskId The ID of the task to be deleted.
     * @return Void.
     */
    @DELETE("/api/tasks/{taskId}")
    Call<Void> deleteTask(@Header("Authorization") String token,
                          @Path("taskId") int taskId);




    // Contributors API

    /**
     * Fetches all contributors for a specific project.
     * @param token Authorization token.
     * @param projectId The ID of the project.
     * @return A list of MemberResponse objects.
     */
    @GET("/api/contributors/project/{projectId}")
    Call<List<MemberResponse>> getContributorsByProject(@Header("Authorization") String token,
                                                        @Path("projectId") int projectId);

    /**
     * Adds a contributor to a project by user ID.
     * @param token Authorization token.
     * @param projectId The ID of the project.
     * @param userId The ID of the user to be added.
     * @return A MemberResponse containing the added contributor details.
     */
    @POST("/api/contributors/project/{projectId}/user/{userId}")
    Call<MemberResponse> addContributorById(@Header("Authorization") String token,
                                            @Path("projectId") int projectId,
                                            @Path("userId") int userId);

    /**
     * Adds a contributor to a project by email.
     * @param token Authorization token.
     * @param projectId The ID of the project.
     * @param email The email of the user to be added.
     * @return A MemberResponse containing the added contributor details.
     */
    @POST("/api/contributors/project/{projectId}")
    Call<MemberResponse> addContributorByEmail(@Header("Authorization") String token,
                                               @Path("projectId") int projectId,
                                               @Query("email") String email);

    /**
     * Removes a contributor from a project by user ID.
     * @param token Authorization token.
     * @param projectId The ID of the project.
     * @param userId The ID of the user to be removed.
     * @return Void.
     */
    @DELETE("/api/contributors/project/{projectId}/user/{userId}")
    Call<Void> removeContributorById(@Header("Authorization") String token,
                                     @Path("projectId") int projectId,
                                     @Path("userId") int userId);

    /**
     * Removes a contributor from a project by email.
     * @param token Authorization token.
     * @param projectId The ID of the project.
     * @param email The email of the user to be removed.
     * @return Void.
     */
    @DELETE("/api/contributors/project/{projectId}")
    Call<Void> removeContributorByEmail(@Header("Authorization") String token,
                                        @Path("projectId") int projectId,
                                        @Query("email") String email);



    // Task Assignment API

    /**
     * Fetches all users assigned to a specific task.
     * @param token Authorization token.
     * @param taskId The ID of the task.
     * @return A list of TaskAssignmentResponse objects.
     */
    @GET("/api/tasks/assignment/task/{taskId}")
    Call<List<TaskAssignmentResponse>> getTaskAssignments(@Header("Authorization") String token,
                                                          @Path("taskId") int taskId);

    /**
     * Assigns a task to a user by user ID.
     * @param token Authorization token.
     * @param taskId The ID of the task.
     * @param userId The ID of the user to be assigned.
     * @return A TaskAssignmentResponse containing the assignment details.
     */
    @POST("/api/tasks/assignment/task/{taskId}/user/{userId}")
    Call<TaskAssignmentResponse> assignTaskToUserById(@Header("Authorization") String token,
                                                      @Path("taskId") int taskId,
                                                      @Path("userId") int userId);

    /**
     * Assigns a task to a user by email.
     * @param token Authorization token.
     * @param taskId The ID of the task.
     * @param email The email of the user to be assigned.
     * @return A TaskAssignmentResponse containing the assignment details.
     */
    @POST("/api/tasks/assignment/task/{taskId}")
    Call<TaskAssignmentResponse> assignTaskToUserByEmail(@Header("Authorization") String token,
                                                         @Path("taskId") int taskId,
                                                         @Query("email") String email);

    /**
     * Unassigns a task from a user by user ID.
     * @param token Authorization token.
     * @param taskId The ID of the task.
     * @param userId The ID of the user to be unassigned.
     * @return Void.
     */
    @DELETE("/api/tasks/assignment/task/{taskId}/user/{userId}")
    Call<Void> unassignTaskFromUserById(@Header("Authorization") String token,
                                        @Path("taskId") int taskId,
                                        @Path("userId") int userId);

    /**
     * Unassigns a task from a user by email.
     * @param token Authorization token.
     * @param taskId The ID of the task.
     * @param email The email of the user to be unassigned.
     * @return Void.
     */
    @DELETE("/api/tasks/assignment/task/{taskId}")
    Call<Void> unassignTaskFromUserByEmail(@Header("Authorization") String token,
                                           @Path("taskId") int taskId,
                                           @Query("email") String email);
}
