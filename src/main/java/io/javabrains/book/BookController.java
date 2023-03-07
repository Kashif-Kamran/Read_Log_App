package io.javabrains.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class BookController
{
    @Autowired
    BookService bookService;
    private final String COVER_IMAGE_ROOL_URL = "https://covers.openlibrary.org/b/id/";

    @GetMapping("/books/{bookId}")
    public String getBook(@PathVariable String bookId, Model model, @AuthenticationPrincipal OAuth2User principle)
    {
        Optional<Book> bookOptional = bookService.getBookById(bookId);
        if (bookOptional.isPresent())
        {

            Book book = bookOptional.get();
            String coverImgURL = "/images/no-image.jpg";
            if (book.getCoversIds() != null && book.getCoversIds().size() > 0)
            {
                coverImgURL = this.COVER_IMAGE_ROOL_URL + book.getCoversIds().get(0) + "-L.jpg";
            }
            model.addAttribute("coverImg", coverImgURL);
            model.addAttribute("bookVariable", book);
            if (principle.getAttribute("login") != null)
            {
                model.addAttribute("loginId",principle.getAttribute("loginId"));
            }

            return "Book";

        } else
        {
            return "ErrorPage";
        }
    }
}
