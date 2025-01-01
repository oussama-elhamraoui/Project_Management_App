package com.example.projectmanagementapp.state;

import androidx.lifecycle.MutableLiveData;

import com.example.projectmanagementapp.models.Member;
import com.example.projectmanagementapp.models.Project;
import com.example.projectmanagementapp.models.ProjectTheme;
import com.example.projectmanagementapp.models.Task;
import com.example.projectmanagementapp.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectState {
    private static ProjectState instance;
    public final MutableLiveData<Project> project;

    private ProjectState() {
        project = new MutableLiveData<Project>(getDefaultProject());
    }

    private Project getDefaultProject(){
        return new Project(-1, null, null, new ArrayList<Task>(), ProjectTheme.BLUE, new ArrayList<Member>());
    }

    public static ProjectState getInstance(){
        if(instance == null){
            instance = new ProjectState();

        }
        return instance;
    }

    public void setProject(Project project){
        this.project.setValue(project);
    }

    public Project getProject(){
        return project.getValue();
    }

    public void addMember(final User user, boolean isAdmin) {
        final Project updatedProject= new Project(Objects.requireNonNull(project.getValue()));
        updatedProject.addMember(new Member(user, isAdmin));
        project.setValue(updatedProject);

    }
    public void deleteMember(final Member member) {
        final Project updatedProject= new Project(Objects.requireNonNull(project.getValue()));
        updatedProject.deleteMember(member);
        project.setValue(updatedProject);
    }

    public void setTheme(ProjectTheme theme){
        final Project newProject = new Project(Objects.requireNonNull(project.getValue()));
        newProject.setTheme(theme);
        project.setValue(newProject);
    }

    public ProjectTheme getTheme(){
        return Objects.requireNonNull(project.getValue()).getTheme();
    }

    public void addTask(Task task){
        Project updatedProject = new Project(Objects.requireNonNull(project.getValue()));
        updatedProject.addTask(task);
        project.setValue(updatedProject);
        update();
    }
    public void deleteTask(Task task){
        final Project updatedProject= new Project(Objects.requireNonNull(project.getValue()));
        updatedProject.deleteTask(task);
        project.setValue(updatedProject);
        update();
    }
    public void updateTask(Task task) {
        final Project updatedProject = new Project(Objects.requireNonNull(project.getValue()));
        updatedProject.updateTask(task);
        project.setValue(updatedProject);
        update();
    }

    public List<Task> getPendingTasks() {
        List<Task> pendingTasks = new ArrayList<>();
        List<Task> tasks = Objects.requireNonNull(project.getValue()).tasks;
        for (Task t : tasks) {
            if (t.status.equals("IN_PROGRESS") || t.status.equals("TO_DO")) {
                pendingTasks.add(t);
            }
        }
        return pendingTasks;
    }

    public List<Task> getFinishedTasks() {
        List<Task> pendingTasks = new ArrayList<>();
        List<Task> tasks = Objects.requireNonNull(project.getValue()).tasks;
        for (Task t : tasks) {
            if (t.status.equals("COMPLETED")) {
                pendingTasks.add(t);
            }
        }
        return pendingTasks;
    }

    public int getId(){
        return Objects.requireNonNull(project.getValue()).id;
    }

    private void update() {
        UserProjectsState.getInstance().updateProject(project.getValue());
    }
    public List<Task> getYourTasks(){
        return Objects.requireNonNull(project.getValue()).tasks;
    }

}
