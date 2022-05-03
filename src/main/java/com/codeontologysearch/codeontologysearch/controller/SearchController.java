package com.codeontologysearch.codeontologysearch.controller;

import com.codeontologysearch.codeontologysearch.dto.InputDto;
import com.codeontologysearch.codeontologysearch.dto.ResultDto;
import com.codeontologysearch.codeontologysearch.service.SearchService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("input", new InputDto());

        return "search"; //view
    }

    @PostMapping("/search")
    public String search(Model model, @ModelAttribute("input") InputDto term) throws FileNotFoundException {
        List<ResultDto> allByTerm = searchService.findAllByTerm("");
        if (term != null && StringUtils.isNotEmpty(term.getTerm())) {
            String termInput = term.getTerm();
            model.addAttribute("list",  allByTerm.stream().filter(outputdata -> {
                return StringUtils.containsIgnoreCase(outputdata.getLabel(), termInput)
                        || StringUtils.containsIgnoreCase(outputdata.getSubject(), termInput)
                        || StringUtils.containsIgnoreCase(outputdata.getSupertype(), termInput);
            }).collect(Collectors.toList()));
        } else {
            model.addAttribute("list",  allByTerm.stream().filter(outputdata -> {
                return StringUtils.isNoneBlank(outputdata.getLabel())
                        || StringUtils.isNoneBlank(outputdata.getSubject())
                        || StringUtils.isNoneBlank(outputdata.getSupertype());
            }).collect(Collectors.toList()));
        }


        return "search"; //view
    }

}
