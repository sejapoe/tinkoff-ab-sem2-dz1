package ru.sejapoe.dz1.security;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.sejapoe.dz1.model.User;

import java.util.Objects;

public class SecurityUtils {
    @Nullable
    public static User getCurrentUser() {
        return ((User) (SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

    @NotNull
    public static User requireCurrentUser() {
        return Objects.requireNonNull(getCurrentUser());
    }
}
