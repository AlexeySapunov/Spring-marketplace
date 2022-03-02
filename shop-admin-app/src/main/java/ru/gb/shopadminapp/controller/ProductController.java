package ru.gb.shopadminapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.gb.shopadminapp.controller.dto.ProductDto;
import ru.gb.shopadminapp.service.CategoryService;
import ru.gb.shopadminapp.service.ProductService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listPage(Model model,
                           @RequestParam("categoryId") Optional<Long> categoryId,
                           @RequestParam("nameFilter") Optional<String> nameFilter,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sort") Optional<String> sort) {
        logger.info("Product filter with name pattern {}", nameFilter.orElse(null));

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", productService.findAll(
                categoryId,
                nameFilter,
                page.orElse(1) - 1,
                size.orElse(5),
                sort.filter(s ->
                        !s.isBlank())
                        .orElse("id")));
        return "product";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("activePage", "Product");
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        logger.info("Edit product page requested");

        model.addAttribute("product", productService.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found")));
        model.addAttribute("categories", categoryService.findAll());
        return "product_form";
    }

    @GetMapping("/new")
    public String create(Model model) {
        logger.info("New product page requested");

        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categoryService.findAll());
        return "product_form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("product") ProductDto product, BindingResult result, Model model) {
        logger.info("Saving product");

        if (result.hasErrors()) {
            logger.error(result.getAllErrors().toString());
            model.addAttribute("categories", categoryService.findAll());
            return "product_form";
        }
        productService.save(product);
        return "redirect:/product";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        logger.info("Deleting product with id {}", id);

        productService.deleteById(id);
        return "redirect:/product";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
