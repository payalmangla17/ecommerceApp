package com.example.ecommerce.model;

import java.util.List;

public class CartAdapterData {
    private Product results;
    private int cnt;
    public CartAdapterData(Product results, int cnt){
        this.results=results;
        this.cnt=cnt;
    }

    public Product getResults() {
        return results;
    }

    public int getCnt() {
        return cnt;
    }

    public void setResults(Product results) {
        this.results = results;
    }

    public void setCnt(int cnt) {
            this.cnt = cnt;
    }
}
