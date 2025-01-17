class ProductApp {
    constructor() {
        this.productApi = new ProductApi();
        this.productsList = document.getElementById("products");
    }

    async init() {
        await this.loadProducts();
    }

    async loadProducts() {
        const products = await this.productApi.findAll();
        this.renderProducts(products);
    }

    renderProducts(products) {
        const productsContainer = document.querySelector('.row.gx-4.gx-lg-5.row-cols-2.row-cols-md-3.row-cols-xl-4.justify-content-center');
        productsContainer.innerHTML = '';
        products.forEach(product => {
            const colDiv = document.createElement('div');
            colDiv.className = 'col mb-5';

            const cardDiv = document.createElement('div');
            cardDiv.className = 'card h-100';

            const img = document.createElement('img');
            img.className = 'card-img-top';
            img.src = product.images[0].url;
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

            const viewOptionsLink = document.createElement('a');
            viewOptionsLink.className = 'btn btn-outline-dark mt-auto';
            viewOptionsLink.href = '#';
            viewOptionsLink.textContent = 'View options';

            textCenterFooterDiv.appendChild(viewOptionsLink);
            cardFooterDiv.appendChild(textCenterFooterDiv);

            cardDiv.appendChild(img);
            cardDiv.appendChild(cardBodyDiv);
            cardDiv.appendChild(cardFooterDiv);

            colDiv.appendChild(cardDiv);
            productsContainer.appendChild(colDiv);
        });
    }
}
new ProductApp().init().then(
    () => {
        console.log("Product app initialized");
    }
).catch(
    error => {
        console.error("Failed to initialize product app", error);
    }
);