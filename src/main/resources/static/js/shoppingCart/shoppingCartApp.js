class ShoppingCartApp {
    constructor() {
        this.productApi = new ProductApi();
        this.shoppingCartApi = new ShoppingCartApi();
        this.container = document.getElementById('shoppingCartDiv');
        this.userId = this.getUserId();

        // Get initial data from hidden elements
        this.productIds = Array.from(document.querySelectorAll('#idList p')).map(p => p.textContent);
        this.quantities = Array.from(document.querySelectorAll('#quantities p')).map(p => parseInt(p.textContent) || 1);

        // Set up clear cart button
        const clearBtn = document.querySelector('.clear-cart');
        if (clearBtn) {
            clearBtn.addEventListener('click', () => this.clearCart());
        }
    }

    async init() {
        await this.loadProducts();
        this.updateTotalPrice();
    }

    async loadProducts() {
        this.container.innerHTML = '';

        for (const [index, productId] of this.productIds.entries()) {
            const product = await this.productApi.findById(productId);
            if (!product) continue;

            const cartItemDiv = this.createCartItem(product, index);
            this.container.appendChild(cartItemDiv);
        }
    }

    createCartItem(product, index) {
        const cartItemDiv = document.createElement('div');
        cartItemDiv.className = 'cart-item';
        cartItemDiv.style.marginTop = '20px';

        // Product image
        const img = document.createElement('img');
        img.src = product.images[0] || 'https://dummyimage.com/450x300/dee2e6/6c757d.jpg';
        img.alt = 'Product Image';
        img.className = 'product-image';
        cartItemDiv.appendChild(img);

        // Product details
        const productDetailsDiv = document.createElement('div');
        productDetailsDiv.className = 'product-details';

        const title = document.createElement('h3');
        title.className = 'product-title';
        title.textContent = product.name;
        productDetailsDiv.appendChild(title);

        const price = document.createElement('p');
        price.className = 'product-price';
        const priceValue = parseFloat(product.price) || 0;
        price.textContent = (priceValue * this.quantities[index]).toFixed(2) + ' €';
        productDetailsDiv.appendChild(price);

        cartItemDiv.appendChild(productDetailsDiv);

        // Quantity controls
        const quantityControlDiv = document.createElement('div');
        quantityControlDiv.className = 'quantity-control';



        const minusButton = document.createElement('button');
        minusButton.className = 'quantity-btn';
        minusButton.textContent = '-';
        quantityControlDiv.appendChild(minusButton);

        const quantitySpan = document.createElement('span');
        quantitySpan.className = 'quantity';
        quantitySpan.textContent = this.quantities[index];
        quantityControlDiv.appendChild(quantitySpan);

        const plusButton = document.createElement('button');
        plusButton.className = 'quantity-btn';
        plusButton.textContent = '+';
        quantityControlDiv.appendChild(plusButton);

        cartItemDiv.appendChild(quantityControlDiv);

        // Delete button
        const deleteButton = document.createElement('button');
        deleteButton.className = 'delete-btn';
        deleteButton.textContent = '🗑';
        cartItemDiv.appendChild(deleteButton);

        // Event listeners
        plusButton.addEventListener('click', () => {
            this.updateQuantity(index, quantitySpan, price, priceValue, 1);
            this.updateTotalPrice()
        });

        minusButton.addEventListener('click', () => {
            this.updateQuantity(index, quantitySpan, price, priceValue, -1);
            this.updateTotalPrice()
        });

        deleteButton.addEventListener('click', () => {
            this.handleDelete(index, cartItemDiv);
        });

        return cartItemDiv;
    }

    async updateQuantity(index, quantitySpan, price, priceValue, change) {
        const newQuantity = parseInt(quantitySpan.textContent) + change;
        if (newQuantity < 1) return;

        this.quantities[index] = newQuantity;
        quantitySpan.textContent = newQuantity;
        price.textContent = (priceValue * newQuantity).toFixed(2) + ' €';

        await this.shoppingCartApi.updateItemQuantity(
            this.userId,
            this.productIds[index],
            newQuantity
        );

        this.updateTotalPrice();
    }

    async handleDelete(index, itemElement) {
        await this.shoppingCartApi.removeProductFromCart(
            this.userId,
            this.productIds[index]
        );

        // Remove from arrays
        this.productIds.splice(index, 1);
        this.quantities.splice(index, 1);

        // Remove from DOM
        itemElement.remove();
        this.updateTotalPrice();
    }

    async clearCart() {
        await this.shoppingCartApi.clearCart(this.userId);
        this.productIds = [];
        this.quantities = [];
        this.container.innerHTML = '';
        document.getElementById('totalPrice').textContent = '0.00 €';
    }

    async updateTotalPrice() {
        let total = 0;
        for (let i = 0; i < this.productIds.length; i++) {
            const product = await this.productApi.findById(this.productIds[i]);
            total += product.price * this.quantities[i];
        }
        document.getElementById('totalPrice').textContent = total.toFixed(2) + ' €';
        await this.shoppingCartApi.updateCartTotalPrice(this.userId, total);
    }

    getUserId() {
        return localStorage.getItem('userId') || 'guest';
    }
}

document.addEventListener('DOMContentLoaded', () => {
    new ShoppingCartApp().init();
});