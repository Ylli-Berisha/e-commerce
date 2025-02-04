window.fetch = ((originalFetch) => {
    return function(url, options) {
        if (typeof url === "string" && url.startsWith("http://localhost:8080/product/")) {
            url = url.replace("http://localhost:8080/product/", "http://localhost:8080/");
        }
        return originalFetch(url, options);
    };
})(window.fetch);

class ProductPage {
    constructor() {
        this.productApi = new ProductApi();
        this.container = document.getElementById("containerDiv");
        console.log(this.container); // Check if the container is found
        if (!this.container) {
            console.error("Container element not found!");
            return;
        }
        this.url = window.location.href;
        console.log("URL:", this.url); // Log the URL
        //this.id = this.url.match(/\/product\/(\d+)/)[1];
        this.id = document.getElementById("productId").textContent;
        console.log("Product ID before:", this.id); // Log the ID
        this.id = parseInt(this.id);
        console.log("Product ID after:", this.id); // Log the ID
        console.log("Product ID:", this.id); // Log the ID
        this.wishlistApp = new WishlistApp();
        this.init();
    }

    async init() {
        console.log("ProductPage initialized");
        await this.loadProduct();
    }

    async loadProduct() {
        console.log("Loading product...");
        try {
            const product = await this.productApi.findById(this.id);
            console.log("Product fetched:", product);
            this.renderProduct(product);
        } catch (error) {
            console.error("Error loading product:", error);
        }
    }

    renderProduct(product) {
        console.log("Rendering product...");
        if (!product) {
            console.error("No product data available");
            return;
        }

        this.container.innerHTML = '';
        const rowDiv = document.createElement('div');
        rowDiv.className = 'row product-card';


        const colImgDiv = document.createElement('div');
        colImgDiv.className = 'col-md-6';

        const img = document.createElement('img');
        img.className = 'img-fluid';
        img.alt = 'Product Image';

        // Check if the product has images, if not, use a placeholder image
        if (product.images && product.images.length > 0) {
            img.src = product.images[0];
            console.log("Image URL:", img.src);
            if (img.src.includes('http://localhost:8080/product/products')) {
                img.src = img.src.replace(/\/product/, '')
            }
        } else {
            img.src = 'https://dummyimage.com/450x300/dee2e6/6c757d.jpg'; // Set a placeholder image URL
        }if (img.src === 'https://example.com/image.jpg') {
            img.src = 'https://dummyimage.com/450x300/dee2e6/6c757d.jpg';
        }

        colImgDiv.appendChild(img);

        const colDetailsDiv = document.createElement('div');
        colDetailsDiv.className = 'col-md-6 product-details';

        const h2 = document.createElement('h2');
        h2.textContent = product.name;

        const descriptionP = document.createElement('p');
        descriptionP.textContent = product.description;

        const priceP = document.createElement('p');
        priceP.textContent = `Price: $` + product.price;

        const addToCartBtn = document.createElement('button');
        addToCartBtn.className = 'btn btn-primary';
        addToCartBtn.id = 'addToCartBtn';
        addToCartBtn.textContent = 'Add to Cart';

        const addToWishlistBtn = document.createElement('button');
        addToWishlistBtn.className = 'btn btn-secondary';
        addToWishlistBtn.id = 'addToWishlistBtn';
        addToWishlistBtn.textContent = 'Add to Wishlist';

        const removeFromWishlistBtn = document.createElement('button');
        removeFromWishlistBtn.className = 'btn btn-danger';
        removeFromWishlistBtn.id = 'removeFromWishlistBtn';
        removeFromWishlistBtn.textContent = 'Remove from Wishlist';

        addToWishlistBtn.addEventListener('click', async (e) => {
            e.preventDefault();
            const isProductInWishlist = await this.wishlistApp.isProductInWishlist(product.id);
            if (isProductInWishlist) {
                console.log(`Product ${product.id} is already in the wishlist`);
                alert("Product is already in the wishlist!");
                return;
            }
            await this.wishlistApp.addProductToWishlist(product.id);
            console.log(`Product ${product.id} added to wishlist`);
            alert("Product added to wishlist!");
        });

        removeFromWishlistBtn.addEventListener('click', async (e) => {
            e.preventDefault();
            await this.wishlistApp.removeProductFromWishlist(product.id);
            console.log(`Product ${product.id} removed from wishlist`);
            //window.location = 'http://localhost:8080/wishlist';
            alert("Product removed from wishlist!");
        });

        colDetailsDiv.appendChild(h2);
        colDetailsDiv.appendChild(descriptionP);
        colDetailsDiv.appendChild(priceP);
        colDetailsDiv.appendChild(addToCartBtn);
        colDetailsDiv.appendChild(addToWishlistBtn);
        colDetailsDiv.appendChild(removeFromWishlistBtn);

        rowDiv.appendChild(colImgDiv);
        rowDiv.appendChild(colDetailsDiv);

        this.container.appendChild(rowDiv);
    }


}

document.addEventListener('DOMContentLoaded', () => {
    let productPage = new ProductPage();
});

