class TransactionApp {
    constructor() {
        this.userApi = new UserApi();
        this.shoppingCartApi = new ShoppingCartApi();
        this.wishlistApi = new WishlistApi();
        this.userId = this.getUserId();
    }

    async buyProduct(productId, quantity) {
        const response = await this.userApi.buyProduct(this.userId, productId, quantity);
        if (response.status === 200) {
            alert("Transaction successful");
            window.location.href = 'http://localhost:8080/';
            return true;
        }
        return false;
    }

    async buyProducts(map) {
    //to be implemented
    }

    async returnProduct(productId, quantity) {
        const response = await this.userApi.returnProduct(this.userId, productId, quantity);
        if (response.status === 200) {
            alert("Transaction successful");
            window.location.href = 'http://localhost:8080/';
            return true;
        }
        return false;
    }

    getUserId() {
        return localStorage.getItem("userId") || 'guest';
    }

}