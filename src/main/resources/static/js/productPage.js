document.addEventListener('DOMContentLoaded', () => {
    let productPage = new ProductPage();
});

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
        this.id = this.url.match(/\/product\/(\d+)/)[1];
        console.log("Product ID:", this.id); // Log the ID
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
            if (img.src.includes('http://localhost:8080/product/products  ')) {
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

        colDetailsDiv.appendChild(h2);
        colDetailsDiv.appendChild(descriptionP);
        colDetailsDiv.appendChild(priceP);
        colDetailsDiv.appendChild(addToCartBtn);

        rowDiv.appendChild(colImgDiv);
        rowDiv.appendChild(colDetailsDiv);

        this.container.appendChild(rowDiv);
    }


}
