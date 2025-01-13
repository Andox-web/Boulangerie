package mg.itu.config.role;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.auth.Role;
import mg.itu.model.auth.Utilisateur;
import mg.itu.util.UrlUtil;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HttpSession session = request.getSession();
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

            if (utilisateur != null && utilisateur.getRole() != null) {
                request.setAttribute("role",utilisateur.getRole().getNom());
            }

            HandlerMethod handlerMethod = (HandlerMethod) handler;

            RoleRequired roleRequired = handlerMethod.getMethodAnnotation(RoleRequired.class);
            if (roleRequired == null) {
                roleRequired = handlerMethod.getBeanType().getAnnotation(RoleRequired.class);
            }

            if (roleRequired != null) {
                String[] requiredRoles = roleRequired.role();
                if (utilisateur == null || utilisateur.getRole() == null) {
                    if (requiredRoles.length == 0) {
                        return true;
                    }
                    return handleRoleError(request, response, handlerMethod);
                }

                Role role = utilisateur.getRole();
                String userRole = role.getNom();
                String[] exceptRoles = roleRequired.except();

                if (userRole == null || 
                    (requiredRoles.length > 0 && Arrays.stream(requiredRoles).noneMatch(userRole::equalsIgnoreCase)) || 
                    (exceptRoles.length > 0 && Arrays.stream(exceptRoles).anyMatch(userRole::equalsIgnoreCase))) {
                    return handleRoleError(request,response, handlerMethod);
                }
            }
        }

        return true; 
    }

    private boolean handleRoleError(HttpServletRequest request,HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        if (handlerMethod.getBeanType().isAnnotationPresent(RestController.class)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Accès refusé\"}");
        } else {
            response.sendRedirect(UrlUtil.getBaseUrl(request));
        }
        System.out.println("Erreur non autorisee");
        return false; 
    }
}
