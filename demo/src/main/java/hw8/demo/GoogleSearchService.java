package hw8.demo;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleSearchService {

    private final CseProps props;
    private final RestTemplate restTemplate = new RestTemplate();

    public GoogleSearchService(CseProps props) {
        this.props = props;
    }

    public List<SearchResult> search(String query) {
        List<SearchResult> results = new ArrayList<>();
        if (query == null || query.isBlank()) return results;

        if (!props.isEnabled() || props.getApiKey() == null || props.getCx() == null) {
            results.add(new SearchResult(
                    "Demo Result (請設定 google.cse.apiKey / cx)",
                    "https://developers.google.com/custom-search/v1/overview",
                    "尚未設定金鑰與ID。"
            ));
            return results;
        }

        try {
            String q = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String url = "https://www.googleapis.com/customsearch/v1"
                + "?key=" + props.getApiKey()
                + "&cx=" + props.getCx()
                + "&num=" + props.getNum()
                + "&lr=lang_zh-TW"
                + "&gl=tw"
                + "&hl=zh-TW"
                + "&q=" + q;

            @SuppressWarnings("unchecked")
            ResponseEntity<Map> resp = restTemplate.getForEntity(url, Map.class);
            Map<String, Object> body = resp.getBody();
            if (body == null) return results;

            Object itemsObj = body.get("items");
            if (itemsObj instanceof List<?> items) {
                for (Object itemObj : items) {
                    if (itemObj instanceof Map<?, ?> item) {
                        Object titleObj = item.get("title");
                        Object linkObj = item.get("link");
                        Object snippetObj = item.get("snippet");
                        String title = titleObj == null ? "" : titleObj.toString();
                        String link = linkObj == null ? "" : linkObj.toString();
                        String snippet = snippetObj == null ? "" : snippetObj.toString();
                        if (!title.isEmpty() && !link.isEmpty()) {
                            results.add(new SearchResult(title, link, snippet));
                        }
                    }
                }
            }
        } catch (Exception e) {
            results.add(new SearchResult("Request failed", "#", "呼叫 Google CSE 失敗：" + e.getMessage()));
        }
        return results;
    }
}