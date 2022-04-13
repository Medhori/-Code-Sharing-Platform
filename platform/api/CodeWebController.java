package platform.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import platform.service.CodeService;

import java.util.UUID;


@Controller
public class CodeWebController {
    private final CodeService codeService;

    @Autowired
    public CodeWebController(CodeService codeService) {
        this.codeService = codeService;
    }


    @GetMapping(path = "code/{id}")
    public String getCode(@PathVariable UUID id, Model model) {
        model.addAttribute("code", codeService.getCode(id));
        return "CodePage";
    }

    @GetMapping(path = "code/latest")
    public String getLatestCode(Model model) {
        model.addAttribute("tasksList", codeService.getLatest());
        return "codeLatest";
    }

    @GetMapping(path = "code/new")
    public String getNewCode() {
        return "codeNew";
    }


}
