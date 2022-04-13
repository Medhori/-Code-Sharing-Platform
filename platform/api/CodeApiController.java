package platform.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import platform.model.Code;
import platform.service.CodeService;

import java.util.List;
import java.util.UUID;


@RestController
public class CodeApiController {
    private final CodeService codeService;


    @Autowired
    public CodeApiController(CodeService codeService) {
        this.codeService = codeService;
    }

    @PostMapping("api/code/new")
    public String addCode(@RequestBody Code code) {
        String id = codeService.addCode(code);
        return "{ \"id\" : \"" + id + "\" }";
    }

    @GetMapping(path = "api/code/latest", produces = "application/json;charset=UTF-8")
    public List<Code> getLatestCode() {
        return codeService.getLatest();
    }

    @GetMapping(path = "api/code/{id}", produces = "application/json;charset=UTF-8")
    public Code getCode(@PathVariable UUID id) {
        return codeService.getCode(id);
    }

    @DeleteMapping(path = "api/code/all")
    public void deleteAllCode() {
        codeService.deleteAllData();
    }

}
