/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Shopify Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.shopify.buy.model.internal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import com.shopify.buy.model.Customer;
import com.shopify.buy.utils.DateUtility;

import java.lang.reflect.Type;
import java.util.Date;

public class CustomerWrapper {

    private Customer customer;

    @SerializedName("access_token")
    private String token;

    public CustomerWrapper() {
        //empty constructor
    }

    public CustomerWrapper(Customer customer) {
        this.customer = customer;
        this.token = customer.getToken();
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getToken() {
        return token;
    }


    public static class CustomerWrapperDeserializer implements JsonDeserializer<CustomerWrapper> {

        @Override
        public com.shopify.buy.model.internal.CustomerWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return fromJson(json.toString());
        }

    }

    public static CustomerWrapper fromJson(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateUtility.DateDeserializer())
                .create();

        CustomerWrapper wrapper = gson.fromJson(json, CustomerWrapper.class);
        wrapper.customer.setToken(wrapper.getToken());
        return wrapper;
    }
}
