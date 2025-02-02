class WishlistApi{
    constructor() {
        this.baseUrl = "/api/wishlists";
    }
    async createWishlist(userId){
        const response = await fetch(`${this.baseUrl}/create/${userId}`, {
            method: 'POST'
        });
        return response.json();
    }
    async getWishlistByUserId(userId){
        const response = await fetch(`${this.baseUrl}/user/${userId}`);
        return response.json();
    }
    async addProductToWishlist(wishlistId, productId){
        const response = await fetch(`${this.baseUrl}${wishlistId}/add-product/${productId}`, {
            method: 'POST'
        });
        return response.json();
    }
    async removeProductFromWishlist(wishlistId, productId){
        const response = await fetch(`${this.baseUrl}${wishlistId}/remove-product/${productId}`, {
            method: 'DELETE'
        });
        return response.json();
    }
    async clearWishlist(wishlistId){
        const response = await fetch(`${this.baseUrl}${wishlistId}/clear`, {
            method: 'DELETE'
        });
        return response.json();
    }
    async isProductInWishlist(wishlistId, productId){
        const response = await fetch(`${this.baseUrl}${wishlistId}/contains/${productId}`);
        return response.json();
    }
    async buyWishlistProducts(wishlistId){
        const response = await fetch(`${this.baseUrl}${wishlistId}/buy`, {
            method: 'POST'
        });
        return response.json();
    }
}