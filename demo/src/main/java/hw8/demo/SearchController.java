package hw8.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    private final GoogleSearchService searchService;

    public SearchController(GoogleSearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("q") String query, Model model) {
        List<SearchResult> results = searchService.search(query);
        model.addAttribute("q", query);
        model.addAttribute("results", results);
        return "search";
    }
}