package io.javabrains.search;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SearchController
{
    private final WebClient webClient;
    private final String COVER_IMAGE_ROOL_URL="https://covers.openlibrary.org/b/id/";
    public SearchController(WebClient.Builder webClientBuilder)
    {
        webClient = webClientBuilder.exchangeStrategies(ExchangeStrategies.
                builder().codecs(configure -> configure
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build()).baseUrl("http://openlibrary.org/search.json").build();
    }

    @GetMapping("/search")
    public String getSearchResults(@RequestParam String query, Model model)
    {
        Mono<SearchResult> resultsMon = this.webClient.get().uri("?q={query}", query).
                retrieve().bodyToMono(SearchResult.class);
        SearchResult searchResult = resultsMon.block();
        if (searchResult != null)
        {
            List<SearchResultBook> books = searchResult.getDocs()
                    .stream()
                    .limit(20)
                    .map(tempResult ->{
                        tempResult.setKey(tempResult.getKey().replace("/works/",""));
                        String coverId=tempResult.getCover_i();
                        if (StringUtils.hasText(coverId))
                        {
                            coverId=this.COVER_IMAGE_ROOL_URL+coverId+"-M.jpg";
                        }
                        else{
                                coverId="/images/no-image.jpg";
                        }
                        tempResult.setCover_i(coverId);
                        return tempResult;
                    })
                    .collect(Collectors.toList());
            books.forEach(x-> System.out.println(x.getTitle()));
            model.addAttribute("searchResult", books);
            return "Search";
        } else
        {
            return "ErrorPage";
        }
    }

}
