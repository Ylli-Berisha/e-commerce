class UserApi extends BaseApi {
    constructor() {
        super("/api/users");
    }

    async getUserByUsername(username) {
        const response = await fetch(`${this.baseUrl}/username/${username}`);
        return response.json();
    }
    async getUserByEmail(email) {
        const response = await fetch(`${this.baseUrl}/email/${email}`);
        return response.json();
    }
    async getUsersByRole(role) {
        const response = await fetch(`${this.baseUrl}/role/${role}`);
        return response.json();
    }
    async getUsersByActive(active) {
        const response = await fetch(`${this.baseUrl}/active/${active}`);
        return response.json();
    }
    async activateUser(userId) {
        const response = await fetch(`${this.baseUrl}/activate/${userId}`, {
            method: 'PUT'
        });
        return response.json();
    }
    async deactivateUser(userId) {
        const response = await fetch(`${this.baseUrl}/deactivate/${userId}`, {
            method: 'PUT'
        });
        return response.json();
    }
    async resetPassword(email, newPassword) {
        const response = await fetch(`${this.baseUrl}/reset-password?email=${email}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ newPassword })
        });
        return response.json();
    }
    async getBoughtProducts(userId) {
        const response = await fetch(`${this.baseUrl}/bought-products/${userId}`);
        return response.json();
    }
    async buyProduct(userId, productId, quantity) {
        const response = await fetch(`${this.baseUrl}/buy-product/${userId}/${productId}/?quantity=${quantity}`, {
            method: 'POST'
        });
    }

    async buyProducts(userId, productIdsAndQuantities) {
        const response = await fetch(`${this.baseUrl}/buy-products/${userId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(productIdsAndQuantities)
        });
    }

    async returnProduct(userId, productId, quantity) {
        const response = await fetch(`${this.baseUrl}/return-product/${userId}/${productId}/?quantity=${quantity}`, {
            method: 'POST'
        });
    }
    async logout() {
        const response = await fetch(`${this.baseUrl}/logout`, {
            method: 'POST'
        });
        if (response.status === 200) {
            window.location.href = '/auth/login';
            return true;
        }
        return false
    }
}