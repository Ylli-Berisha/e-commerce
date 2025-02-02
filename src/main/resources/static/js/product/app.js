class ProductApp {
    constructor() {
        this.productApi = new ProductApi();
        this.productsList = document.getElementById("products");
        this.shoppingCartApi = new ShoppingCartApi();
        const userIdElement = document.getElementById('userId');
        try {
            this.userId = parseInt(this.getUserId());
        }catch (e) {
            console.log("Not logged in:", e);
        }
    }

    async init() {
        console.log("ProductApp initialized");
        await this.loadProducts();
    }

    async loadProducts() {
        console.log("Loading products...");
        try {
            const products = await this.productApi.findAll();
            console.log(products);
            this.renderProducts(products);
        } catch (error) {
            console.error("Error loading products:", error);
        }
    }

    renderProducts(products) {
        console.log("Rendering products...");
        if (!this.productsList) {
            return;
        }
        if (!products || products.length === 0) {
            this.productsList.innerHTML = '<h2>No products found</h2>';
            return;
        }

        this.productsList.innerHTML = '';
        products.forEach(product => {
            const aContainer = document.createElement('a');
            aContainer.href = `http://localhost:8080/product/${product.id}`;

            const colDiv = document.createElement('div');
            colDiv.className = 'col mb-5';


            const cardDiv = document.createElement('div');
            cardDiv.className = 'card h-100';

            // Check if images exist
            const img = document.createElement('img');
            img.className = 'card-img-top';
            if (Array.isArray(product.images) && product.images.length > 0) {
                img.src = product.images[0];
            } else {
                img.src = 'https://dummyimage.com/450x300/dee2e6/6c757d.jpg'; // Fallback image
            }
            if (img.src === 'https://example.com/image.jpg')
                img.src = 'https://dummyimage.com/450x300/dee2e6/6c757d.jpg';
            img.alt = 'No image';

            const cardBodyDiv = document.createElement('div');
            cardBodyDiv.className = 'card-body p-4';

            const textCenterDiv = document.createElement('div');
            textCenterDiv.className = 'text-center';

            const h5 = document.createElement('h5');
            h5.className = 'fw-bolder';
            h5.textContent = product.name;

            const priceP = document.createElement('p');
            priceP.textContent = `Price: $${product.price}`;

            textCenterDiv.appendChild(h5);
            textCenterDiv.appendChild(priceP);
            cardBodyDiv.appendChild(textCenterDiv);

            const cardFooterDiv = document.createElement('div');
            cardFooterDiv.className = 'card-footer p-4 pt-0 border-top-0 bg-transparent';

            const textCenterFooterDiv = document.createElement('div');
            textCenterFooterDiv.className = 'text-center';

            const addToCartBtn = document.createElement('button');
            addToCartBtn.className = 'btn btn-outline-dark mt-auto';
            addToCartBtn.textContent = 'Add to cart';
            addToCartBtn.style.marginBottom = '10px';

            addToCartBtn.addEventListener('click', (e) => {
                e.preventDefault();
                if (isNaN(this.userId)) {
                    this.userId = this.getUserId();
                    if (isNaN(this.userId)) {
                        window.location.href = "/auth/login";
                        alert("Please login first");
                        return;
                    }
                }
                if (this.userId === undefined || this.userId === 'guest') {
                    window.location.href = "/auth/login";
                    alert("Please login first");
                    return;
                }
                console.log(`User ID in product app: ${this.userId}`);
                this.shoppingCartApi.addProductToShoppingCart(this.userId, product.id);
                console.log("Product added to cart");
                alert("Product added to cart!");
            });

            const buyProductBtn = document.createElement('button');
            buyProductBtn.className = 'btn btn-success mt-auto';
            buyProductBtn.textContent = 'Buy Product';

            buyProductBtn.addEventListener('click', (e) => {
                e.preventDefault();
                window.location.href = `http://localhost:8080/product/${product.id}`;
            });

            textCenterFooterDiv.appendChild(addToCartBtn);
            textCenterFooterDiv.appendChild(buyProductBtn);
            cardFooterDiv.appendChild(textCenterFooterDiv);

            cardDiv.appendChild(img);
            cardDiv.appendChild(cardBodyDiv);
            cardDiv.appendChild(cardFooterDiv);

            colDiv.appendChild(cardDiv);
            aContainer.appendChild(colDiv);
            this.productsList.appendChild(aContainer);
        });
    }

    getUserId() {
        return localStorage.getItem('userId') || 'guest';
    }
}
