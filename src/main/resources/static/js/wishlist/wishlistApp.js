class WishlistApp {
    constructor() {
        this.wishlistApi = new WishlistApi();
        this.productApi = new ProductApi();
        this.productApp = new ProductApp();
        this.userId = this.getUserId();
        this.products = [];
        this.productIds = []; // Default to empty array
    }

    async initialize() {
        await this.initWishlist();
    }

    async initWishlist() {
        try {
            this.wishlist = await this.wishlistApi.getWishlistByUserId(this.userId);

            // Create new wishlist if none exists
            if (!this.wishlist) {
                this.wishlist = await this.wishlistApi.createWishlist(this.userId);
                this.wishlist.productIds = []; // Initialize productIds
            }

            // Ensure productIds is always an array
            this.productIds = this.wishlist.productIds || [];
            await this.init();
        } catch (error) {
            console.error("Failed to initialize wishlist:", error);
        }
    }

    async init() {
        try {
            // Process productIds safely
            for (const id of this.productIds) {
                const response = await this.productApi.findById(id);
                if (response.ok) {
                    this.products.push(await response.json());
                }
            }
            this.productApp.renderProducts(this.products);
        } catch (error) {
            console.error("Failed to load products:", error);
        }
    }

    async addProductToWishlist(productId) {
        const response = await this.wishlistApi.addProductToWishlist(this.userId, productId);
        if (response.ok) {
            this.productIds.push(productId);
            this.products.push(await this.productApi.findById(productId));
            this.productApp.renderProducts(this.products);
    }
}
    async removeProductFromWishlist(productId) {
        const response = await this.wishlistApi.removeProductFromWishlist(this.userId, productId);
        console.log(response);
        if (response.ok) {
            const index = this.productIds.indexOf(productId);
            console.log(index);
            console.log(this.productIds);
            this.productIds.splice(index, 1);
            this.products.splice(index, 1);
            this.productApp.renderProducts(this.products);
        }
    }

    async clearWishlist() {
        const response = await this.wishlistApi.clearWishlist(this.userId);
        if (response.ok) {
            this.productIds = [];
            this.products = [];
            this.productApp.renderProducts(this.products);
        }
    }

    async isProductInWishlist(productId) {
        return await this.wishlistApi.isProductInWishlist(this.userId, productId) && this.productIds.includes(productId);
    }

    getUserId() {
        return localStorage.getItem("userId") || 'guest';
    }
}