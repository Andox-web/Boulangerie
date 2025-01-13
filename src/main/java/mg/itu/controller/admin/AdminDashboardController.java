package mg.itu.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import mg.itu.annotation.auth.RoleRequired;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {
    @GetMapping
    public String afficherDashboard(Model model) {
        model.addAttribute("page", "admin/dashboard");
        return "template/template";
    }
}