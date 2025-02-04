package org.bisha.ecommercefinal.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.bisha.ecommercefinal.dtos.*;
import org.bisha.ecommercefinal.helpers.FileHelper;
import org.bisha.ecommercefinal.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private static final String UPLOAD_DIR = "uploads/products/";

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubcategoryService subcategoryService;
    @Autowired
    private FileHelper fileHelper;
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ShoppingCartItemService shoppingCartItemService;
    @Autowired
    private WishlistService wishlistService;


    @GetMapping
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/about")
    public String getAboutPage() {
        return "about";
    }

    @GetMapping("/contact")
    public String getContactPage() {
        return "contact";
    }

    @GetMapping("product/{id}")
    public String getProductPage(Model model, @PathVariable Long id) {
        if (productService.getProductById(id) == null) {
            return "home";
        }
        model.addAttribute("productId", id);
        return "productPage";
    }

    @GetMapping("add-product")
    public String getAddProductPage(Model model) {
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("subcategories", subcategoryService.getAllSubcategories());
        return "addProduct";
    }

    @GetMapping("/create/{dtoName}")
    public String getCreatePage(@PathVariable String dtoName, Model model) {
        switch (dtoName) {
            case "category": {
                model.addAttribute("category", new CategoryDto());
                break;
            }
            case "subcategory": {
                model.addAttribute("subcategory", new SubcategoryDto());
                model.addAttribute("categories", categoryService.getAllCategories());
                break;
            }
            case "product": {
                model.addAttribute("categories", categoryService.getAllCategories());
                model.addAttribute("subcategories", subcategoryService.getAllSubcategories());
                model.addAttribute("product", new ProductRegisterDto());
                break;
            }
            default: {
                return "home";
            }
        }
        return "create";
    }

    @PostMapping(value = "/create/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional// Ensure atomicity
    public String addProduct(
            @ModelAttribute @Valid ProductRegisterDto productRegisterDto,
            BindingResult bindingResult,
            @RequestParam("photoFile") MultipartFile photoFile, // Required by default
            Model model
    ) {
        // Add dropdown data to the model (required for form re-rendering)
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("subcategories", subcategoryService.getAllSubcategories());

        // Validate file upload
        if (photoFile.isEmpty()) {
            bindingResult.rejectValue("photoFile", "file.required", "Please select a file to upload.");
        }

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", productRegisterDto);
            return "create";
        }

        // Handle file upload
        String photoFileName;
        try {
            String fileName = fileHelper.uploadFile(
                    UPLOAD_DIR,
                    photoFile.getOriginalFilename(),
                    photoFile.getBytes()
            );
            photoFileName = "products/" + fileName;
        } catch (IOException e) {
            bindingResult.rejectValue("photoFile", "file.upload.failed", "File upload failed.");
            model.addAttribute("product", productRegisterDto);
            return "create";
        }

        // Map DTO and create product
        ProductDto productDto = new ProductDto();
        productDto.setName(productRegisterDto.getName());
        productDto.setDescription(productRegisterDto.getDescription());
        productDto.setPrice(productRegisterDto.getPrice());
        productDto.setStock(productRegisterDto.getStock());
        productDto.setBrand(productRegisterDto.getBrand());
        productDto.setCategoryId(productRegisterDto.getCategoryId());
        productDto.setSubcategoryId(productRegisterDto.getSubcategoryId());
        productDto.setCreatedAt(LocalDate.now());
        productDto.setAvailable(true);
        productDto.setRating(0.0);
        productDto.setImages(List.of(photoFileName)); // Include image in DTO

        // Save product and image in a transaction
        ProductDto createdProduct = productService.addProduct(productDto);
        if (createdProduct == null) {
            bindingResult.reject("product.creation.failed", "Product creation failed.");
            model.addAttribute("product", productRegisterDto);
            return "create";
        }

        return "redirect:/";
    }

    @PostMapping("/create/category")
    public String addCategory(
            @ModelAttribute CategoryDto categoryDto,
            BindingResult bindingResult,
            Model model
    ) {
        model.addAttribute("categories", categoryService.getAllCategories());

        // Check for validation errors in category details
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryDto); // Retain user input
            return "create"; // Return to the form page with validation errors
        }

        // Proceed with category creation
        CategoryDto createdCategory = categoryService.saveCategory(categoryDto);
        if (createdCategory == null) {
            bindingResult.reject("category.creation.failed", "Category creation failed. Please try again.");
            model.addAttribute("category", categoryDto); // Retain user input
            return "create"; // Return to form with category creation error
        }

        // Redirect to home page on successful category creation
        return "redirect:/";
    }

    @PostMapping("/create/subcategory")
    public String addSubcategory(
            @ModelAttribute SubcategoryDto subcategoryDto,
            BindingResult bindingResult,
            Model model
    ) {
        model.addAttribute("categories", categoryService.getAllCategories());

        // Check for validation errors in subcategory details
        if (bindingResult.hasErrors()) {
            model.addAttribute("subcategory", subcategoryDto); // Retain user input
            return "create"; // Return to the form page with validation errors
        }

        // Proceed with subcategory creation
        SubcategoryDto createdSubcategory = subcategoryService.saveSubcategory(subcategoryDto);
        if (createdSubcategory == null) {
            bindingResult.reject("subcategory.creation.failed", "Subcategory creation failed. Please try again.");
            model.addAttribute("subcategory", subcategoryDto); // Retain user input
            return "create"; // Return to form with subcategory creation error
        }

        // Redirect to home page on successful subcategory creation
        return "redirect:/";
    }

    @GetMapping("shopping-cart")
    public String getShoppingCartPage(HttpServletRequest request,  Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }
        if (session.getAttribute("user") == null) {
            model.addAttribute("userLoginDto", new UserLoginDto());
            return "signin";
        }
        var user = (UserDto) session.getAttribute("user");
        Long id = user.getId();
        ShoppingCartDto shoppingCart;
        List<ShoppingCartItemDto> items = null;
        if (shoppingCartService.getCartByUserId(id) == null) {
           shoppingCart = shoppingCartService.createCart(id);
        }else {
            shoppingCart = shoppingCartService.getCartByUserId(id);
            items = new ArrayList<>();
            var itemIds = shoppingCart.getShoppingCartItemIds();
            for (var itemId : itemIds) {
                items.add(shoppingCartItemService.getShoppingCartItemById(itemId));
            }
        }
        model.addAttribute("cart", shoppingCart);
        model.addAttribute("items", items);
        return "shoppingCart";
    }

    @GetMapping("wishlist")
    public String getWishlistPage(HttpServletRequest request,  Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }
        if (session.getAttribute("user") == null) {
            model.addAttribute("userLoginDto", new UserLoginDto());
            return "signin";
        }
        var user = (UserDto) session.getAttribute("user");
        Long id = user.getId();
        WishlistDto wishlist;
        if (wishlistService.getWishlistByUserId(id) == null) {
            wishlist = wishlistService.createWishlist(id);
        }else {
            wishlist = wishlistService.getWishlistByUserId(id);
            var productIds = wishlist.getProductIds();
            model.addAttribute("productIds", productIds);
        }
        model.addAttribute("wishlist", wishlist);

        return "wishlist";
    }

}