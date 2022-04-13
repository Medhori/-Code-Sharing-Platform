package platform.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import platform.dao.CodeDao;
import platform.model.Code;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
public class CodeService {
    private final CodeDao codeDao;

    @Autowired
    public CodeService(CodeDao codeDao) {
        this.codeDao = codeDao;
    }

    public String addCode(Code code) {
        var newCode = codeDao.save(new Code(code.getCode(), code.getTime(), code.getViews()));
        return newCode.printID();
    }


    public List<Code> getLatest() {
        List<Code> newList = new ArrayList<>();
        var tasks = codeDao.findAll().stream()
                .filter(code -> code.getTime() == 0 && code.getViews() == 0)
                .collect(Collectors.toList());
        for (int i = tasks.size() - 1; i >= 0; i--) {
            newList.add(tasks.get(i));
            if (newList.size() == 10) {
                break;
            }
        }

        return newList;
    }

    public Code getCode(UUID id) {
        var code = codeDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "code not found"));
        if (code.rest()) {
            long consumedTime = consumedTime(code.getDate());
            if (consumedTime < code.getTime() && code.getViews() > 0) {
                code.setTime(code.getTime() - consumedTime);
                code.setViews(code.getViews() - 1);
                codeDao.save(code);
                return code;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else if (code.timeRest()) {
            long consumedTime = consumedTime(code.getDate());
            if (consumedTime < code.getTime()) {
                code.setTime(code.getTime() - consumedTime);
                codeDao.save(code);
                return code;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

        } else if (code.viewRest()) {
            if (code.getViews() > 0) {
                code.setViews(code.getViews() - 1);
                codeDao.save(code);
                return code;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
        return code;
    }


    public long consumedTime(String date) {
        String[] dateTime = date.split(" ");
        LocalTime dateOfCode = LocalTime.parse(dateTime[1]);
        LocalTime thisMoment = LocalTime.now();
        return thisMoment.toSecondOfDay() - dateOfCode.toSecondOfDay();
    }

    public void deleteAllData() {
        codeDao.deleteAll();
    }

}
