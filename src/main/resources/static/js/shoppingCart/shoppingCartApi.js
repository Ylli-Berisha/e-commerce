class ShoppingCartApi {
    constructor() {
        this.baseUrl = "/api/shopping-carts";
    }
    async addProductToShoppingCart(userId, productDto) {
        const response = await fetch(`${this.baseUrl}/${userId}/add-product`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(productDto)
        });
        return response.json();
    }

    async removeProductFromCart(userId, productId) {
        const response = await fetch(`${this.baseUrl}/${userId}/remove-product/${productId}`, {
            method: 'DELETE'
        });
        return response.json();
    }

    async getCartByUserId(userId) {
        const response = await fetch(`${this.baseUrl}/${userId}`);
        return response.json();
    }

    async clearCart(userId) {
        const response = await fetch(`${this.baseUrl}/${userId}/clear`, {
            method: 'DELETE'
        });
        return response.json();
    }

    async getTotalPrice(userId) {
        const response = await fetch(`${this.baseUrl}/${userId}/total-price`);
        return response.json();
    }

    async getProductCount(userId) {
        const response = await fetch(`${this.baseUrl}/${userId}/product-count`);
        return response.json();
    }

    async isProductInCart(userId, productId) {
        const response = await fetch(`${this.baseUrl}/${userId}/is-product-in-cart/${productId}`);
        return response.json();
    }

    async getItemsByShoppingCartId(shoppingCartId) {
        const response = await fetch(`${this.baseUrl}/${shoppingCartId}/items`);
        return response.json();
    }

    async getItemByShoppingCartIdAndProductId(shoppingCartId, productId) {
        const response = await fetch(`${this.baseUrl}/${shoppingCartId}/item/${productId}`);
        return response.json();
    }
    async getItemsByShoppingCartIdAndQuantityGreaterThan(shoppingCartId, quantity) {
        const response = await fetch(`${this.baseUrl}/${shoppingCartId}/items/quantity-greater-than/${quantity}`);
        return response.json();
    }

    async addItemToShoppingCart(shoppingCartId, shoppingCartItemDto) {
        const response = await fetch(`${this.baseUrl}/${shoppingCartId}/add-item`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(shoppingCartItemDto)
        });
        return response.json();
    }

    async updateItemInShoppingCart(shoppingCartId, shoppingCartItemDto) {
        const response = await fetch(`${this.baseUrl}/${shoppingCartId}/update-item`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(shoppingCartItemDto)
        });
        return response.json();
    }

    async buyCartProducts(userId) {
        const response = await fetch(`${this.baseUrl}/${userId}/buy`, {
            method: 'POST'
        });
        return response.json();
    }
}