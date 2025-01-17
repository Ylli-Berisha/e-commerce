class BaseApi {

    constructor(baseUrl) {
        this.baseUrl = baseUrl;
    }

    async findAll() {
        const response = await fetch(this.baseUrl);
        return response.json();
    }

    async findById(id) {
        const response = await fetch(`${this.baseUrl}/${id}`);
        return response.json();
    }

    async add(entity){
        const response = await fetch(this.baseUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(entity)
        });
        return response.json();
    }

    async modify(id, entity) {
        const response = await fetch(`${this.baseUrl}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(entity)
        });
        return response.json();
    }

    async removeById(id){
        const response = await fetch(`${this.baseUrl}/${id}`, {
            method: 'DELETE'
        });
        return response.json
    }

}



