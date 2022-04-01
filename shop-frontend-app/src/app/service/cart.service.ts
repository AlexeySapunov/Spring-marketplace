import { Injectable } from '@angular/core';
import {AddLineItem} from "../model/addLineItem";
import {HttpClient} from "@angular/common/http";
import {AllCart} from "../model/allCart";
import {Observable} from "rxjs";
import {LineItem} from "../model/lineItem";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private http: HttpClient) { }

  public findAll() : Observable<AllCart> {
    return this.http.get<AllCart>('api/v1/cart/all');
  }

  public addToCart(addLineItem: AddLineItem) {
    return this.http.post<any>('api/v1/cart', addLineItem);
  }

  public removeLineItem(lineItem: LineItem) {
    return this.http.delete('api/v1/cart', ({
      body: lineItem
    }));
  }
}
