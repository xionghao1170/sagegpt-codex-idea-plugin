package com.sage.codex.sagecodex.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.IdeFocusManager;
import com.intellij.openapi.wm.IdeFrame;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/7 18:18
 */
public class ApplicationUtil {

    private ApplicationUtil() {
    }

    @Nullable
    public static Project findCurrentProject() {
        IdeFrame frame = IdeFocusManager.getGlobalInstance().getLastFocusedFrame();
        Project project = frame != null ? frame.getProject() : null;
        if (isValidProject(project)) {
            return project;
        } else {
            Project[] projects = ProjectManager.getInstance().getOpenProjects();
            int projectsLength = projects.length;

            for(int i = 0; i < projectsLength; ++i) {
                Project p = projects[i];
                if (isValidProject(p)) {
                    return p;
                }
            }

            return null;
        }
    }

    @Nonnull
    public static Iterable<Project> findValidProjects() {
        return (Iterable) Arrays.stream(ProjectManager.getInstance().getOpenProjects()).filter(ApplicationUtil::isValidProject).collect(Collectors.toList());
    }

    private static boolean isValidProject(@Nullable Project project) {
        return project != null && !project.isDisposed() && !project.isDefault();
    }
}
