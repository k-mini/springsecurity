package com.kmini.springsecurity.aopsecurity;

import com.kmini.springsecurity.domain.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AopSecurityController {

    private final AopMethodService aopMethodService;
    private final AopPointcutService aopPointcutService;

    @GetMapping("/preAuthorize")
    @PreAuthorize("hasRole('ROLE_USER') AND #account.username == principal.username")
    public String preAuthorize(AccountDto account, Model model, Principal principal, @AuthenticationPrincipal Object object) {
        System.out.println("principal = " + principal);
        System.out.println("AuthenticationPrincipal class = " + object.getClass());
        System.out.println("AuthenticationPrincipal = " + object);

        model.addAttribute("method", "Success @PreAuthorize");

        return "aop/method";
    }

    @GetMapping("/methodSecured")
    public String methodSecured(Model model) {

        aopMethodService.methodSecured();
        model.addAttribute("method", "Success MethodSecured");
        return "aop/method";
    }

    @GetMapping("/pointcutSecured")
    public String pointcutSecured(Model model) {

        aopPointcutService.notSecured();
        aopPointcutService.pointcutSecured();
        model.addAttribute("pointcut", "Success PointcutSecured");

        return "aop/method";
    }

}
